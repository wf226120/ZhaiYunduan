package com.jlgproject.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

/**
 * Created by sunbeibei on 2017/5/25.
 */

public class Pic_Text_Details extends BaseActivity implements View.OnClickListener{

    private String url;
    private WebView view;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2,ll_ivParent_title_newsInfo;
    private TextView mTv_Title_newsInfo;
    private ImageView mIv_Title_left_newsInfo;

    @Override
    public int loadWindowLayout() {
        return R.layout.news_details;
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

        mTv_Title_newsInfo = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_newsInfo.setText("图文详情");
        mIv_Title_left_newsInfo = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_newsInfo.setVisibility(View.VISIBLE);
        ll_ivParent_title_newsInfo= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        ll_ivParent_title_newsInfo.setOnClickListener(this);
        view = (WebView) findViewById(R.id.web);
        view.setVisibility(View.VISIBLE);
        linearLayout = (LinearLayout) findViewById(R.id.line_progress);
        linearLayout2 = (LinearLayout) findViewById(R.id.webshow);
        WebSettings webSettings = view.getSettings();
        if(url!=null){
            view.loadUrl(url);
        }
        view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        view.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                 if (newProgress==100){
                       linearLayout.setVisibility(View.GONE);
                     linearLayout2.setVisibility(View.VISIBLE);
                 }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==ll_ivParent_title_newsInfo){
            finish();
        }
    }
}

