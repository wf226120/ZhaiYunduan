package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Details_Demand_Moldel2;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/6/27.
 */

/**
 * 需求详情
 */
public class Details_Demand_Information extends BaseActivity implements HttpMessageInfo.IMessage {

    private TextView ass_name;
    private TextView assert_type;
    private TextView total_count;
    private TextView asset_count;
    private long id;
    private String name;
    private TextView title_name;
    private LinearLayout progress;
    private ImageView title_left;

    @Override
    public int loadWindowLayout() {
        return R.layout.activiyt_demand_detail;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        id = getIntent().getLongExtra("id", 0);
        name = getIntent().getStringExtra("name");
        requestDmDetail();
    }

    @Override
    public void initViews() {
        super.initViews();
        title_name = (TextView) findViewById(R.id.tv_title_name);
        title_name.setText(name);
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title_left.setVisibility(View.VISIBLE);
        title_left.setImageResource(R.mipmap.back);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //资产名称
        ass_name = (TextView) findViewById(R.id.real_estate);
        //资产性质分类
        assert_type = (TextView) findViewById(R.id.asset_type);
        //总价值
        total_count = (TextView) findViewById(R.id.total_count);
        //资产数量
        asset_count = (TextView) findViewById(R.id.asserts_number);
        progress = (LinearLayout) findViewById(R.id.progress_demend);
        progress.setVisibility(View.VISIBLE);
    }

   private void requestDmDetail() {
       GetParmars parmars = new GetParmars();
       parmars.add("demandid",id);
       Login_zud zud = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO,null);
       AddHeaders headers = new AddHeaders();
       headers.add("Authorization", zud.getData().getToken());
       HttpMessageInfo<Details_Demand_Moldel2>info = new HttpMessageInfo<>(BaseUrl.DEMAND_DETAILS,new Details_Demand_Moldel2());
       info.setiMessage(this);
       OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET,info,parmars,headers,1);


    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Details_Demand_Moldel2){
            Details_Demand_Moldel2 data= (Details_Demand_Moldel2) o;
            if (data.getState().equals("ok")){
                progress.setVisibility(View.GONE);
                ass_name.setText(data.getData().getItem().getName());
                total_count.setText(data.getData().getItem().getTotalAmout()+"");
                asset_count.setText(data.getData().getItem().getAssetNum()+"");
                int tangible = data.getData().getItem().getTangible();
                if (tangible==0){
                    assert_type.setText("无形资产");
                }else if (tangible==1){
                    assert_type.setText("有形资产");
                }
            }else{
                progress.setVisibility(View.GONE);
                ToastUtil.showShort(this,data.getMessage());
            }
        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {
           progress.setVisibility(View.GONE);
    }
}
