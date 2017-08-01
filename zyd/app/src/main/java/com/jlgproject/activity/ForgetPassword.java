package com.jlgproject.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.UpDatePasswordInfo;
import com.jlgproject.model.Yzm;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.CountdownTimer_utils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * 忘记密码
 */
public class ForgetPassword extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private TextView mTv_Title_forget;
    private ImageView mIv_Title_left_forget;
    private LinearLayout mLl_ivParent_forget;
    private Button mBt_forget_next, btn_yzm;
    private EditText mEt_forget_phone, mEt_forget_yzm, mNew_password;
    private Handler handler = new Handler();

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_forget = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_forget.setText(getResources().getText(R.string.zhaohui_password));
        mIv_Title_left_forget = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_forget.setVisibility(View.VISIBLE);
        mLl_ivParent_forget = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_forget.setOnClickListener(this);
        mBt_forget_next = (Button) findViewById(R.id.bt_forget_next);
        mBt_forget_next.setOnClickListener(this);

        btn_yzm = (Button) findViewById(R.id.btn_yzm);
        btn_yzm.setOnClickListener(this);
        mEt_forget_phone = (EditText) findViewById(R.id.et_forget_phone);
        mEt_forget_yzm = (EditText) findViewById(R.id.et_forget_yzm);
        mNew_password = (EditText) findViewById(R.id.new_password);
    }

    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_forget) {//返回
            finish();
        } else if (v == mBt_forget_next) {//保存
            save();
        } else if (v == btn_yzm) {//获取验证码
            if (!TextUtils.isEmpty(mEt_forget_phone.getText().toString())) {
                if(mEt_forget_phone.getText().length()==11){
                    new Thread(new CountdownTimer_utils(60,handler,btn_yzm,getResources().getDrawable(R.drawable.countimer), getResources().getDrawable(R.drawable.delete))).start();
                    HttpMessageInfo<Yzm> info = new HttpMessageInfo<>(BaseUrl.YZM, new Yzm());
                    info.setiMessage(this);
                    GetParmars parmars = new GetParmars();
                    parmars.add("mobile", mEt_forget_phone.getText().toString());
                    OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, null, 1);
                } else {
                    ToastUtil.showShort(this, "请正确输入手机号");
                }

            } else {
                ToastUtil.showShort(this, "请输入手机号");
            }
        }
    }

    private void save() {
        if (TextUtils.isEmpty(mEt_forget_phone.getText().toString())) {
            ToastUtil.showShort(this, "请输入手机号");
            return;
        }
        if (mEt_forget_phone.getText().length() != 11) {
            ToastUtil.showShort(this, "请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(mEt_forget_yzm.getText().toString())) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }
        if (mEt_forget_yzm.getText().length() != 6) {
            ToastUtil.showShort(this, "验证码输入错误");
            return;
        }
        if (TextUtils.isEmpty(mNew_password.getText().toString())) {
            ToastUtil.showShort(this, "请输入新密码");
            return;
        }
        if (mNew_password.getText().length() < 6 || mNew_password.getText().length() > 18) {
            ToastUtil.showShort(this, "密码长度在6—18为之间");
            return;
        }
        String phone = mEt_forget_phone.getText().toString();
        String yzm = mEt_forget_yzm.getText().toString();
        String pass = mNew_password.getText().toString();
        UpDatePasswordInfo date = new UpDatePasswordInfo(phone, yzm, pass);
        upDatePassword(date);
    }

    private void upDatePassword(UpDatePasswordInfo date) {
        HttpMessageInfo<Yzm> info = new HttpMessageInfo<>(BaseUrl.UPDATE_PASSWOED, new Yzm());
        info.setiMessage(this);
        info.setFormBody(RequestBody.create(ConstUtils.JSON, JsonUtil.toJson(date)));
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, null,1);
    }

    @Override
    public void getServiceData(Object o) {
        if(o instanceof Yzm){
            Yzm yam = (Yzm) o;
            if (yam.getMessage().equals("验证码错误")) {
                ToastUtil.showShort(this, yam.getMessage());
            } else if (yam.getMessage().equals("密码修改成功")) {
                ToastUtil.showShort(this, yam.getMessage());
                finish();
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        ToastUtil.showShort(this, "系统繁忙，请稍后再试");
    }
}
