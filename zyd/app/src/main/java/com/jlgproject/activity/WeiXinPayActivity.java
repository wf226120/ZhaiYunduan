package com.jlgproject.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jlgproject.R;
import com.jlgproject.model.weixin.WeiXinBean;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeiXinPayActivity extends AppCompatActivity {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin_pay);

        api = WXAPIFactory.createWXAPI(WeiXinPayActivity.this, null);
        // 将该app注册到微信
        api.registerApp(ConstUtils.WEIXIN_ADDID);

        WeiXinBean.DataBean.CallWxBean callWxBean= (WeiXinBean.DataBean.CallWxBean) getIntent().getSerializableExtra("weixin");
        PayReq payReq = new PayReq();
        payReq.appId = callWxBean.getAppid();
        payReq.partnerId = callWxBean.getPartnerid();
        payReq.prepayId = callWxBean.getPrepayid();
        payReq.packageValue = callWxBean.getPackageX();
        payReq.nonceStr = callWxBean.getNoncestr();
        payReq.timeStamp = callWxBean.getTimestamp();
        payReq.sign = callWxBean.getSign();
        api.sendReq(payReq);
        L.e("----api.sendReq(payReq)------" + api.sendReq(payReq));
    }
}
