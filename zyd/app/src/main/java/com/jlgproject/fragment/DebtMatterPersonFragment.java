package com.jlgproject.fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.New_Debt_Matter_Preson;
import com.jlgproject.adapter.DebtManerPresonAdapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debt_Preson_Manger;
import com.jlgproject.model.UserType;
import com.jlgproject.model.eventbusMode.DebtPreson;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 债事人管理 fragment
 */
public class DebtMatterPersonFragment extends BaseFragment implements HttpMessageInfo.IMessage, View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {


    private TextView title_name;
    private int pn = 1;
    private int ps = 8;
    private List<Debt_Preson_Manger.DataBean.ItemsBean> items;
    private LinearLayout line;
    private LinearLayout line2;
    private DebtManerPresonAdapter adapter;
    private PullToRefreshListView listview;
    // 1.设置几个状态码方便我们进行状态的判断
    private static  final  int NOMAIL=0;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private ImageView title_lift_xz;
    private ImageView title_left;
    private int a = 1;
    private LinearLayout serchlie;
    private EditText naicard;
    private ImageView sousuo;
    private boolean state = false;
    private String trim;


    @Override
    public int getLoadViewId() {
        return R.layout.fragment_debtmatter_person_manag;
    }


    @Override
    public View initView(View view) {
        EventBus.getDefault().register(this);
        naicard = (EditText) view.findViewById(R.id.et_naicard);
        sousuo = (ImageView) view.findViewById(R.id.serch_fdj);
        sousuo.setOnClickListener(this);
        title_name = (TextView) view.findViewById(R.id.tv_title_name);
        title_name.setText("债事人管理");
        serchlie = (LinearLayout) view.findViewById(R.id.serch_line);
        title_lift_xz = (ImageView) view.findViewById(R.id.iv_title_right);
        title_lift_xz.setImageResource(R.drawable.add_debt_person3);
        title_lift_xz.setVisibility(View.VISIBLE);
        title_lift_xz.setOnClickListener(this);
        title_left = (ImageView) view.findViewById(R.id.iv_title_left);
        title_left.setVisibility(View.VISIBLE);
        title_left.setImageResource(R.mipmap.search_bar);
        title_left.setOnClickListener(this);
        line = (LinearLayout) view.findViewById(R.id.linerlayout);//默认页面
        line.setVisibility(View.GONE);
        line2 = (LinearLayout) view.findViewById(R.id.line2);//搜索
        listview = (PullToRefreshListView) view.findViewById(R.id.listview);
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.BOTH);

        int type = UserInfoState.getUserType();
        if (type != 0) {
            judgeState(type);
        }
        getUserType();
        return view;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            int type = UserInfoState.getUserType();
            if (type != 0) {
                judgeState(type);
            }
            getUserType();
        }
    }

    public void getUserType() {

        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                L.e("----请求用户type----");
                HttpMessageInfo<UserType> info = new HttpMessageInfo<>(BaseUrl.USER_TYPE, new UserType());
                info.setiMessage(this);
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, null, headers, 1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == title_left) {//搜索框
            switch (a) {
                case 1:
                    serchlie.setVisibility(View.VISIBLE);
                    a = 2;
                    break;
                case 2:
                    serchlie.setVisibility(View.GONE);
                    a = 1;
                    break;
            }
        } else if (v == title_lift_xz) {//新增债事人
            if (UserInfoState.isLogin()) {
                ActivityCollector.startA(getActivity(), New_Debt_Matter_Preson.class);
            }

        } else if (v == sousuo) {
            state = true;
            if (!TextUtils.isEmpty(naicard.getText().toString().trim())) {
                status=NOMAIL;
                requstDebtManger();
            } else {
                ToastUtil.showShort(getActivity(), "请输入搜索人姓名和身份证号");
            }
        }
    }


    private void requstDebtManger() {
        //获取网络数据
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                HttpMessageInfo<Debt_Preson_Manger> info = new HttpMessageInfo<>(BaseUrl.DEBT_PRESON_MANGER, new Debt_Preson_Manger());
                info.setiMessage(this);
                GetParmars parmars = new GetParmars();

                if (state == true) {
                    trim = naicard.getText().toString().trim();
                    parmars.add("condition", trim);
                    state = false;
                    parmars.add("pn", 1);
                    parmars.add("ps", ps);
                    L.e(trim);
                } else {
                    parmars.add("pn", pn);
                    parmars.add("ps", ps);
                }
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebtP(DebtPreson debtPreson) {
        int person = debtPreson.getDebtpVul();
        if (person == 1) {
            getUserType();
            status = NOMAIL;
            requstDebtManger();
        }
    }

    //抽取的展示数据的方法
    private void displayData(final Debt_Preson_Manger manger) {

        if (status==NOMAIL) {
            items = manger.getData().getItems();
            adapter = new DebtManerPresonAdapter(getActivity(), items);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            if (status == REFRESH) {
                L.e("--------刷新——————");
                items.clear();
                items = manger.getData().getItems();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                L.e("--------加载——————");
                if (items.size() < 8) {
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    L.e("----人-1---");
                } else {
                    List<Debt_Preson_Manger.DataBean.ItemsBean> items2 = manger.getData().getItems();
                    items.addAll(items2);
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    L.e("----人-2---");
                }
            }
        }
    }


    private void judgeState(int userTypeData) {

        if (userTypeData == 1) {
            line.setVisibility(View.GONE);
            line2.setVisibility(View.VISIBLE);
            status=NOMAIL;
            requstDebtManger();
        } else if (userTypeData == 2 || userTypeData == 3) {
            line2.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof UserType) { //根据type动态设置权限
            UserType userType = (UserType) o;
            if (userType != null) {
                DismissDialog();
                UserInfoState.setUserType(userType.getData().getUserType());
                UserInfoState.setUserPhone(userType.getData().getPhoneNumber());
                int type = UserInfoState.getUserType();
                judgeState(type);
            } else {
                getUserType();
            }
        } else if (o instanceof Debt_Preson_Manger) {
            Debt_Preson_Manger debt_preson_manger = (Debt_Preson_Manger) o;
            if (debt_preson_manger != null) {
                DismissDialog();
                listview.onRefreshComplete();
                if (debt_preson_manger.getState().equals("ok")) {
                    displayData(debt_preson_manger);
                } else {
                    ToastUtil.show(getActivity(), debt_preson_manger.getMessage(), Toast.LENGTH_SHORT);
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        listview.onRefreshComplete();
        DismissDialog();
        ToastUtil.showShort(getActivity(), "服务器繁忙,请稍后再试");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status=REFRESH;
        requstDebtManger();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status=LOADMORE;
        requstDebtManger();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        pn=1;
        ps=8;
    }
}
