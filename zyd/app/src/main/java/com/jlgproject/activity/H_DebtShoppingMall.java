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
 * 债事商城
 */
public class H_DebtShoppingMall extends BaseActivity implements View.OnClickListener{

    private TextView mTv_Title_shopping;
    private ImageView mIv_Title_left_shopping;
    private LinearLayout mLl_ivParent_shopping;
    private WebView mWebView;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_h__debt_shopping_mall;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_shopping = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_shopping.setText(getResources().getText(R.string.zssc));
        mIv_Title_left_shopping = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_shopping.setVisibility(View.VISIBLE);
        mLl_ivParent_shopping = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_shopping.setOnClickListener(this);
        mWebView= (WebView) findViewById(R.id.wv_sczs);
        mWebView.setWebViewClient(new WebViewClient());
        WebSettings webset = mWebView.getSettings();
        webset.setUseWideViewPort(true);
        webset.setLoadWithOverviewMode(true);
        mWebView.loadUrl("http://wx.wdcome.com/");
    }


    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_shopping){
            finish();
        }
    }
}
