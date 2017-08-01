package com.jlgproject.fragment;

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
import com.jlgproject.model.eventbusMode.DebtBeiAn;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/2.
 * 未解决
 */

public class DebtMatterMangChildFragment1 extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {


    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private List<Debts_Manger.DataBean.ItemsBean> items;
    private DebtManerAdapter adapter;
    private PullToRefreshListView listView_fresh1;

    @Override
    public int getLoadViewId() {
        return R.layout.debt_manger_listview;
    }

    @Override
    public View initView(View view) {


        listView_fresh1 = (PullToRefreshListView) view.findViewById(R.id.listview);
        listView_fresh1.setOnRefreshListener(this);
        listView_fresh1.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        status = NORMAL;
        resquestDebt_Manger();
        L.e("-------债市管理-----未解决---");
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebtBeiAn(DebtBeiAn beiAn) {
        String beian = beiAn.getDebtBeiAnString();
        if (beian.equals("1")) {
            resquestDebt_Manger();//请求数据
        }
    }


    //抽取显示数据的方法；
    private void displayData(Debts_Manger data) {
        if (status == NORMAL) {
            items = data.getData().getItems();
            adapter = new DebtManerAdapter(getActivity(), items);
            listView_fresh1.setAdapter(adapter);

        } else if (status == REFRESH) {

            items.clear();
            items = data.getData().getItems();
            if (items != null) {
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            } else {
                ToastUtil.showShort(getActivity(), "暂无数据");
            }
        } else if (status == LOADMORE) {
//            List<Debts_Manger.DataBean.ItemsBean> items1 = data.getData().getItems();
//            if (items1.size() != 0) {
//                items.addAll(items1);
//                adapter.setItems(items);
//                adapter.notifyDataSetChanged();
//            }


            int size = items.size();
            if (size != 0) {
                if (items.size() < 8) {
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    L.e("-----备案--1--");
                } else {
                    List<Debts_Manger.DataBean.ItemsBean> items1 = data.getData().getItems();
                    items.addAll(items1);
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                    L.e("-----备案--2--");
                }
            }
            else {
                ToastUtil.showShort(getActivity(), "暂无数据");
            }
        }
    }

    private void resquestDebt_Manger() {
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                HttpMessageInfo<Debts_Manger> info = new HttpMessageInfo<>(BaseUrl.DEBTS_MANGER, new Debts_Manger());
                info.setiMessage(this);
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                GetParmars parmars = new GetParmars();
                parmars.add("issolution", 0);
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
                listView_fresh1.onRefreshComplete();
                if (data.getState().equals("ok")) {
                    displayData(data);
                } else {
                    ToastUtil.showShort(getActivity(), data.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        listView_fresh1.onRefreshComplete();
        DismissDialog();
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}