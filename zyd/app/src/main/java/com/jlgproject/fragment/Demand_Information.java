package com.jlgproject.fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Preson_Details;
import com.jlgproject.activity.Details_Demand_Information;
import com.jlgproject.adapter.Demand_Information_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Demand_Informations;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/6.
 * 需求信息
 */

public class Demand_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {

    private PullToRefreshListView listView;
    private Long id;
    private Login_zud zud;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NOMAIL = 0;
    //2.下拉刷新的状态
    private static final int REFRESH = 2;
    //3.上啦加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Handler handler = new Handler();
    private List<Demand_Informations.DataBean.ItemsBean> items;
    private String name;
    private int ps = 8;
    private int pn = 1;
    private Demand_Information_Adapter information_adapter;
    private List<Demand_Informations.DataBean.ItemsBean> itemsCopy;//用来存储列表数据
    private int itemSize;


    @Override
    public int getLoadViewId() {
        return R.layout.demand_information;
    }

    @Override
    public View initView(View view) {
        id = Debt_Matter_Management_Preson_Details.id;
        name = Debt_Matter_Management_Preson_Details.name;
        listView = (PullToRefreshListView) view.findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnItemClickListener(this);
        itemsCopy = new ArrayList<>();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Demand_Informations.DataBean.ItemsBean itemsBean = items.get(position - 1);
        Long id1 = itemsBean.getId();
        startActivity(new Intent(getActivity(), Details_Demand_Information.class).putExtra("id", id1).putExtra("name", name).putExtra("indext", 2));
    }

    private void requestDemand() {
        if (ShowDrawDialog(getActivity())) {
            GetParmars parmars = new GetParmars();
            parmars.add("debtid", id);
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Demand_Informations> info = new HttpMessageInfo<>(BaseUrl.DEMAND_INFROMATIION, new Demand_Informations());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        status = NOMAIL;
        requestDemand();
    }


    @Override
    public void getServiceData(Object o) {

        if (o instanceof Demand_Informations) {
            Demand_Informations infromations = (Demand_Informations) o;
            if (infromations != null) {
                DismissDialog();
                listView.onRefreshComplete();
                if (infromations.getState().equals("ok")) {
                    displayData(infromations);
                } else {
                    ToastUtil.showShort(getActivity(), infromations.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        listView.onRefreshComplete();
        ToastUtil.show(getActivity(), "服务器繁忙，请稍后重试", Toast.LENGTH_SHORT);
    }

    //抽取的展示数据的方法
    private void displayData(final Demand_Informations info) {

        int total = info.getData().getTotal();
        if (total == 0) {
            ToastUtil.showShort(getActivity(), "暂无需求信息");
        } else {

            if (status == NOMAIL) {
                items = info.getData().getItems();
                information_adapter = new Demand_Information_Adapter(getActivity());
                information_adapter.setItems(items);
                listView.setAdapter(information_adapter);
                itemSize = items.size();
            } else {

                if (status == REFRESH) {
                    items.clear();
                    items = info.getData().getItems();
                    information_adapter.setItems(items);
                    listView.setAdapter(information_adapter);
                } else if (status == LOADMORE) {
                    int size = items.size();
                    if (size < 8) {
                        information_adapter.setItems(items);
                        listView.setAdapter(information_adapter);
                        information_adapter.notifyDataSetChanged();
                    } else {
                        List<Demand_Informations.DataBean.ItemsBean> items2 = info.getData().getItems();
                        items.addAll(items2);
                        information_adapter.setItems(items);
                        listView.setAdapter(information_adapter);
                        information_adapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestDemand();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestDemand();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
