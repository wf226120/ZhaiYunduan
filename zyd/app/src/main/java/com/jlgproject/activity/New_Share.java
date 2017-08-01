package com.jlgproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Add_Shares;
import com.jlgproject.model.EquityVo;
import com.jlgproject.model.EquitydebtVo;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/5/9.
 * 股权信息新增
 */

public class New_Share extends BaseActivity implements HttpMessageInfo.IMessage, View.OnClickListener {

    private Button button;
    private ImageView iv_left;
    private String titlename;
    private TextView title;
    private long id;
    private String type;
    private EditText share_name;
    private EditText icard;
    private EditText adress;
    private EditText touzijine;
    private EditText bile;
    private EditText ziben;
    private EditText shidaoziben;
    private String name;
    private String zhengjianhao;
    private String dizhi;
    private String jine;
    private String touzibili;
    private String zhuceziben;
    private String et_shidao;

    @Override
    public int loadWindowLayout() {
        return R.layout.new_share;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        titlename = getIntent().getStringExtra("titlename");
        id = getIntent().getLongExtra("id", 0);
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    public void initViews() {
        super.initViews();
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText(titlename);
        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_left.setOnClickListener(this);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);

        //股东名称
        share_name = (EditText) findViewById(R.id.et_share_name);
        //股东证件号
        icard = (EditText) findViewById(R.id.et_icard);
        //地址
        adress = (EditText) findViewById(R.id.et_adress);
        //投资金额
        touzijine = (EditText) findViewById(R.id.et_acount);
        //投资比例
        bile = (EditText) findViewById(R.id.et_bili);
        //注册资本
        ziben = (EditText) findViewById(R.id.et_ziben);
        //实到资本
        shidaoziben = (EditText) findViewById(R.id.et_shidaoziben);

        button = (Button) findViewById(R.id.btn_save);
        button.setOnClickListener(this);


    }

    private void initEvent() {
        name = share_name.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(this, "请填写股东名称");
            return;
        }
        zhengjianhao = icard.getText().toString();
        if (TextUtils.isEmpty(zhengjianhao)) {
            ToastUtil.showShort(this, "请填写股东证件号");
            return;
        }
        dizhi = adress.getText().toString();
        if (TextUtils.isEmpty(dizhi)) {
            ToastUtil.showShort(this, "请填写地址");
            return;
        }
        jine = touzijine.getText().toString();
        if (TextUtils.isEmpty(jine)) {
            ToastUtil.showShort(this, "请填写投资金额");
            return;
        }
        touzibili = bile.getText().toString();

        if (TextUtils.isEmpty(touzibili)) {
            ToastUtil.showShort(this, "请填写投资比例");
            return;
        }
        zhuceziben = ziben.getText().toString();
        if (TextUtils.isEmpty(zhuceziben)) {
            ToastUtil.showShort(this, "请填写注册资本");
            return;
        }
        et_shidao = shidaoziben.getText().toString();

        if (TextUtils.isEmpty(et_shidao)) {
            ToastUtil.showShort(this, "请填写实到资本");
            return;
        }

    }

    private void requestNewShare() {

        EquityVo equityVo = new EquityVo();
        equityVo.setShareholderName(name);
        equityVo.setShareholderCode(zhengjianhao);
        equityVo.setAddress(dizhi);
        equityVo.setAmount(jine);
        equityVo.setProportion(touzibili);
        equityVo.setRegisteredCapital(zhuceziben);
        equityVo.setActualCapital(et_shidao);
        EquitydebtVo equitydebtVo = new EquitydebtVo();
        equitydebtVo.setDebtid(id + "");
        equitydebtVo.setEquityVo(equityVo);
        String s = JsonUtil.toJson(equitydebtVo);
        AddHeaders headers = new AddHeaders();
        headers.add("Authorization", UserInfoState.getToken());
        HttpMessageInfo<Add_Shares> info = new HttpMessageInfo<>(BaseUrl.NEW_SHARES, new Add_Shares());
        info.setiMessage(this);
        info.setFormBody(RequestBody.create(ConstUtils.JSON, s));
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);


    }

    public void customDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.customdolig, null);
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setView(view);
        builder.show();
        //得到当前显示设备的宽度，单位是像素
        int width = getWindowManager().getDefaultDisplay().getWidth();
        //得到这个dialog界面的参数对象
        WindowManager.LayoutParams params = builder.getWindow().getAttributes();
        //设置dialog的界面宽度
        params.width = width - (width / 6);
        //设置dialog高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的重心
        params.gravity = Gravity.CENTER;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        builder.getWindow().setAttributes(params);
        Button button = (Button) view.findViewById(R.id.confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
                finish();
            }
        });

    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Add_Shares) {
            Add_Shares share = (Add_Shares) o;
            if(share!=null){
                DismissDialog();
                if (share.getState().equals("ok")) {
                    customDialog();
                } else {
                    ToastUtil.showShort(this, "添加股权失败,请重试");
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_save:
                initEvent();
                if (ShowDrawDialog(this)) {
                    requestNewShare();
                }
                break;
        }
    }
}
