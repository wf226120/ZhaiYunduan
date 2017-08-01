package com.jlgproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.LoginInfo;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.ScreenUtil;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/4/24.
 */

public class Login extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private Button resgir, mSign_in;//注册，登录
    private TextView mTv_Title_login;
    private ImageView mIv_Title_left_login;
    private LinearLayout mLl_ivParent_title;
    private EditText mEt_phone;//手机号
    private EditText mEt_password;//密码
    private TextView mTt_login_wjmm;
    private int indexPager;

    @Override
    public int loadWindowLayout() {
        return R.layout.login;
    }

    @Override
    public void initViews() {

        //动态设置标题
        mTv_Title_login = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_login.setText(getResources().getText(R.string.string_login_btn));
        mIv_Title_left_login = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_login.setVisibility(View.VISIBLE);
        mLl_ivParent_title = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title.setOnClickListener(this);
        resgir = (Button) findViewById(R.id.regiser_in);
        resgir.setOnClickListener(this);
        mTt_login_wjmm = (TextView) findViewById(R.id.tv_login_wjmm);
        mTt_login_wjmm.setOnClickListener(this);
        mSign_in = (Button) findViewById(R.id.sign_in);
        mSign_in.setOnClickListener(this);
        mEt_phone = (EditText) findViewById(R.id.et_phone);
        mEt_password = (EditText) findViewById(R.id.et_password);
        indexPager = getIntent().getIntExtra(ConstUtils.PD, 0);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regiser_in:
                startActivity(new Intent(Login.this, Resiger.class));
                break;
            case R.id.ll_ivParent_title://返回
                if (indexPager == 1 || indexPager == 2) {
                    ActivityCollector.startA(this, MainActivity.class, "currMenu", 0);
                } else {
                    finish();
                }
                break;
            case R.id.tv_login_wjmm://忘记密码
                ActivityCollector.startA(Login.this, ForgetPassword.class);
                break;
            case R.id.sign_in://登录
                login();
                break;
        }
    }



    private void login() {

        if (TextUtils.isEmpty(mEt_phone.getText().toString())) {
            ToastUtil.showShort(this, "请输入手机号码");
            return;
        }
        if (mEt_phone.getText().toString().length() != 11) {
            ToastUtil.showShort(this, "请输入正确的手机号码");
            return;
        }

        if (TextUtils.isEmpty(mEt_password.getText().toString())) {
            ToastUtil.showShort(this, "请输入密码");
            return;
        }

        if (mEt_password.getText().length() < 6 || mEt_password.getText().length() > 18) {
            ToastUtil.showShort(this, "请输入正确的密码");
            return;
        }

        String name = mEt_phone.getText().toString();
        String pass = mEt_password.getText().toString();
        LoginInfo login = new LoginInfo(name, pass, "888888");
        if(ShowDrawDialog(this)){
            loginRequest(login);
        }
    }


    private void loginRequest(LoginInfo login) {
        String json = JsonUtil.toJson(login);
        L.e("----登录---" + json);
        RequestBody body = RequestBody.create(ConstUtils.JSON, json);
        HttpMessageInfo<Login_zud> info = new HttpMessageInfo<Login_zud>(BaseUrl.LOGIN, new Login_zud());
        info.setiMessage(this);
        info.setFormBody(body);
        AddHeaders header = new AddHeaders();
        header.add("Accept", "application/json");
        header.add("Content-Types", "application/json");
        header.add("Authorization", "");
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, header, 1);
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Login_zud) {
            Login_zud login = (Login_zud) o;
            if (login != null) {
                DismissDialog();
                if (login.getState().equals("ok")) {
                    SharedUtil.getSharedUtil().putObject(this, ConstUtils.IOGIN_INFO, login);
                    if (indexPager == 1) {
                        ActivityCollector.startA(this, MainActivity.class, "currMenu", 1);//返回债事管理
                    } else if (indexPager == 2) {
                        ActivityCollector.startA(this, MainActivity.class, "currMenu", 2);//返回债事人
                    } else if (indexPager == 3) {
                        ActivityCollector.startA(this, MyWallet.class);
                    } else if (indexPager == 4) {
                        ActivityCollector.startA(this, New_Debt_Matter_Preson.class);
                    } else {
                        ActivityCollector.startA(this, MainActivity.class, "currMenu", 0);
                    }
                    ToastUtil.showShort(this, login.getMessage());
                    SharedUtil.getSharedUtil().remove(this, ConstUtils.ESC);//删除退出状态值
                    finish();
                } else if (login.getState().equals("warn")) {
                    ToastUtil.showShort(this, login.getMessage());
                }
            }
        }
    }


    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(Login.this, "服务器繁忙");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (indexPager == 1 || indexPager == 2) {
            L.e("--------onBackPressed-------------" + indexPager);
            ActivityCollector.startA(this, MainActivity.class, "currMenu", 0);
        }
    }
}
