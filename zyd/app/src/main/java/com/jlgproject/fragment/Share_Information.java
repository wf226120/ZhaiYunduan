package com.jlgproject.fragment;

import android.os.Handler;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Details;
import com.jlgproject.adapter.Share_Information_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Share_Informations;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.jlgproject.R.id.listview;

/**
 * Created by sunbeibei on 2017/5/6.
 * 股权信息
 */

public class Share_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private PullToRefreshListView listView;
    private long id;
    private int pn = 1;
    private int ps = 8;
    private Share_Informations share_informations;
    private List<Share_Informations.DataBean.ItemsBean> items;
    private List<Share_Informations.DataBean.ItemsBean> items2;
    private Share_Information_Adapter adapter;

    @Override
    public int getLoadViewId() {
        return R.layout.share_information;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        id = Debt_Matter_Management_Details.id;
    }

    @Override
    public View initView(View view) {
        listView = (PullToRefreshListView) view.findViewById(listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        status = NORMAL;
        requestShareInformation();
    }

    private void requestShareInformation() {
        if (ShowDrawDialog(getActivity())) {
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("debtid", id);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Share_Informations> info = new HttpMessageInfo<>(BaseUrl.SHARE_INFORMATION, new Share_Informations());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    //抽取的展示数据的方法
    private void displayData(final Share_Informations o) {

        if (status == NORMAL) {
            items = o.getData().getItems();
            adapter = new Share_Information_Adapter(getActivity(), items);
            listView.setAdapter(adapter);

        } else {

            if (status == REFRESH) {
                items.clear();
                items = o.getData().getItems();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                items2 = o.getData().getItems();
                items.addAll(items2);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Share_Informations) {
            share_informations = (Share_Informations) o;
            if (share_informations != null) {
                DismissDialog();
                listView.onRefreshComplete();
                if (share_informations.getState().equals("ok")) {
                    if (share_informations.getData().getItems().size() != 0) {
                        displayData(share_informations);
                    } else {
                        ToastUtil.showShort(getActivity(), "暂无股权信息");
                    }
                } else {
                    ToastUtil.showShort(getActivity(), "查询失败");
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
        requestShareInformation();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestShareInformation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
