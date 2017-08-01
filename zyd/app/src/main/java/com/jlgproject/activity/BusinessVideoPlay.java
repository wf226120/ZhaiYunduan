package com.jlgproject.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.BusinessVideoPlayAdapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Video_List_Bean;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;
import com.jlgproject.view.CustomView.MyJCVideoPlayerStandard;

import java.io.IOException;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCUserAction;
import fm.jiecao.jcvideoplayer_lib.JCUserActionStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;


public class BusinessVideoPlay extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage, AdapterView.OnItemClickListener
      ,PullToRefreshBase.OnRefreshListener2{

    MyJCVideoPlayerStandard myJCVideoPlayerStandard;
    private PullToRefreshListView lv_b_v;
    private TextView tv_b_v_title, tv_b_v_jianjie, tv_b_v_time;//标题,简介,时间
    private String url;
    // 1.设置几个状态码方便我们进行状态的判断
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private int pn = 1;
    private int ps = 8;
    private Video_List_Bean video_list_bean;
    private BusinessVideoPlayAdapter buness_video;
    private List<Video_List_Bean.DataBean.ItemsBean> items;
    private String path;
    private String title;
    private String sutitle;
    private String time;

    @Override
    public int loadWindowLayout() {
        return R.layout.business_video;
    }

    @Override
    public void initDatas() {

        Intent intent = getIntent();
        url = intent.getStringExtra("video");
        title = intent.getStringExtra("title");
        sutitle = intent.getStringExtra("sutitle");
        time = intent.getStringExtra("time");
    }

    @Override
    public void initViews() {

        lv_b_v = (PullToRefreshListView) findViewById(R.id.listview);
        lv_b_v.setOnItemClickListener(this);
        lv_b_v.setOnRefreshListener(this);
        lv_b_v.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        View header = getLayoutInflater().inflate(R.layout.video_header,lv_b_v, false);
        header.setLayoutParams(layoutParams);
        ListView lv = lv_b_v.getRefreshableView();
        lv.addHeaderView(header);

        tv_b_v_title = (TextView) header.findViewById(R.id.tv_b_v_title);//标题
        tv_b_v_jianjie = (TextView) header.findViewById(R.id.tv_b_v_jianjie);//简介
        tv_b_v_time = (TextView) header.findViewById(R.id.tv_b_v_time);//时间
        myJCVideoPlayerStandard = (MyJCVideoPlayerStandard) header.findViewById(R.id.jc_video);

//        Glide.with(this).load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360").into(myJCVideoPlayerStandard.thumbImageView);
        myJCVideoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        myJCVideoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        myJCVideoPlayerStandard.backButton.setOnClickListener(this);//返回图标点击事件
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
        requestBunessVideo();

        tv_b_v_title.setText(title);
        tv_b_v_jianjie.setText(sutitle);
        tv_b_v_time.setText(time);
    }

    private void requestBunessVideo() {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Video_List_Bean> info = new HttpMessageInfo<>(BaseUrl.BUNESS_VIDEO, new Video_List_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("type", "2");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }


    @Override
    public void onClick(View v) {
        if (v == myJCVideoPlayerStandard.backButton) {
            finish();
        }
    }




    @Override
    public void getServiceData(Object o) {
        if (o instanceof Video_List_Bean && o != null) {
            video_list_bean = (Video_List_Bean) o;
            if(video_list_bean!=null){
                lv_b_v.onRefreshComplete();
                DismissDialog();
                SharedUtil.getSharedUtil().putObject(this, ConstUtils.VIDEO_LISTS, video_list_bean);
                if (video_list_bean.getState().equals("ok")) {
                    displayData(video_list_bean);
                } else {
                    ToastUtil.showShort(this, video_list_bean.getMessage());
                }
            }
        }
    }

    //抽取的展示数据的方法
    private void displayData(final Video_List_Bean manger) {

        if (items == null) {
            items = manger.getData().getItems();
            buness_video = new BusinessVideoPlayAdapter(BusinessVideoPlay.this, items);
            lv_b_v.setAdapter(buness_video);
            buness_video.notifyDataSetChanged();

        } else {

            if (status == REFRESH) {
                L.e("--------刷新——————");
                items.clear();
                buness_video.setItems(items);
                buness_video.notifyDataSetChanged();
            } else if (status == LOADMORE) {
                L.e("--------加载——————");
                if (items.size() < 8) {
                    buness_video.setItems(items);
                    buness_video.notifyDataSetChanged();
                } else {
                    List<Video_List_Bean.DataBean.ItemsBean> items2 = manger.getData().getItems();
                    items.addAll(items2);
                    buness_video.setItems(items);
                    buness_video.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "网络异常，请稍后再试");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Video_List_Bean.DataBean.ItemsBean itemsBean = items.get(position-2);
        path = itemsBean.getUrl();
        tv_b_v_title.setText(itemsBean.getTitle());
        tv_b_v_jianjie.setText(itemsBean.getSubTitle());
        tv_b_v_time.setText(itemsBean.getUpdateTime());
        myJCVideoPlayerStandard.setUp(path, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        Glide.with(this).load("http://img4.jiecaojingxuan.com/2016/11/23/00b026e7-b830-4994-bc87-38f4033806a6.jpg@!640_360").into(myJCVideoPlayerStandard.thumbImageView);
        myJCVideoPlayerStandard.backButton.setOnClickListener(this);//返回图标点击事件
        myJCVideoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        JCVideoPlayer.setJcUserAction(new MyUserActionStandard());
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn=1;
        status=REFRESH;
        requestBunessVideo();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn=pn+1;
        status=LOADMORE;
        requestBunessVideo();
    }


    class MyUserActionStandard implements JCUserActionStandard {

        @Override
        public void onEvent(int type, String url, int screen, Object... objects) {
            switch (type) {
                case JCUserAction.ON_CLICK_START_ICON:
                    Log.i("USER_EVENT", "ON_CLICK_START_ICON" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_ERROR:
                    Log.i("USER_EVENT", "ON_CLICK_START_ERROR" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_START_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_CLICK_START_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_PAUSE:
                    Log.i("USER_EVENT", "ON_CLICK_PAUSE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_CLICK_RESUME:
                    Log.i("USER_EVENT", "ON_CLICK_RESUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_AUTO_COMPLETE:
                    Log.i("USER_EVENT", "ON_AUTO_COMPLETE" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_FULLSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_FULLSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_ENTER_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_ENTER_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_QUIT_TINYSCREEN:
                    Log.i("USER_EVENT", "ON_QUIT_TINYSCREEN" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_VOLUME" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserAction.ON_TOUCH_SCREEN_SEEK_POSITION:
                    Log.i("USER_EVENT", "ON_TOUCH_SCREEN_SEEK_POSITION" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;

                case JCUserActionStandard.ON_CLICK_START_THUMB:
                    Log.i("USER_EVENT", "ON_CLICK_START_THUMB" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                case JCUserActionStandard.ON_CLICK_BLANK:
                    Log.i("USER_EVENT", "ON_CLICK_BLANK" + " title is : " + (objects.length == 0 ? "" : objects[0]) + " url is : " + url + " screen is : " + screen);
                    break;
                default:
                    Log.i("USER_EVENT", "unknow");
                    break;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

}
