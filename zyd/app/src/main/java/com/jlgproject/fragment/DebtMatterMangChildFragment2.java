package com.jlgproject.fragment;

import android.os.Handler;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.DebtManerAdapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debts_Manger;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/2.
 */

public class DebtMatterMangChildFragment2 extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    private int pn = 1;
    private int ps = 8;
    private PullToRefreshListView listview;
    private Login_zud zud;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Handler handler = new Handler();
    private List<Debts_Manger.DataBean.ItemsBean> items;
    private DebtManerAdapter debtManerAdapter;

    @Override
    public int getLoadViewId() {
        return R.layout.debt_manger_listview;
    }

    @Override
    public View initView(View view) {

        listview = (PullToRefreshListView) view.findViewById(R.id.listview);
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }


    //抽取显示数据的方法；
    private void displayData(Debts_Manger data) {
        if (status == NORMAL) {
            items = data.getData().getItems();
            debtManerAdapter= new DebtManerAdapter(getActivity(), items);
            listview.setAdapter(debtManerAdapter);
            debtManerAdapter.notifyDataSetChanged();
        } else if (status == REFRESH) {
            items.clear();
            items = data.getData().getItems();

            if (items != null) {
                debtManerAdapter.setItems(items);
                debtManerAdapter.notifyDataSetChanged();
            } else {
                ToastUtil.showShort(getActivity(), "暂无已解债数据");
            }
        } else if (status == LOADMORE) {

            int size = items.size();
            if (size != 0) {
                if (items.size() < 8) {
                    debtManerAdapter.setItems(items);
                    debtManerAdapter.notifyDataSetChanged();
                } else {
                    List<Debts_Manger.DataBean.ItemsBean> items1 = data.getData().getItems();
                    items.addAll(items1);
                    debtManerAdapter.setItems(items);
                    debtManerAdapter.notifyDataSetChanged();
                }

            } else {
                ToastUtil.showShort(getActivity(), "暂无数据");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        status=NORMAL;
        resquestDebt_Manger();
        L.e("-------债市管理-----已解决---");
    }

    private void resquestDebt_Manger() {
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                HttpMessageInfo<Debts_Manger> info = new HttpMessageInfo<>(BaseUrl.DEBTS_MANGER, new Debts_Manger());
                info.setiMessage(this);
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                GetParmars parmars = new GetParmars();
                parmars.add("issolution", 1);
                parmars.add("pn", pn);
                parmars.add("ps", ps);
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
            }
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Debts_Manger) {
            Debts_Manger data = (Debts_Manger) o;
            if (data != null) {
                DismissDialog();
                listview.onRefreshComplete();
                if (data.getData().getTotal() != 0) {
                    List<Debts_Manger.DataBean.ItemsBean> items = data.getData().getItems();
                    if (items.size() != 0) {
                        DebtManerAdapter debtManerAdapter = new DebtManerAdapter(getActivity(), items);
                        listview.setAdapter(debtManerAdapter);
                        debtManerAdapter.notifyDataSetChanged();
                        displayData(data);
                    } else {
                        ToastUtil.showShort(getActivity(), "暂无更多债事");
                    }
                }else{
                    if(status!=NORMAL)
                    ToastUtil.showShort(getActivity(), "暂无已解债数据");
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        listview.onRefreshComplete();
        ToastUtil.showShort(getActivity(), "服务器繁忙,请稍后再试");
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status = REFRESH;
        resquestDebt_Manger();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status = LOADMORE;
        resquestDebt_Manger();
    }
}
