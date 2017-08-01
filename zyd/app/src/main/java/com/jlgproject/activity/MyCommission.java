package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

/**
 * 我的佣金
 */
public class MyCommission extends BaseActivity implements View.OnClickListener {

    private TextView mTv_Title_comm;
    private ImageView mIv_Title_left_comm;
    private LinearLayout mLl_ivParent_title_Wallet;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_commission;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_comm = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_comm.setText(getResources().getText(R.string.my_commission));
        mIv_Title_left_comm = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_comm.setVisibility(View.VISIBLE);
        mLl_ivParent_title_Wallet = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_Wallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_title_Wallet) {
            finish();
        }
    }
}
