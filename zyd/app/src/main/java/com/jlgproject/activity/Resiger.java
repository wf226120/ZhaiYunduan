package com.jlgproject.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.ResigerInfo;
import com.jlgproject.model.Yzm;
import com.jlgproject.model.eventbusMode.ResigerBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.CountdownTimer_utils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/4/24.
 */

public class Resiger extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private TextView mTv_Title_resiger, mTv_resiger_xy;
    private ImageView mIv_Title_left_resiger;
    private TextView back_login;
    private ImageView image_btn;
    private boolean status = true;
    private EditText mUser_name,//姓名
            mPhone_number,//联系电话
            mEt_yangzheng,//验证码
            mEt_resiger_mima,//密码
            mConfirm_password;//确认密码
    private Button mRegiser_btn;
    private Button mYzm;//验证码按钮
    private LinearLayout ll_ivParent_title;
    private EditText icard;
    private Handler handler = new Handler();
    private EditText tjr;
    private int showIndex = 0;//弹窗返回值

    @Override
    public int loadWindowLayout() {
        return R.layout.resiger;
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        //动态设置标题
        mTv_Title_resiger = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_resiger.setText(getResources().getText(R.string.string_resgier_btn));
        mIv_Title_left_resiger = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_resiger.setVisibility(View.VISIBLE);
        image_btn = (ImageView) findViewById(R.id.image_btn);
        image_btn.setOnClickListener(this);
        mRegiser_btn = (Button) findViewById(R.id.regiser_btn);
        mRegiser_btn.setOnClickListener(this);
        mYzm = (Button) findViewById(R.id.btn_yangzheng);
        mYzm.setOnClickListener(this);
        mUser_name = (EditText) findViewById(R.id.user_name);
        mPhone_number = (EditText) findViewById(R.id.phone_number);
        mEt_yangzheng = (EditText) findViewById(R.id.et_yangzheng);
        mEt_resiger_mima = (EditText) findViewById(R.id.et_resiger_mima);
        mConfirm_password = (EditText) findViewById(R.id.confirm_password);
        ll_ivParent_title = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        ll_ivParent_title.setOnClickListener(this);
        tjr = (EditText) findViewById(R.id.et_tjr);
        mTv_resiger_xy = (TextView) findViewById(R.id.tv_resiger_xy);
        mTv_resiger_xy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_btn://勾选协议
                if (status) {
                    image_btn.setImageResource(R.drawable.agreeyes);
                    status = false;
                    L.e("------1-----" + status);
                } else if (!status) {
                    image_btn.setImageResource(R.drawable.agreeno);
                    status = true;
                    L.e("------1-----" + status);
                }
                break;
            case R.id.regiser_btn:
                regiser();
                break;
            case R.id.ll_ivParent_title://返回
                finish();
                break;
            case R.id.btn_yangzheng:
                String yzm = mPhone_number.getText().toString();
                if (!TextUtils.isEmpty(yzm) || yzm.length()== 11) {
                    getMessageInfo();
                } else {
                    ToastUtil.showShort(this, "请正确输入手机号");
                }
                break;
            case R.id.tv_resiger_xy:
                ActivityCollector.startA(this, DialogActivity.class, "dialogIndex", 4);
                break;
        }
    }

    //获取手机验证码
    private void getMessageInfo() {
        if (ShowDrawDialog(this)) {
            new Thread(new CountdownTimer_utils(60, handler, mYzm, getResources().getDrawable(R.drawable.countimer), getResources().getDrawable(R.drawable.delete))).start();
            HttpMessageInfo<Yzm> info = new HttpMessageInfo<>(BaseUrl.YZM, new Yzm());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("mobile", mPhone_number.getText().toString().trim());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, null, 1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showIndex == 1) {
            image_btn.setImageResource(R.drawable.agreeyes);
            status = false;
            L.e("------1-----" + status);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        showIndex = 0;
    }

    private void regiser() {

        if (TextUtils.isEmpty(mUser_name.getText().toString())) {
            ToastUtil.showShort(this, "请输入姓名");
            return;
        }


        if (mUser_name.getText().length() < 2) {
            ToastUtil.showShort(this, "请正确输入姓名");
            return;
        }

        if (TextUtils.isEmpty(mPhone_number.getText().toString())) {
            ToastUtil.showShort(this, "请输入正确的手机号码");
            return;
        }

        if (!(mPhone_number.getText().length() == 11)) {
            ToastUtil.showShort(this, "请输入正确的手机号码");
            return;
        }

        if (TextUtils.isEmpty(mEt_yangzheng.getText().toString())) {
            ToastUtil.showShort(this, "请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(mEt_resiger_mima.getText().toString())) {
            ToastUtil.showShort(this, "请输入密码");
            return;
        }
        if (mEt_resiger_mima.getText().length() < 6 && mEt_resiger_mima.getText().length() > 18) {
            ToastUtil.showShort(this, "密码长度为6-18位");
            return;
        }
        if (TextUtils.isEmpty(mConfirm_password.getText().toString())) {
            ToastUtil.showShort(this, "请再次输入密码");
            return;
        }
        if (mConfirm_password.getText().length() < 6 && mConfirm_password.getText().length() > 16) {
            ToastUtil.showShort(this, "密码长度为6-16位之间");
            return;
        }
        String tujiaren = "";
        if (!TextUtils.isEmpty(tjr.getText().toString())) {
            tujiaren = tjr.getText().toString();
        }
        if (status) {
            ToastUtil.showShort(this, "勾选协议才能进行注册哦！");
            return;
        }
        String name = mUser_name.getText().toString();
        String phone = mPhone_number.getText().toString();
        String yzm = mEt_yangzheng.getText().toString();
        String mima = mEt_resiger_mima.getText().toString();
        String cmima = mConfirm_password.getText().toString();
        ResigerInfo resigerInfo = new ResigerInfo(name, phone, yzm, mima, cmima, tujiaren);
        getHttpMessageRegiser(resigerInfo);
    }

    public void getHttpMessageRegiser(ResigerInfo resigerInfo) {
        if (ShowDrawDialog(this)) {
            String str = JsonUtil.toJson(resigerInfo);
            L.e("----注册信息---" + str);
            HttpMessageInfo<Yzm> info = new HttpMessageInfo<>(BaseUrl.RESIGER, new Yzm());
            info.setiMessage(this);
            info.setFormBody(RequestBody.create(ConstUtils.JSON, str));
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, null, 1);
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Yzm) {
            Yzm yzm = (Yzm) o;
            if (yzm != null) {
                DismissDialog();
                if (yzm.getState().equals("ok")) {
                    if (yzm.getMessage().equals("短信发送成功！")) {
                        ToastUtil.showShort(this, yzm.getMessage());
                    } else if (yzm.getMessage().equals("注册成功！")) {
                        ToastUtil.showShort(this, yzm.getMessage());
                        finish();
                    }
                } else {
                    ToastUtil.showShort(this, yzm.getMessage());
                }
            }
        }


    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.show(this, "服务器繁忙，请稍后再试", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getShowIndex(ResigerBean resigerBean) {
        showIndex = resigerBean.getShowIndex();
        L.e("----showIndex----" + showIndex);
    }
}
