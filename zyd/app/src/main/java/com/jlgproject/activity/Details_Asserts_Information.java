package com.jlgproject.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.adapter.Asset_Pic_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Demand_Infromation_Detail;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.view.CustomGridView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/8.
 * 点击进入需求信息详情页面
 */

public class Details_Asserts_Information extends BaseActivity implements HttpMessageInfo.IMessage {

    private ImageView title_left;
    private Long id;
    private TextView title;
    private Login_zud zud;
    private AddHeaders headers;
    private GetParmars pamars;
    private TextView asset_name;
    private TextView asset_type;
    private TextView total_vlue;
    private TextView asset_number;
    private TextView lt_volue;
    private TextView asset_pz;
    private TextView asset_detail;
    private TextView dy_dsljz;
    private TextView susong;
    private TextView liut;
    private TextView bloge_to;
    private TextView adress;
    private TextView phone_number;
    private TextView xzlt;
    private TextView zydx;
    private TextView xzltjz;
    private TextView xzltfzl;
    private String name1;
    private int indext;
    private int assid;
    private List<String> picList;
    private CustomGridView gridView;


    @Override
    public int loadWindowLayout() {
        return R.layout.details_demand_informaton;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        name1 = intent.getStringExtra("name");
        indext = intent.getIntExtra("indext", 9);


        requestDemandDetail();
    }

    private void requestDemandDetail() {
        zud = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
        pamars = new GetParmars();
        pamars.add("id", id + "");
        pamars.add("flag", "0");
        headers = new AddHeaders();
        headers.add("Authorization", zud.getData().getToken());
        HttpMessageInfo<Demand_Infromation_Detail> info = new HttpMessageInfo<>(BaseUrl.ASSERT_INFORMATION_DETAIL, new Demand_Infromation_Detail());
        info.setiMessage(this);
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, pamars, headers, 1);

    }

    @Override
    public void initViews() {
        super.initViews();
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText(name1);
        title_left.setImageResource(R.drawable.back3);
        title_left.setVisibility(View.VISIBLE);
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //资产名称
        asset_name = (TextView) findViewById(R.id.get_code);
        //资产性质分类
        asset_type = (TextView) findViewById(R.id.get_legal_person_name);
        //总价值
        total_vlue = (TextView) findViewById(R.id.get_legal_person_icard);
        //资产数量
        asset_number = (TextView) findViewById(R.id.get_area);
        //可流通资产价值
        lt_volue = (TextView) findViewById(R.id.get_phone_number);
        //资产凭证
        asset_pz = (TextView) findViewById(R.id.get_registered_capital);
        //资产详情
        asset_detail = (TextView) findViewById(R.id.get_adress);
        //抵押等数量和价值
        dy_dsljz = (TextView) findViewById(R.id.get_industry);
        //诉讼情况
        susong = (TextView) findViewById(R.id.get_email);
        //流通资产
        liut = (TextView) findViewById(R.id.get_qq);
        //归属人
        bloge_to = (TextView) findViewById(R.id.get_wetchat);
        //所在位置
        adress = (TextView) findViewById(R.id.adress);
        //联系电话
        phone_number = (TextView) findViewById(R.id.phone_nuber);
        //限制流通原因
        xzlt = (TextView) findViewById(R.id.restricted_circulation_cause);
        //质押对象
        zydx = (TextView) findViewById(R.id.object_pledge);
        //限制流通价值
        xzltjz = (TextView) findViewById(R.id.restricted_circulation_value);
        //限制流通负债量
        xzltfzl = (TextView) findViewById(R.id.limit_current_liabilities);
        gridView = (CustomGridView) findViewById(R.id.grid);


    }

    @Override
    public void getServiceData(Object o) {
        Demand_Infromation_Detail data = (Demand_Infromation_Detail) o;
        String name = data.getData().getName();
        asset_name.setText(name);
        int tangible = data.getData().getTangible();
        if (tangible == 1) {
            asset_type.setText("有形资产");
        } else {
            asset_type.setText("无形资产");
        }
        int totalAmout = data.getData().getTotalAmout();
        total_vlue.setText(totalAmout + "");
        int assetNum = data.getData().getAssetNum();
        asset_number.setText(assetNum + "");
        Long tradeableAssets = data.getData().getTradeableAssets();
        lt_volue.setText(tradeableAssets + "");
        String assetCredential = data.getData().getAssetCredential();
        asset_pz.setText(assetCredential);
        String assetDetails = data.getData().getAssetDetails();
        asset_detail.setText(assetDetails);
        String mortgage = (String) data.getData().getMortgage();
        dy_dsljz.setText(mortgage);
        int isLawsuit = data.getData().getIsLawsuit();
        if (isLawsuit == 1) {
            susong.setText("已诉讼");
        } else {
            susong.setText("未诉讼");
        }
        String currentAsset = (String) data.getData().getCurrentAsset();
        liut.setText(currentAsset);
        String belongTo = (String) data.getData().getBelongTo();
        bloge_to.setText(belongTo);
        String location = (String) data.getData().getLocation();
        adress.setText(location);
        String phoneNumber = (String) data.getData().getPhoneNumber();
        phone_number.setText(phoneNumber);
        String restrictReasion = (String) data.getData().getRestrictReasion();
        xzlt.setText(restrictReasion);
        String mortgageTarget = (String) data.getData().getMortgageTarget();
        zydx.setText(mortgageTarget);
        String restrictWorth = (String) data.getData().getRestrictWorth();
        xzltjz.setText(restrictWorth);
        String restrictNum = (String) data.getData().getRestrictNum();
        xzltfzl.setText(restrictNum);
        picList = data.getData().getPicList();
        if (indext == 1) {
            gridView.setAdapter(new Asset_Pic_Adapter(this, picList));
        }


    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }
}
