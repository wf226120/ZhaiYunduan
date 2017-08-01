package com.jlgproject.fragment;

import android.os.Handler;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jlgproject.R;
import com.jlgproject.adapter.Buness_Video_Adapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Video_List_Bean;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/7/18.
 * 名师讲堂
 */

public class Video_Teacher_lecture extends BaseFragment implements View.OnClickListener, HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    private PullToRefreshGridView video_grid;
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
    private Video_List_Bean video_list_bean;
    private List<Video_List_Bean.DataBean.ItemsBean> items;
    private Buness_Video_Adapter adapter;

    @Override
    public int getLoadViewId() {
        return R.layout.teacher_dlegant_demeanour;
    }

    @Override
    public void initDatas() {
        super.initDatas();

    }

    private void requestVideoList() {
        if (ShowDrawDialog(getActivity())) {
            HttpMessageInfo<Video_List_Bean> info = new HttpMessageInfo<>(BaseUrl.VIDEO_LIST, new Video_List_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("videoId", "名师讲堂");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public View initView(View view) {
        video_grid = (PullToRefreshGridView) view.findViewById(R.id.video_grid);
        video_grid.setOnRefreshListener(this);
        video_grid.setMode(PullToRefreshBase.Mode.BOTH);
        requestVideoList();

        return view;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Video_List_Bean && o != null) {
            video_list_bean = (Video_List_Bean) o;
            if (video_grid != null) {
                DismissDialog();
                video_grid.onRefreshComplete();
                if (video_list_bean.getState().equals("ok")) {
                    displayData(video_list_bean);
                } else {
                    ToastUtil.showShort(getActivity(), video_list_bean.getMessage());
                }
            }
        }
    }



    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        video_grid.onRefreshComplete();
        ToastUtil.showLong(getActivity(), "服务器繁忙，请稍后再试");
    }


    //抽取的展示数据的方法
    private void displayData(final Video_List_Bean info) {

        if (info != null) {

            if (items == null) {
                items = info.getData().getItems();
                adapter = new Buness_Video_Adapter(getActivity(), items);
                video_grid.setAdapter(adapter);
            } else if (status == REFRESH) {
                items.clear();
                items = info.getData().getItems();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                List<Video_List_Bean.DataBean.ItemsBean> items2 = info.getData().getItems();
                items.addAll(items2);
                adapter.setItems(items);
                adapter.notifyDataSetChanged();

            }
        }
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestVideoList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestVideoList();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
