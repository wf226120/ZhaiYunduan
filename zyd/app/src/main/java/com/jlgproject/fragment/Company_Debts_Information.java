package com.jlgproject.fragment;

import android.os.Handler;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Details;
import com.jlgproject.adapter.Debt_Information_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debts_Informations;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/6/29.
 */

/**
 * 企业债事信息
 */
public class Company_Debts_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    private PullToRefreshListView listView;
    private Long id;
    private Login_zud zud;
    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;

    private Handler handler = new Handler();
    private Debts_Informations informations;
    private List<Debts_Informations.DataBean.ItemsBean> items;
    private Debt_Information_Adapter adapter;


    @Override
    public int getLoadViewId() {
        return R.layout.debt_manger_listview;
    }


    @Override
    public View initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        requestDebtsInformation();

        return view;
    }


    private void requestDebtsInformation() {
        if (ShowDrawDialog(getActivity())) {
            id = Debt_Matter_Management_Details.id;
            L.e("------债事企业债事信息--id----" + id);
            GetParmars parmars = new GetParmars();
            parmars.add("debtId", id);
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Debts_Informations> info = new HttpMessageInfo<>(BaseUrl.DEBTS_INFORMATION, new Debts_Informations());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }


    //抽取的展示数据的方法
    private void displayData(final Debts_Informations info) {

        if (items == null) {
            items = info.getData().getItems();
            adapter = new Debt_Information_Adapter(getActivity(), items);
            listView.setAdapter(adapter);
        } else if (status == REFRESH) {
            items = info.getData().getItems();
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else if (status == LOADMORE) {
            List<Debts_Informations.DataBean.ItemsBean> items2 = info.getData().getItems();
            items.addAll(items2);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Debts_Informations) {
            informations = (Debts_Informations) o;
            if (informations != null) {
                DismissDialog();
                listView.onRefreshComplete();
                if (informations.getState().equals("ok")) {
                    displayData(informations);
                } else {
                    ToastUtil.showShort(getActivity(), informations.getMessage());
                }
            }

        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        listView.onRefreshComplete();
        ToastUtil.showShort(getActivity(), "服务器繁忙，请稍后再试");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestDebtsInformation();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestDebtsInformation();
    }
}
