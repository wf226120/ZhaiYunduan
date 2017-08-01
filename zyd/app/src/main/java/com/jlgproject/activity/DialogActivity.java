package com.jlgproject.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
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
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.Yjfk;
import com.jlgproject.model.eventbusMode.ResigerBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.ScreenUtil;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class DialogActivity extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private ImageView iv_duigou;
    private Button cancel, queren;
    private int dialogIndex;
    private TextView tv_xy_title;
    private LinearLayout ll_dialog_xieyikuang;
    private EditText et_dialog_yjfk;//意见反馈
    private TextView tv_dialog_xy, tv_xy;//协议
    private int backVul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除Activity的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    public void initViews() {
        dialogActivityInit();
        iv_duigou = (ImageView) findViewById(R.id.iv_select);
        cancel = (Button) findViewById(R.id.cancel);
        queren = (Button) findViewById(R.id.queren);
        iv_duigou.setOnClickListener(this);
        cancel.setOnClickListener(this);
        queren.setOnClickListener(this);
        tv_xy_title = (TextView) findViewById(R.id.tv_xy_title);
        ll_dialog_xieyikuang = (LinearLayout) findViewById(R.id.ll_dialog_xieyikuang);
        ll_dialog_xieyikuang.setOnClickListener(this);
        findViewById(R.id.tv_dialog_xy);
        et_dialog_yjfk = (EditText) findViewById(R.id.et_dialog_yjfk);//意见反馈
        et_dialog_yjfk.setVisibility(View.GONE);
        tv_dialog_xy = (TextView) findViewById(R.id.tv_dialog_xy);//协议
        tv_xy = (TextView) findViewById(R.id.tv_xy);

        dialogIndex = getIntent().getIntExtra("dialogIndex", 0);
        if (dialogIndex == 1) {
            tv_xy_title.setText("中金债事快捷支付服务协议");
            tv_xy.setText("阅读并同意《中金债事快捷支付服务协议》");
            tv_dialog_xy.setText(Utils.zhifu_xy);
        } else if (dialogIndex == 2) {
            tv_xy_title.setText("中金债事快捷支付服务协议");
            tv_xy.setText("阅读并同意《中金债事快捷支付服务协议》");
            tv_dialog_xy.setText(Utils.zhifu_xy);
        } else if (dialogIndex == 3) {
            tv_xy_title.setText("意见反馈");
            ll_dialog_xieyikuang.setVisibility(View.GONE);
            tv_dialog_xy.setVisibility(View.GONE);
            et_dialog_yjfk.setVisibility(View.VISIBLE);
            queren.setText("提交");
            queren.setOnClickListener(this);
        } else if (dialogIndex == 4) {
            tv_xy_title.setText("债云端(注册)服务协议");
            tv_dialog_xy.setText(Utils.zhuce_xy);
        }
    }

    /**
     * ActivityDialog 初始化配置
     */
    public void dialogActivityInit() {

        WindowManager windowManager = getWindowManager();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = getWindow().getAttributes();
        // 宽度设置为屏幕的1
        p.width = (int) (ScreenUtil.getScreenWidth(DialogActivity.this) * 0.9);
        // 设置透明度,0.0为完全透明，1.0为完全不透明
        p.alpha = 0.95f;
        // 设置布局参数
        getWindow().setAttributes(p);
        // 设置点击弹框外部不可消失
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_duigou) {//点击选框
            if (SharedUtil.getSharedUtil().getBoolean(this, ConstUtils.CHECKBOX_AGREEMENT)) {
                iv_duigou.setImageResource(R.drawable.agreeno);
                SharedUtil.getSharedUtil().remove(this, ConstUtils.CHECKBOX_AGREEMENT);
            } else {
                iv_duigou.setImageResource(R.drawable.agreeyes);
                SharedUtil.getSharedUtil().putBoolean(this, ConstUtils.CHECKBOX_AGREEMENT, true);
            }
        } else if (v == ll_dialog_xieyikuang) {
            if (SharedUtil.getSharedUtil().getBoolean(this, ConstUtils.CHECKBOX_AGREEMENT)) {
                iv_duigou.setImageResource(R.drawable.agreeno);
                SharedUtil.getSharedUtil().remove(this, ConstUtils.CHECKBOX_AGREEMENT);
            } else {
                iv_duigou.setImageResource(R.drawable.agreeyes);
                SharedUtil.getSharedUtil().putBoolean(this, ConstUtils.CHECKBOX_AGREEMENT, true);
            }
        } else if (v == cancel) {//点击取消
            if (dialogIndex == 2) {
                ActivityCollector.startA(this,MainActivity.class,"currMenu",0);
            }
            finish();
            SharedUtil.getSharedUtil().putInt(this, ConstUtils.CANCEL_AGREEMENT, 1);
        } else if (v == queren) {//点击确认
            if (dialogIndex == 1 || dialogIndex == 2) {
                if (SharedUtil.getSharedUtil().getBoolean(this, ConstUtils.CHECKBOX_AGREEMENT)) {
                    SharedUtil.getSharedUtil().remove(this, ConstUtils.CHECKBOX_AGREEMENT);
                    finish();
                } else {
                    ToastUtil.showLong(DialogActivity.this, "同意协议才能使用该功能哦");
                    //如果勾选了协议且点击取消，那么让勾选值为 false
                }
            } else if (dialogIndex == 3) {//意见反馈
                String str = et_dialog_yjfk.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    setYjMessage(str);
                } else {
                    ToastUtil.showLong(DialogActivity.this, "请输入您的意见或建议");
                }
            } else if (dialogIndex == 4) {
                if (SharedUtil.getSharedUtil().getBoolean(this, ConstUtils.CHECKBOX_AGREEMENT)) {
                    SharedUtil.getSharedUtil().remove(this, ConstUtils.CHECKBOX_AGREEMENT);
                    ActivityCollector.startA(DialogActivity.this, Resiger.class);
                    EventBus.getDefault().postSticky(new ResigerBean(1));
                    finish();
                } else {
                    ToastUtil.showLong(DialogActivity.this, "同意协议才能进行注册哦");
                    //如果勾选了协议且点击取消，那么让勾选值为 false
                }
            }
        }
    }

    private void setYjMessage(String str) {
        Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
        if (login != null) {
            HttpMessageInfo<Yjfk> info = new HttpMessageInfo<Yjfk>(BaseUrl.MY_FK, new Yjfk());
            info.setiMessage(this);
            RequestBody requestBody = new FormBody.Builder()
                    .add("feedback", str)
                    .build();
            info.setFormBody(requestBody);
            AddHeaders header = new AddHeaders();
            header.add("Authorization", login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, header, 1);
        } else {
            ToastUtil.showLong(DialogActivity.this, "请先登录");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);

        }
    }


    @Override
    public void getServiceData(Object o) {

        if (o instanceof Yjfk) {
            Yjfk fk = (Yjfk) o;
            if (fk.getState().equals("ok")) {
                ToastUtil.showShort(this, fk.getMessage());
                finish();
            } else {
                ToastUtil.showShort(this, fk.getMessage());
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }
}
