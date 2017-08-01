package com.jlgproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Asset;
import com.jlgproject.model.AssetRequest;
import com.jlgproject.model.Demand_Infromation_Detail;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.eventbusMode.AssetEvent;
import com.jlgproject.model.eventbusMode.IntentDebtPresonBean;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/8.
 * 资产信息新增
 */

public class New_Assets extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, HttpMessageInfo.IMessage {

    private String titlename;
    private ImageView iv_left;
    private TextView titie;
    private Button btn_next;
    private EditText asst_name;
    private RadioGroup radioGroup;
    private EditText total_volu;
    private EditText asset_count;
    private EditText klt;
    private EditText zcpz;
    private EditText asset_information;
    private EditText dy;
    private RadioGroup idswout;
    private EditText ltzc;
    private EditText blongto;
    private EditText adress;
    private EditText et_phone;
    private EditText limit;
    private EditText object;
    private EditText xzltjz;
    private EditText et_debt_num;
    private String v_name;
    private String v_volue;
    private String v_count;
    private String v_klt;
    private String v_zcpz;
    private String v_information;
    private String v_dy;
    private String v_ltzc;
    private String v_blong;
    private String v_adress;
    private String v_phone;
    private String v_limit;
    private String v_object;
    private String v_xzltjz;
    private String v_debt_num;
    private int a;
    private int b;
    private Long id;
    private Long assid;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private Demand_Infromation_Detail asset;
    private int backPager;

    @Override
    public int loadWindowLayout() {
        return R.layout.new_assets;
    }


    @Override
    public void initViews() {
        super.initViews();
        EventBus.getDefault().register(this);
        //资产名称
        titlename = getIntent().getStringExtra("titlename");
        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        titie = (TextView) findViewById(R.id.tv_title_name);
        titie.setText(titlename);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        //资产名称
        asst_name = (EditText) findViewById(R.id.et_organization_code);
        //资产性质分类
        radioGroup = (RadioGroup) findViewById(R.id.rgroup);
        radioGroup.setOnCheckedChangeListener(this);
        //总价值
        total_volu = (EditText) findViewById(R.id.et_enterpeise_name);
        //资产数量
        asset_count = (EditText) findViewById(R.id.et_legal_person_name);
        //可流通资产价值
        klt = (EditText) findViewById(R.id.et_legal_person_icard);
        //资产凭证
        zcpz = (EditText) findViewById(R.id.et_provinces);
        //资产详情
        asset_information = (EditText) findViewById(R.id.et_industry);
        //抵押等数量和价值
        dy = (EditText) findViewById(R.id.et_contact_phone_number);
        //流通资产
        ltzc = (EditText) findViewById(R.id.et_email);
        //归属人
        blongto = (EditText) findViewById(R.id.et_qq);
        //所在位置
        adress = (EditText) findViewById(R.id.et_wetchcat);
        //联系电话
        et_phone = (EditText) findViewById(R.id.et_phone);
        //限制 流通原因
        limit = (EditText) findViewById(R.id.et_limit);
        //质押对象
        object = (EditText) findViewById(R.id.et_object);
        //限制流通价值
        xzltjz = (EditText) findViewById(R.id.et_vlaue);
        //限制流通负债量
        et_debt_num = (EditText) findViewById(R.id.et_debt_num);

        //诉讼情况
        idswout = (RadioGroup) findViewById(R.id.is_swout);
        idswout.setOnCheckedChangeListener(this);
        radioButton1 = (RadioButton) findViewById(R.id.radio1);
        radioButton2 = (RadioButton) findViewById(R.id.radio2);
        radioButton3 = (RadioButton) findViewById(R.id.radio3);
        radioButton4 = (RadioButton) findViewById(R.id.radio4);

        //债事人Id ---新增
        id = getIntent().getLongExtra("id", 0);
        //编辑时的 id
        assid = getIntent().getLongExtra(ConstUtils.ASSET_ID, 9);
        //获取该资产id 对应的资产信息
        if (assid != 9) {//
            L.e("---------编辑时--资产-Id-------------" + assid);
            getAssetInfo(assid);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == radioGroup) {
            if (checkedId == R.id.radio1) {//有形资产
                a = 1;
            } else if (checkedId == R.id.radio2) {//无形资产
                a = 0;
            }
        } else if (group == idswout) {//诉讼情况
            if (checkedId == R.id.radio3) {//未诉讼
                b = 0;
            } else if (checkedId == R.id.radio4) {//已诉讼
                b = 1;
            }
        }

    }

    private void next() {
        if (TextUtils.isEmpty(asst_name.getText().toString().trim())) {
            show("请输入资产名称");
            return;
        }
        if (TextUtils.isEmpty(total_volu.getText().toString().trim())) {
            show("请输入总价值");
            return;
        }
        String trim = total_volu.getText().toString().trim();

        if (Long.parseLong(trim) == 0) {
            show("总价值不能为0");
            return;
        }
        if (TextUtils.isEmpty(asset_count.getText().toString().trim())) {
            show("请输入资产数量");
            return;
        }
        if (Long.parseLong(asset_count.getText().toString().trim()) == 0) {
            show("资产数量不能为0");
            return;
        }
        if (TextUtils.isEmpty(klt.getText().toString().trim())) {
            show("请输入可流通资产价值");
            return;
        }
        if (Long.parseLong(klt.getText().toString().trim()) == 0) {
            show("流通资产价值不能为0");
            return;
        }

        if (TextUtils.isEmpty(zcpz.getText().toString().trim())) {
            show("请输入资产凭证");
            return;
        }

        if (TextUtils.isEmpty(asset_information.getText().toString().trim())) {
            show("请输入资产详情");
            return;
        }
        if (TextUtils.isEmpty(dy.getText().toString().trim())) {
            show("请输入抵押等数量和价值");
            return;
        }
        if (Long.parseLong(dy.getText().toString().trim()) == 0) {
            show("抵押等数量和价值不能为0");
            return;
        }
        if (TextUtils.isEmpty(ltzc.getText().toString().trim())) {
            show("请输入流通资产");
            return;
        }
        if (Long.parseLong(ltzc.getText().toString().trim()) == 0) {
            show("流通资产不能为0");
            return;
        }
        if (TextUtils.isEmpty(blongto.getText().toString().trim())) {
            show("请输入归属人");
            return;
        }
        if (TextUtils.isEmpty(adress.getText().toString().trim())) {
            show("请输入所在位置");
            return;
        }
        if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            show("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(limit.getText().toString().trim())) {
            show("请输入限制流通原因");
            return;
        }

        if (TextUtils.isEmpty(object.getText().toString().trim())) {
            show("请输入质押对象");
            return;
        }

        if (TextUtils.isEmpty(xzltjz.getText().toString().trim())) {
            show("请输入限制流通价值");
            return;
        }
        if (Long.parseLong(xzltjz.getText().toString().trim()) == 0) {
            show("限制流通价值不能为0");
            return;
        }
        if (TextUtils.isEmpty(et_debt_num.getText().toString().trim())) {
            show("请输入限制流通负债量");
            return;
        }
        if (Long.parseLong(et_debt_num.getText().toString().trim()) == 0) {
            show("限制流通负债量不能为0");
            return;
        }

        v_name = asst_name.getText().toString().trim();
        v_volue = total_volu.getText().toString().trim();
        v_count = asset_count.getText().toString().trim();
        v_klt = klt.getText().toString().trim();
        v_zcpz = zcpz.getText().toString().trim();
        v_information = asset_information.getText().toString().trim();
        v_dy = dy.getText().toString().trim();
        v_ltzc = ltzc.getText().toString().trim();
        v_blong = blongto.getText().toString().trim();
        v_adress = adress.getText().toString().trim();
        v_phone = et_phone.getText().toString().trim();
        v_limit = limit.getText().toString().trim();
        v_object = object.getText().toString().trim();
        v_xzltjz = xzltjz.getText().toString().trim();
        v_debt_num = et_debt_num.getText().toString().trim();


        if (assid != 9) {//点击下一步是在编辑
            Asset editAsset = new Asset();//编辑实体类
            editAsset.setId(assid);
            editAsset.setName(v_name);
            editAsset.setTangible(a);
            editAsset.setTotalAmout(Long.parseLong(v_volue));
            editAsset.setAssetNum(Long.parseLong(v_count));
            editAsset.setTradeableAssets(Long.parseLong(v_klt));
            editAsset.setAssetCredential(v_zcpz);
            editAsset.setAssetDetails(v_information);
            editAsset.setMortgage(v_dy);
            editAsset.setIsLawsuit(b);
            editAsset.setCurrentAsset(v_ltzc);
            editAsset.setBelongTo(v_blong);
            editAsset.setLocation(v_adress);
            editAsset.setPhoneNumber(v_phone);
            editAsset.setRestrictReasion(v_limit);
            editAsset.setMortgageTarget(v_object);
            editAsset.setRestrictWorth(v_xzltjz);
            editAsset.setRestrictNum(v_debt_num);
            if (asset.getData().getPicList().size() != 0) {
                editAsset.setPicUrl(asset.getData().getPicList());
            }
            startActivity(new Intent(New_Assets.this, UpdownPhotos.class).putExtra("addpic", 4));
            EventBus.getDefault().postSticky(editAsset);
        } else {//新增
            AssetRequest info = new AssetRequest();//新增实体类
            info.setDebtId(id);
            info.setName(v_name);
            info.setTangible(a);
            info.setTotalAmout(Long.parseLong(v_volue));
            info.setAssetNum(Long.parseLong(v_count));
            info.setTradeableAssets(Long.parseLong(v_klt));
            info.setAssetCredential(v_zcpz);
            info.setAssetDetails(v_information);
            info.setMortgage(v_dy);
            info.setIsLawsuit(b);
            info.setCurrentAsset(v_ltzc);
            info.setBelongTo(v_blong);
            info.setLocation(v_adress);
            info.setPhoneNumber(v_phone);
            info.setRestrictReasion(v_limit);
            info.setMortgageTarget(v_object);
            info.setRestrictWorth(v_xzltjz);
            info.setRestrictNum(v_debt_num);
            startActivity(new Intent(New_Assets.this, UpdownPhotos.class).putExtra("addpic", 5));
            EventBus.getDefault().postSticky(info);
        }
    }



    private void show(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.btn_next:
                next();

                break;
        }
    }


    //请求编辑资产信息
    public void getAssetInfo(Long id) {
        if (ShowDrawDialog(this)) {
            GetParmars pamars = new GetParmars();
            pamars.add("id", id + "");
            pamars.add("flag", "0");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Demand_Infromation_Detail> info = new HttpMessageInfo<>(BaseUrl.ASSERT_INFORMATION_DETAIL, new Demand_Infromation_Detail());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, pamars, headers, 1);
        }

    }


    @Override
    public void getServiceData(Object o) {

        if (o instanceof Demand_Infromation_Detail) {
            asset = (Demand_Infromation_Detail) o;
            if (asset != null) {
                DismissDialog();
                if (asset.getState().equals("ok")) {
                    Demand_Infromation_Detail.DataBean bean = asset.getData();
                    asst_name.setText(bean.getName().toString());
                    total_volu.setText(bean.getTotalAmout() + "");
                    asset_count.setText(bean.getAssetNum() + "");
                    klt.setText(bean.getTradeableAssets() + "");
                    zcpz.setText(bean.getAssetCredential().toString());
                    asset_information.setText(bean.getAssetDetails().toString());
                    dy.setText(bean.getMortgage().toString());
                    ltzc.setText(bean.getCurrentAsset().toString());
                    blongto.setText(bean.getBelongTo().toString());
                    adress.setText(bean.getLocation().toString());
                    et_phone.setText(bean.getPhoneNumber().toString());
                    limit.setText(bean.getRestrictReasion().toString());
                    object.setText(bean.getMortgageTarget().toString());
                    xzltjz.setText(bean.getRestrictWorth().toString());
                    et_debt_num.setText(bean.getRestrictNum().toString());
                    //资产性质分类
                    radioGroup = (RadioGroup) findViewById(R.id.rgroup);
                    radioGroup.setOnCheckedChangeListener(this);
                    int zc = bean.getTangible();
                    if (zc == 0) {
                        radioButton1.setChecked(true);
                    } else if (zc == 1) {
                        radioButton2.setChecked(true);
                    }
                    int ss = bean.getIsLawsuit();
                    if (ss == 0) {
                        radioButton3.setChecked(true);
                    } else if (ss == 1) {
                        radioButton4.setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void geteditAssets(AssetEvent assetEvent) {
        backPager = assetEvent.getI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(backPager==1){
            backPager=0;
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        backPager=0;
    }
}
