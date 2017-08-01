package com.jlgproject.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Preson_Details;
import com.jlgproject.activity.Details_Asserts_Information;
import com.jlgproject.adapter.Asset_Information_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Asserts_Infromations;
import com.jlgproject.model.eventbusMode.AssetEvent;
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
 * Created by sunbeibei on 2017/5/6.
 * 资产信息
 */

public class Asset_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {


    private PullToRefreshListView listView;

    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private List<Asserts_Infromations.DataBean.ItemsBean> items;
    private Asset_Information_Adapter asset_info_adapter;
    private String name;
    private Long grId;
    private String grType;
    private long qyId;
    private String qyType;
    private int shua;


    @Override
    public int getLoadViewId() {
        return R.layout.asset_information;
    }

    @Override
    public View initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        status=NORMAL;
        int pp=1;
        ++pp;
        L.e("--------资产信息---执行-----"+pp);
        requestAssertInformation();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Asserts_Infromations.DataBean.ItemsBean itemsBean = items.get(position - 1);
        Long id2 = itemsBean.getId();
        startActivity(new Intent(getActivity(), Details_Asserts_Information.class).putExtra("id", id2).putExtra("indext", 1).putExtra("name", name));
    }


    @Override
    public void initDatas() {
        grId = Debt_Matter_Management_Preson_Details.id;
        name = Debt_Matter_Management_Preson_Details.name;
        grType = Debt_Matter_Management_Preson_Details.tyep;

    }


    private void requestAssertInformation() {
        if (ShowDrawDialog(getActivity())) {
            GetParmars parmars = new GetParmars();
            parmars.add("debtId", grId);
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Asserts_Infromations> info = new HttpMessageInfo<>(BaseUrl.ASSERT_INFORMATION, new Asserts_Infromations());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }

    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Asserts_Infromations && o != null) {
            Asserts_Infromations infromations = (Asserts_Infromations) o;
            DismissDialog();
            if (infromations != null) {
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
        ToastUtil.showShort(getActivity(), "服务器繁忙，请稍后再试");
    }




    //抽取的展示数据的方法
    private void displayData(final Asserts_Infromations info) {
        int total = info.getData().getTotal();
        if (total == 0) {
            ToastUtil.showShort(getActivity(), "暂无资产信息");
        } else {

            if (status == NORMAL) {
                items = info.getData().getItems();
                asset_info_adapter = new Asset_Information_Adapter(getActivity());
                asset_info_adapter.setItems(items);
                listView.setAdapter(asset_info_adapter);
            } else {

                if (status == REFRESH) {
                    items.clear();
                    items = info.getData().getItems();
                    asset_info_adapter.setItems(items);
                    listView.setAdapter(asset_info_adapter);
                } else if (status == LOADMORE) {
                    int size = items.size();
                    if (size < 8) {
                        asset_info_adapter.setItems(items);
                        listView.setAdapter(asset_info_adapter);
                        asset_info_adapter.notifyDataSetChanged();
                    } else {
                        List<Asserts_Infromations.DataBean.ItemsBean> items2 = info.getData().getItems();
                        items.addAll(items2);
                        asset_info_adapter.setItems(items);
                        listView.setAdapter(asset_info_adapter);
                        asset_info_adapter.notifyDataSetChanged();
                    }
                }
            }
        }


    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestAssertInformation();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestAssertInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn=1;
        ps=8;
        shua=0;
    }
}

