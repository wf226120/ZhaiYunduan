package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

public class BankCard_Message extends BaseActivity implements View.OnClickListener{

    //动态设置标题
    private TextView mTv_Title_bank_m;
    private ImageView mIv_Title_left_bank_m;
    private LinearLayout mLl_ivParent_title_bank_m;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_bank_card__message;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_bank_m = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_bank_m.setText(getResources().getText(R.string.add_bank_card));
        mIv_Title_left_bank_m = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_bank_m.setVisibility(View.VISIBLE);
        mLl_ivParent_title_bank_m= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_bank_m.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_title_bank_m){
            finish();
        }
    }
}
