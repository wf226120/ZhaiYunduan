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
import com.jlgproject.model.Add_Buness_Informaton;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.ManageStateRequest;
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
 * 新增经营信息界面
 */

public class New_Buiness extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    private ImageView iv_left;
    private TextView title;
    private Button save;
    private String titlename;
    private Long id;
    private EditText farenname;
    private EditText lianxiphone;
    private EditText shuinum;
    private EditText adress;
    private EditText year;
    private EditText xiaoshou;
    private EditText dianfei;
    private EditText renjunzhi;
    private EditText xianyouren;
    private EditText lirunlv;
    private EditText total_shouru;
    private EditText total_investment;
    private String name;
    private String phone;
    private String shuihao;
    private String contectadress;
    private String niandu;
    private String edu;
    private String niandudianfei;
    private String zongzhi;
    private String zongshu;
    private String lirun;
    private String zongshouru;
    private String zogntouzi;

    @Override
    public int loadWindowLayout() {
        return R.layout.new_buiness;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        titlename = getIntent().getStringExtra("titlename");
        id = Debt_Matter_Management_Details.id;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }

    @Override
    public void initViews() {
        super.initViews();
        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText(titlename);
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(this);
        //法人姓名
        farenname = (EditText) findViewById(R.id.et_organization_code);
        //联系电话
        lianxiphone = (EditText) findViewById(R.id.et_enterpeise_name);
        //税号
        shuinum = (EditText) findViewById(R.id.et_legal_person_name);
        //联系地址
        adress = (EditText) findViewById(R.id.et_legal_person_icard);
        //所属年度
        year = (EditText) findViewById(R.id.et_provinces);
        //上季度销售额
        xiaoshou = (EditText) findViewById(R.id.et_industry);
        //年度电费
        dianfei = (EditText) findViewById(R.id.et_contact_phone_number);
        //年度人均总值
        renjunzhi = (EditText) findViewById(R.id.et_the_registered_capital);
        //现有人员总数
        xianyouren = (EditText) findViewById(R.id.et_email);
        //利润率
        lirunlv = (EditText) findViewById(R.id.et_qq);
        //总收入
        total_shouru = (EditText) findViewById(R.id.et_wetchcat);
        //总投资
        total_investment = (EditText) findViewById(R.id.total_investment);

    }

    private void initEvent() {
        name = farenname.getText().toString();
        if (TextUtils.isEmpty(name)) {
            show("请输入法人姓名");
            return;
        }
        phone = lianxiphone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            show("请输入联系电话");
            return;
        }
        shuihao = shuinum.getText().toString();
        if (TextUtils.isEmpty(shuihao)) {
            show("请输入税号");
            return;
        }
        contectadress = adress.getText().toString();
        if (TextUtils.isEmpty(contectadress)) {
            show("请输入联系地址");
            return;
        }
        niandu = year.getText().toString();
        if (TextUtils.isEmpty(niandu)) {
            show("请输入所属年度");
            return;
        }
        edu = xiaoshou.getText().toString();
        if (TextUtils.isEmpty(edu)) {
            show("请输入上季度销售额");
            return;
        }
        if (Long.parseLong(edu) == 0) {
            show("季度销售额不能为0");
            return;
        }
        niandudianfei = dianfei.getText().toString();
        if (TextUtils.isEmpty(niandudianfei)) {
            show("请输入年度电费");
            return;
        }
        if (Long.parseLong(niandudianfei) == 0) {
            show("年度电费不能为0");
            return;
        }
        zongzhi = renjunzhi.getText().toString();
        if (TextUtils.isEmpty(zongzhi)) {
            show("请输入年度人均总值");
            return;
        }
        if (Long.parseLong(zongzhi) == 0) {
            show("年度人均总值不能为0");
            return;
        }
        zongshu = xianyouren.getText().toString();
        if (TextUtils.isEmpty(zongshu)) {
            show("请输入现有人员总数");
            return;
        }
        if (Long.parseLong(zongshu) == 0) {
            show("人员总数不能为0");
            return;
        }
        lirun = lirunlv.getText().toString();
        if (TextUtils.isEmpty(lirun)) {
            show("请输入利润率");
            return;
        }
        if (Long.parseLong(lirun) == 0) {
            show("利润率不能为0");
            return;
        }
        zongshouru = total_shouru.getText().toString();
        if (TextUtils.isEmpty(zongshouru)) {
            show("请输入总收入");
            return;
        }
        zogntouzi = total_investment.getText().toString();
        if (TextUtils.isEmpty(zogntouzi)) {
            show("请输入总投资");
            return;
        }
        if (Long.parseLong(zogntouzi) == 0) {
            show("总投资不能为零");
            return;
        }

        ManageStateRequest manageStateRequest = new ManageStateRequest();
        manageStateRequest.setDebtId(id);
        manageStateRequest.setLegalPersonName(name);
        manageStateRequest.setPhoneNumber(phone);
        manageStateRequest.setTaxNumber(shuihao);
        manageStateRequest.setAddress(contectadress);
        manageStateRequest.setYear(niandu);
        manageStateRequest.setLastSales(edu);
        manageStateRequest.setLastElectricityBills(niandudianfei);
        manageStateRequest.setPerCapita(zongzhi);
        manageStateRequest.setEmployeeNum(zongshu);
        manageStateRequest.setProfitMargin(lirun);
        manageStateRequest.setGross(zongshouru);
        manageStateRequest.setTotalInvestment(zogntouzi);
        if (ShowDrawDialog(this)) {
            String s = JsonUtil.toJson(manageStateRequest);
            HttpMessageInfo<Add_Buness_Informaton> info = new HttpMessageInfo<>(BaseUrl.ADD_BUNESS, new Add_Buness_Informaton());
            info.setiMessage(this);
            info.setFormBody(RequestBody.create(ConstUtils.JSON, s));
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
        }

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

    private void show(String str) {
        ToastUtil.showShort(this, str);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.save:
                initEvent();
                break;
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Add_Buness_Informaton) {
            Add_Buness_Informaton add_buness_informaton = (Add_Buness_Informaton) o;
            if (add_buness_informaton != null) {
                if (add_buness_informaton.getState().equals("ok")) {
                    customDialog();
                } else {
                    show("添加失败");
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        show("服务器繁忙，请稍后再试");
    }
}
