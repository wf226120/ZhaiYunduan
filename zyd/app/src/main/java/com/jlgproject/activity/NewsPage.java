package com.jlgproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.NewsAdapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debts_Manger;
import com.jlgproject.model.News;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.view.HomeListView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class NewsPage extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    //动态设置标题
    private TextView mTv_Title_news;
    private ImageView mIv_Title_left_news;
    private LinearLayout mLl_ivParent_title_news;
    private NewsAdapter mNewsAdapter;//首页ListView 的 适配器
    //数据源
    private PullToRefreshListView homeListView;
    List<News.DataBean.NewsBean> newsBeen;
    private int pn = 1;
    private int ps = 8;
    private int status = NORMAL;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int num;//集合条目数量


    @Override
    public int loadWindowLayout() {
        return R.layout.activity_news_page;
    }

    @Override
    public void initViews() {

        //动态设置标题
        mTv_Title_news = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_news.setText(getResources().getText(R.string.news_page));
        mIv_Title_left_news = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_news.setVisibility(View.VISIBLE);
        mLl_ivParent_title_news = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_news.setOnClickListener(this);

        homeListView = (PullToRefreshListView) findViewById(R.id.listview);
        homeListView.setOnRefreshListener(this);
        homeListView.setMode(PullToRefreshBase.Mode.BOTH);
        mNewsAdapter = new NewsAdapter();
        mNewsAdapter.setContext(NewsPage.this);
        getNewsMessage();
    }

    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_title_news) {//返回
            finish();
        }
    }


    private void getNewsMessage() {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<News> info = new HttpMessageInfo<News>(BaseUrl.GENG_NEWS_2, new News());
            info.setiMessage(this);
            GetParmars getParmars = new GetParmars();
            getParmars.add("pn", 1);
            getParmars.add("ps", 8);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, getParmars, null, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        News news = (News) o;
        if (news != null) {
            DismissDialog();
            homeListView.onRefreshComplete();
            if (news.getState().equals("ok")) {

                if (newsBeen == null) {
                    newsBeen = news.getData().getNews();
                    num=newsBeen.size();
                    mNewsAdapter.setmHomeList(newsBeen);
                    homeListView.setAdapter(mNewsAdapter);
                } else {
                    if (status == REFRESH) {
                        newsBeen.clear();
                        newsBeen = news.getData().getNews();
                        if (newsBeen != null) {
                            num=newsBeen.size();
                            mNewsAdapter.setmHomeList(newsBeen);
                            mNewsAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showShort(this, "已是最新");
                        }

                    } else if (status == LOADMORE) {
                        int size = newsBeen.size();
                        if (size != 0) {
                            if (size ==num || size<num) {
                                ToastUtil.showShort(this, "无更多数据");
                            } else {
                                List<News.DataBean.NewsBean> items1 = news.getData().getNews();
                                newsBeen.addAll(items1);
                                mNewsAdapter.setmHomeList(newsBeen);
                                mNewsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            ToastUtil.showShort(this, "无更多数据");
                        }
                    }
                }



            }else{
                ToastUtil.showShort(this,news.getMessage());
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        homeListView.onRefreshComplete();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status = REFRESH;
        getNewsMessage();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status = LOADMORE;
        getNewsMessage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn=1;
        ps=8;
    }
}
