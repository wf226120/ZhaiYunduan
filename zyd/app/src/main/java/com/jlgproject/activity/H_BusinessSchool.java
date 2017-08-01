package com.jlgproject.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.H_BunessSchool_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Video_List_Bean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 商学院
 */
public class H_BusinessSchool extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage,PullToRefreshBase.OnRefreshListener2 {

    private TextView mTv_Title_school;
    private ImageView mIv_Title_left_school;
    private LinearLayout mLl_ivParent_school;
    private ImageView video_player;
    private ImageView pic_text;
    private PullToRefreshListView video_list;
    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Video_List_Bean video_list_bean;
    private H_BunessSchool_Adapter buness_video;
    private List<Video_List_Bean.DataBean.ItemsBean> items;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_h__business_school;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_school = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_school.setText(getResources().getText(R.string.sxy));
        mIv_Title_left_school = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_school.setVisibility(View.VISIBLE);
        mLl_ivParent_school = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_school.setOnClickListener(this);
        ShowDrawDialog(this);
        requestBunessVideo();

        video_list = (PullToRefreshListView) findViewById(R.id.listview);
        video_list.setOnRefreshListener(this);
        video_list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View header = getLayoutInflater().inflate(R.layout.sxy_header, video_list, false);
        header.setLayoutParams(layoutParams);
        ListView lv = video_list.getRefreshableView();
        lv.addHeaderView(header);

        video_player = (ImageView) header.findViewById(R.id.video_player);//视屏课程
        video_player.setOnClickListener(this);
        pic_text = (ImageView) header.findViewById(R.id.pic_text);//图文列表
        pic_text.setOnClickListener(this);
    }



    private void requestBunessVideo() {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Video_List_Bean> info = new HttpMessageInfo<>(BaseUrl.BUNESS_VIDEO, new Video_List_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("type", "1");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_school) {
            finish();
        } else if (v == video_player) {
            ActivityCollector.startA(this, Buness_Video_Player.class);
        } else if (v == pic_text) {
            ActivityCollector.startA(this, Buness_Pic_Text.class);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Video_List_Bean && o != null) {
            video_list_bean = (Video_List_Bean) o;
            if (video_list_bean != null) {
                DismissDialog();
                video_list.onRefreshComplete();
                SharedUtil.getSharedUtil().putObject(this, ConstUtils.VIDEO_LISTS, video_list_bean);
                if (video_list_bean.getState().equals("ok")) {
                    displayData(video_list_bean);
                } else {
                    ToastUtil.showShort(this, video_list_bean.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        video_list.onRefreshComplete();
        ToastUtil.showShort(this,"服务器繁忙，请稍后再试");
    }


    //抽取的展示数据的方法
    private void displayData(final Video_List_Bean manger) {

        if (items == null) {
            items = manger.getData().getItems();
            buness_video = new H_BunessSchool_Adapter(H_BusinessSchool.this, items);
            video_list.setAdapter(buness_video);
            buness_video.notifyDataSetChanged();

        } else {

            if (status == REFRESH) {
                L.e("--------刷新——————");
                items.clear();

                buness_video = new H_BunessSchool_Adapter(H_BusinessSchool.this, items);
                video_list.setAdapter(buness_video);
                buness_video.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                L.e("--------加载——————");
                final int size = items.size();
                if (items.size() < 8) {
                    buness_video = new H_BunessSchool_Adapter(H_BusinessSchool.this, items);
                    video_list.setAdapter(buness_video);
                    buness_video.notifyDataSetChanged();
                } else {
                    List<Video_List_Bean.DataBean.ItemsBean> items2 = manger.getData().getItems();
                    items.addAll(items2);
                    buness_video = new H_BunessSchool_Adapter(H_BusinessSchool.this, items);
                    video_list.setAdapter(buness_video);
                    buness_video.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status=REFRESH;
        requestBunessVideo();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status=LOADMORE;
        requestBunessVideo();
    }
}
