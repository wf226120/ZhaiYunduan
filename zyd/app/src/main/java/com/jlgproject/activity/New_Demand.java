package com.jlgproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.AddDemands;
import com.jlgproject.model.Demand;
import com.jlgproject.model.Demand_Informations;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.DemandVo;
import com.jlgproject.model.Yzm;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/5/8.
 * 新增需求的的activity
 */

public class New_Demand extends BaseActivity implements HttpMessageInfo.IMessage, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private String titlename;
    private TextView tv_title;
    private AlertDialog builder;
    private Button btn_save;
    private EditText ass_name;
    private RadioGroup gr;
    private EditText total;
    private EditText count;
    private RadioButton wRbutton,yRbutton;
    private int state;
    private String name;
    private String toal_vole;
    private String shuliang;
    private Long id;
    private AddDemands demands;
    private Demand_Informations.DataBean.ItemsBean itemsBean;
    private ImageView iv_left;
    private String name1;

    @Override
    public int loadWindowLayout() {
        return R.layout.new_demand1;
    }

    public void customDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.customdolig, null);
        //得到当前显示设备的宽度，单位是像素
        int width = getWindowManager().getDefaultDisplay().getWidth();
        builder = DialogUtils.getBuilder(view, this, width);
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
    public void initDatas() {
        super.initDatas();

        Intent intent = getIntent();
        titlename = intent.getStringExtra("titlename");
        id = intent.getLongExtra("id", 0);
        itemsBean = (Demand_Informations.DataBean.ItemsBean) intent.getSerializableExtra("demandBean");
        if (itemsBean!=null){
            name1 = itemsBean.getName();
        }

    }


    @Override
    public void initViews() {
        super.initViews();
        tv_title = (TextView) findViewById(R.id.tv_title_name);
        if (titlename!=null){
            tv_title.setText(titlename);
        }else{
            tv_title.setText(name1);
        }



        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        findViewById(R.id.not_assets);
        ass_name = (EditText) findViewById(R.id.assets_name);//资产名称
        gr = (RadioGroup) findViewById(R.id.radio_type);//资产性质分类
        gr.setOnCheckedChangeListener(this);
        yRbutton= (RadioButton) findViewById(R.id.have_assets);
        wRbutton= (RadioButton) findViewById(R.id.not_assets);
        total = (EditText) findViewById(R.id.et_total_vlue);//总价值
        count = (EditText) findViewById(R.id.et_count);//数量
        btn_save = (Button) findViewById(R.id.btn_save);//保存
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initEditData();
            }
        });


        //编辑 进行页面赋值
        if (itemsBean != null) {
            ass_name.setText(itemsBean.getName().toString());
            int tang = itemsBean.getTangible();
            if (tang == 1) {
                yRbutton.setChecked(true);
            }else if (tang==0){
                wRbutton.setChecked(true);
            }
            count.setText(itemsBean.getAssetNum()+"");
            total.setText(itemsBean.getTotalAmout()+"");
        }
    }

    @Override
    public void onClick(View v) {
        if (v == iv_left) {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == gr) {
            if (checkedId == R.id.not_assets) {//无形资产
                state = 0;
            } else if (checkedId == R.id.have_assets) {//有形资产
                state = 1;
            }
        }
    }

    private void initEditData() {

        name = ass_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShort(this, "请输入资产名称");
            return;
        }
        toal_vole = total.getText().toString().trim();
        if (TextUtils.isEmpty(toal_vole)) {
            ToastUtil.showShort(this, "请输入资产价值");
            return;
        }
        shuliang = count.getText().toString().trim();
        if (TextUtils.isEmpty(shuliang)) {
            ToastUtil.showShort(this, "请输入需求数量");
            return;
        }
        if(itemsBean!=null){//编辑

            Demand demand=new Demand();
            demand.setName(name);
            demand.setId(itemsBean.getId());
            demand.setAssetNum(Integer.parseInt(shuliang));
            demand.setTangible(state);
            demand.setTotalAmout(Long.parseLong(toal_vole));
            String s = JsonUtil.toJson(demand);
            if(ShowDrawDialog(this)){
                HttpMessageInfo<Yzm> data = new HttpMessageInfo<>(BaseUrl.EDITOR_DEMAND, new Yzm());
                data.setiMessage(this);
                data.setFormBody(RequestBody.create(ConstUtils.JSON, s));
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization",UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, data, null, headers,1);
            }

        }else{//新增
            DemandVo demandVo = new DemandVo();
            demandVo.setDebtId(id);
            demandVo.setName(name);
            demandVo.setTangible(state);
            demandVo.setTotalAmout(toal_vole);
            demandVo.setDemandNum(shuliang);
            String s = JsonUtil.toJson(demandVo);
            if (ShowDrawDialog(this)){
                HttpMessageInfo<AddDemands> data = new HttpMessageInfo<>(BaseUrl.ADD_DEMAND, new AddDemands());
                data.setiMessage(this);
                data.setFormBody(RequestBody.create(ConstUtils.JSON, s));
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, data, null, headers, 1);
            }
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof AddDemands) {//新增需求
            demands = (AddDemands) o;
            if(demands!=null){
                DismissDialog();
                if(demands.getState().equals("ok")){
                    customDialog();

                } else {
                    ToastUtil.showShort(this, demands.getMessage());
                }
            }
        }else if (o instanceof Yzm){//编辑需求
            Yzm yzm= (Yzm) o;
            if(yzm!=null){
                DismissDialog();
                if (yzm.getState().equals("ok")){
                    ToastUtil.showShort(this,yzm.getMessage());
                    customDialog();
                }
            }


        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "网络繁忙");
    }


}
