package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.util.ActivityCollector;

public class PaySuccess extends BaseActivity implements View.OnClickListener{

    private TextView mTv_Title_pay;
    private ImageView mIv_Title_left_pay,mIv_title_right;
    private LinearLayout mLl_ivParent_pay;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_pay_success;
    }

    @Override
    public void initViews() {
        super.initViews();
        //动态设置标题
        mTv_Title_pay = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_pay.setText(getResources().getText(R.string.toPayS));
        mIv_Title_left_pay = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_pay.setVisibility(View.VISIBLE);
        mLl_ivParent_pay = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_pay.setOnClickListener(this);
        mIv_title_right= (ImageView) findViewById(R.id.iv_title_right);
        mIv_title_right.setImageResource(R.mipmap.return_home);
        mIv_title_right.setVisibility(View.VISIBLE);
        mIv_title_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_pay){
            finish();
        }else if(v==mIv_title_right){
            ActivityCollector.startA(PaySuccess.this, MainActivity.class,"currMenu",0);
        }
    }
}
