package com.jlgproject.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

/**
 * 中金天眼
 */
public class H_GoldEye extends BaseActivity implements View.OnClickListener{

    private TextView mTv_Title_h_g;
    private ImageView mIv_Title_left_h_g;
    private LinearLayout mLl_ivParent_h_g;
    private WebView wv_h_g;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_h__gold_eye;
    }

    @Override
    public void initViews() {
        super.initViews();
        //动态设置标题
        mTv_Title_h_g = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_h_g.setText(getResources().getText(R.string.zhongjin_ty));
        mIv_Title_left_h_g = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_h_g.setVisibility(View.VISIBLE);
        mLl_ivParent_h_g = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_h_g.setOnClickListener(this);
        wv_h_g= (WebView) findViewById(R.id.wv_h_g);
        WebSettings webset = wv_h_g.getSettings();
        webset.setUseWideViewPort(true);
        webset.setLoadWithOverviewMode(true);
        webset.setDomStorageEnabled(true);//webView 不显示时
        wv_h_g.setWebViewClient(new WebViewClient());
        wv_h_g.loadUrl("http://www.tianyancha.com/");
    }

    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_h_g){
            finish();
        }
    }
}
