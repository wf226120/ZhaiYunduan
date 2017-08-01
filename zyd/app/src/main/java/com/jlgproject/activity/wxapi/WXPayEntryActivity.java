package com.jlgproject.activity.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.jlgproject.App;
import com.jlgproject.activity.PaySuccess;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, ConstUtils.WEIXIN_ADDID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
//                L.e("--------微信支付成功------回调----");
                ActivityCollector.startA(this, PaySuccess.class);
                Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_LONG);
            } else {
                Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_LONG);
            }
            finish();
        }
//        L.e("---***-----微信支付--错误----------***" + resp.errCode);
    }
}
