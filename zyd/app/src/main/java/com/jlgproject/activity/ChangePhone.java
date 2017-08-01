package com.jlgproject.activity;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
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
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.Yzm;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.CountdownTimer_utils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;


/**
 * Created by sunbeibei on 2017/5/19.
 * 更改手机号
 */

public class ChangePhone extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private ImageView title_left;
    private TextView title;
    private Button button, btn_save;
    private int indext = 60;
    private Handler handler = new Handler();
    private EditText phone_number, et_yzm;
    private String phone;//手机号
    private String yzm;//验证码
    private LinearLayout change;


    @Override
    public int loadWindowLayout() {
        return R.layout.change_phone;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    public void initViews() {
        super.initViews();
        change = (LinearLayout) findViewById(R.id.prgress_change_phone);
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText("更换手机号");
        title_left.setImageResource(R.mipmap.back);
        title_left.setVisibility(View.VISIBLE);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phone_number = (EditText) findViewById(R.id.phone_number);//手机号
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(this);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    private void timerUtils() {
        new Thread(new CountdownTimer_utils(60, handler, button,getResources().getDrawable(R.drawable.countimer),getResources().getDrawable(R.drawable.delete))).start();
        HttpMessageInfo<Yzm> info = new HttpMessageInfo<>(BaseUrl.YZM, new Yzm());
        info.setiMessage(this);
        GetParmars parmars = new GetParmars();
        parmars.add("mobile", phone_number.getText().toString().trim());
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, null, 1);
    }


    @Override
    public void onClick(View v) {
        if (button == v) {
            if (!TextUtils.isEmpty(phone_number.getText().toString().trim())) {
                phone = phone_number.getText().toString().trim();
                if(phone.length()==11){
                    timerUtils();
                } else {
                    ToastUtil.showShort(this, "请输入正确的手机号");
                }

            } else {
                ToastUtil.showShort(this, "请输入手机号");
            }
        } else if (btn_save == v) {
            change.setVisibility(View.VISIBLE);
            if (et_yzm.getText().toString().trim() != null) {
                Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
                HttpMessageInfo<Yzm> info = new HttpMessageInfo<Yzm>(BaseUrl.MY_PHONE, new Yzm());
                info.setiMessage(this);
                RequestBody requestBody = new FormBody.Builder()
                        .add("mobile", phone)
                        .add("validcode", et_yzm.getText().toString().trim())
                        .build();
                info.setFormBody(requestBody);
                if (login != null) {
                    AddHeaders headers = new AddHeaders();
                    headers.add("Authorization", login.getData().getToken());
                    OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
                }
            } else {
                ToastUtil.showShort(this, "请输入验证码");
            }
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Yzm) {
            Yzm yzm = (Yzm) o;
            if (yzm.getState().equals("ok")) {
                change.setVisibility(View.GONE);
                if (yzm.getMessage().equals("短信发送成功!")) {
                    ToastUtil.showShort(this, yzm.getMessage());
                } else {
                    ToastUtil.showShort(this,yzm.getMessage());
                    EventBus.getDefault().postSticky("1");
                    ActivityCollector.startA(this, MainActivity.class, "currMenu", 3);
                    finish();
                }
            } else if (yzm.getState().equals("warn")) {
                change.setVisibility(View.GONE);
                ToastUtil.showShort(this, yzm.getMessage().toString());
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }
}
