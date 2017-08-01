package com.jlgproject.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.jlgproject.R;
import com.jlgproject.adapter.Buness_Video_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Video_List_Bean;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/7/21.
 */

public class Serch_Video extends BaseActivity implements HttpMessageInfo.IMessage, View.OnClickListener, PullToRefreshBase.OnRefreshListener2 {


    private ImageView finish_serch;

    private int pn = 1;
    private int ps = 8;
    private static final int NOMAL=0;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Handler handler = new Handler();

    private ImageView serchpic;
    private EditText serch_pivi;
    private PullToRefreshGridView gridView;
    private List<Video_List_Bean.DataBean.ItemsBean> items;
    private Buness_Video_Adapter adapter;
    private Video_List_Bean video_list_bean;
    private TextView jieguo;

    @Override
    public int loadWindowLayout() {
        return R.layout.serch_video;
    }

    @Override
    public void initDatas() {
        super.initDatas();

    }

    @Override
    public void initViews() {
        super.initViews();
        jieguo = (TextView) findViewById(R.id.jieguo_video);
        serch_pivi = (EditText) findViewById(R.id.serch_pivi);
        finish_serch = (ImageView) findViewById(R.id.finsh_back);
        finish_serch.setOnClickListener(this);
        serchpic = (ImageView) findViewById(R.id.serch_pic);
        serchpic.setOnClickListener(this);
        gridView = (PullToRefreshGridView) findViewById(R.id.video_grid);
        gridView.setOnRefreshListener(this);
        gridView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    private void requestSerchVideo() {
        String wd = serch_pivi.getText().toString();
        if (TextUtils.isEmpty(wd)) {
            ToastUtil.showShort(this, "请输入所要搜索的关键字");
            return;
        }
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Video_List_Bean> info = new HttpMessageInfo<>(BaseUrl.VIDEO_SERCH, new Video_List_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("wd", wd);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        } else {
            ToastUtil.showShort(this, "请登录");
        }

    }

    //抽取的展示数据的方法
    private void displayData(final Video_List_Bean manger) {

        if (status==NOMAL) {
            items = manger.getData().getItems();
           if (items.size()==0){
               jieguo.setVisibility(View.VISIBLE);
               gridView.setVisibility(View.GONE);
           }else{
               jieguo.setVisibility(View.GONE);
               gridView.setVisibility(View.VISIBLE);
               adapter = new Buness_Video_Adapter(this, items);
               gridView.setAdapter(adapter);
               adapter.notifyDataSetChanged();
           }


        }
            if (status == REFRESH) {
                L.e("--------刷新——————");
                items.clear();
                items = manger.getData().getItems();
                adapter.setItems(items);
                adapter.notifyDataSetChanged();
            }
            if (status == LOADMORE) {
                L.e("--------加载——————");
                    List<Video_List_Bean.DataBean.ItemsBean> items2 = manger.getData().getItems();
                    items.addAll(items2);
                    adapter.setItems(items);
                    adapter.notifyDataSetChanged();
                }
            }




    @Override
    public void getServiceData(Object o) {
        if (o instanceof Video_List_Bean && o != null) {
            video_list_bean = (Video_List_Bean) o;
            if (video_list_bean != null) {
                DismissDialog();
                gridView.onRefreshComplete();
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
        gridView.onRefreshComplete();
        ToastUtil.showLong(this, "服务器繁忙，请稍后再试");
    }

    @Override
    public void onClick(View v) {
        if (v == finish_serch) {
            finish();
        } else if (v == serchpic) {
            status=NOMAL;
            requestSerchVideo();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        requestSerchVideo();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        requestSerchVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }
}
