package com.jlgproject.fragment;

import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Details;
import com.jlgproject.adapter.Business_Infromation_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Buness_Informations_Info;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/6.
 * 经营信息
 */

public class Business_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {
    private ArrayList<String> list = new ArrayList<>();


    private PullToRefreshListView listView;
    private long id;
    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Buness_Informations_Info informations_info;
    private List<Buness_Informations_Info.DataBean.ItemsBean> items;
    private Business_Infromation_Adapter adapter;
    private List<Buness_Informations_Info.DataBean.ItemsBean> items1;

    @Override
    public int getLoadViewId() {
        return R.layout.business_information;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        id = Debt_Matter_Management_Details.id;
    }

    @Override
    public View initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        requestBunessInformation();

        return view;
    }

    private void requestBunessInformation() {
        if (ShowDrawDialog(getActivity())) {
            HttpMessageInfo<Buness_Informations_Info> info = new HttpMessageInfo<>(BaseUrl.BUNESS_INFORMATIO, new Buness_Informations_Info());
            info.setiMessage(this);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            GetParmars parmars = new GetParmars();
            parmars.add("debtId", id);
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Buness_Informations_Info) {
            informations_info = (Buness_Informations_Info) o;
            if (informations_info != null) {
                DismissDialog();
                listView.onRefreshComplete();
                if (informations_info.getState().equals("ok")) {
                    if (informations_info.getData().getItems().size() != 0) {
                        displayData(informations_info);
                    } else if (informations_info.getData().getItems().size() == 0) {
                        ToastUtil.showShort(getActivity(), "暂无经营信息");
                    }
                } else {
                    ToastUtil.showShort(getActivity(), informations_info.getMessage());
                }
            }
        }
    }

    //抽取的展示数据的方法
    private void displayData(final Buness_Informations_Info o) {


        if (items == null) {
            items = o.getData().getItems();
            adapter = new Business_Infromation_Adapter(getActivity(), items);
            listView.setAdapter(adapter);
        } else {

            if (status == REFRESH) {
                items.clear();
                items = o.getData().getItems();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                items1 = o.getData().getItems();
                items.addAll(items1);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
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
        requestBunessInformation();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestBunessInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
