package com.jlgproject.fragment;

import android.view.View;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Preson_Details;
import com.jlgproject.adapter.Debt_Information_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debts_Informations;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

import static com.jlgproject.R.id.listview;

/**
 * Created by sunbeibei on 2017/5/6.
 * 债事信息
 */

public class Debts_Information extends BaseFragment implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {


    private PullToRefreshListView listView;
    private Long id;
    private int pn = 1;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Debt_Information_Adapter adapter;
    private Debts_Informations informations;
    private List<Debts_Informations.DataBean.ItemsBean> items;

    @Override
    public int getLoadViewId() {
        return R.layout.debt_manger_listview;
    }


    @Override
    public View initView(View view) {

        id = Debt_Matter_Management_Preson_Details.id;

        listView = (PullToRefreshListView) view.findViewById(listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        requestDebtsInformation();
        return view;
    }


    private void requestDebtsInformation() {
        if (ShowDrawDialog(getActivity())) {
            GetParmars parmars = new GetParmars();
            parmars.add("debtId", id);
            parmars.add("pn", pn);
            parmars.add("ps", 8);
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
            items.clear();
            items = info.getData().getItems();
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        } else if (status == LOADMORE) {
            List<Debts_Informations.DataBean.ItemsBean> items2 = info.getData().getItems();
            items.addAll(items2);
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Debts_Informations) {
            informations = (Debts_Informations) o;
            DismissDialog();
            if (informations != null) {
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
        ToastUtil.show(getActivity(), "服务器繁忙，请稍后重试", Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
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
