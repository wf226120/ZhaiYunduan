package com.jlgproject.activity;

import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

/**
 * Created by sunbeibei on 2017/7/5.
 */

public class WebVideo extends BaseActivity {

    private String url;

    @Override
    public int loadWindowLayout() {
        return R.layout.video;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
    }

    @Override
    public void initViews() {
        super.initViews();
        //动态设置标题
        TextView mTv_Title_school = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_school.setText(getResources().getText(R.string.sxy));
        WebView mWv_sxy = (WebView) findViewById(R.id.wv_sxy);
        mWv_sxy.setWebViewClient(new WebViewClient());
        WebSettings webset = mWv_sxy.getSettings();
        webset.setSupportZoom(true);//设置支持缩放
        webset.setUseWideViewPort(true);
        webset.setLoadWithOverviewMode(true);
        mWv_sxy.loadUrl(url);

    }
}
