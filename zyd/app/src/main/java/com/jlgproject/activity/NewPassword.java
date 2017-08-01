package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;

/**
 * 找回密码
 */
public class NewPassword extends BaseActivity implements View.OnClickListener{

    //设置title
    private TextView mTv_Title_newPass;
    private ImageView mIv_Title_left_newPass;
    private LinearLayout mLl_ivParent_title_newPass;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_new_password;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_newPass = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_newPass.setText(getResources().getText(R.string.zhaohui_password));
        mIv_Title_left_newPass = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_newPass.setVisibility(View.VISIBLE);
        mLl_ivParent_title_newPass = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_newPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_title_newPass){//返回
            finish();
        }
    }
}
