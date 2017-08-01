package com.jlgproject.fragment;

import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.Pic_Text_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Pic_Text_Bean;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/7/18.
 * 解债案例
 */

public class PicT_Debt_Sample extends BaseFragment implements View.OnClickListener, HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {
    private int pn = 1;
    private int ps = 8;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private PullToRefreshListView pic_text;
    private Pic_Text_Bean pic_text_bean;
    private List<Pic_Text_Bean.DataBean.ItemsBean> items;
    private Pic_Text_Adapter adapter;

    @Override
    public int getLoadViewId() {
        return R.layout.pic_debt_sample;
    }

    @Override
    public void initDatas() {
        super.initDatas();

        requestPicText();
    }

    private void requestPicText() {
        if (ShowDrawDialog(getActivity())) {
            HttpMessageInfo<Pic_Text_Bean> info = new HttpMessageInfo<>(BaseUrl.PIC_TEXT, new Pic_Text_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("videoId", "解债案例");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        } else {
            ToastUtil.showShort(getActivity(), "请登录");
        }

    }

    @Override
    public View initView(View view) {

        pic_text = (PullToRefreshListView) view.findViewById(R.id.listview);
        pic_text.setOnRefreshListener(this);
        pic_text.setMode(PullToRefreshBase.Mode.BOTH);


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Pic_Text_Bean && o != null) {
            pic_text_bean = (Pic_Text_Bean) o;
            if (pic_text_bean!=null) {
                DismissDialog();
                pic_text.onRefreshComplete();
                displayData(pic_text_bean);

            } else {
                DismissDialog();
                pic_text.onRefreshComplete();
                ToastUtil.showShort(getActivity(), pic_text_bean.getMessage());
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }

    //抽取的展示数据的方法
    private void displayData(final Pic_Text_Bean manger) {

        if (items == null) {
            items = manger.getData().getItems();
            adapter = new Pic_Text_Adapter(getActivity(),items);
            adapter.setItems(items);
            pic_text.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {

            if (status == REFRESH) {
                L.e("--------刷新——————");
                items.clear();
                items = manger.getData().getItems();
                adapter.setItems(items);
                pic_text.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                L.e("--------加载——————");

                if (items.size() < 8) {
                    adapter.setItems(items);
                    pic_text.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    List<Pic_Text_Bean.DataBean.ItemsBean> items2 = manger.getData().getItems();
                    items.addAll(items2);
                    adapter.setItems(items);
                    pic_text.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        pic_text.onRefreshComplete();
        DismissDialog();
        ToastUtil.showLong(getActivity(), "服务器繁忙，请稍后再试");
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestPicText();

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestPicText();

    }
}
