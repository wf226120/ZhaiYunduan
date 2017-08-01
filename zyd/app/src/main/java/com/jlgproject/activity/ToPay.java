package com.jlgproject.activity;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.model.weixin.WeiXinBean;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.UserInfoState;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.OrderInfo;
import com.jlgproject.model.alipay.PayResult;
import com.jlgproject.model.eventbusMode.AddDebtBean;
import com.jlgproject.model.eventbusMode.DebtBeiAn;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 支付备案费
 */
public class ToPay extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, Callback, HttpMessageInfo.IMessage {

    //设置title
    private TextView mTv_Title_tb;

    private ImageView mIv_Title_left_tb, mTv_title_right;
    private LinearLayout mLl_ivParent_title_tb;

    private RadioGroup mRg_parent;
    private Button mBt_pay;
    private int PAY_WAY = 0;//支付方式

    private Handler mHandler = null;
    private String money;
    private TextView payfei;
    private String orderId = "";
    private static final int SDK_PAY_FLAG = 1;
    private int state;
    private IWXAPI api;



    @Override
    public int loadWindowLayout() {
        return R.layout.activity_to_pay;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    public void customDialog() {
        if (state == 0) {
            DialogUtils.showConfirmDialog(this, "支付" + money + "元解债费可上传信息到解债中心，解债专家为您定制解债方案!", "确认", true, null);
        } else if (state == 1) {
            DialogUtils.showConfirmDialog(this, "支付" + money + "元成为云债行行长，享受行长福利待遇!", "确认", true, null);
        }
    }

    @Override
    public void initViews() {
        EventBus.getDefault().register(this);
        customDialog();
        mHandler = new Handler(this);
        mTv_Title_tb = (TextView) findViewById(R.id.tv_title_name);

        payfei = (TextView) findViewById(R.id.payfei);
        payfei.setText("共计" + money + "元");
        if (state == 1) {//开行
            mTv_Title_tb.setText("支付开行费");
        } else if (state == 0) { //备案
            mTv_Title_tb.setText("支付备案费");
        }
        mIv_Title_left_tb = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_tb.setVisibility(View.VISIBLE);
        mLl_ivParent_title_tb = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_tb.setOnClickListener(this);
        mTv_title_right = (ImageView) findViewById(R.id.iv_title_right);
        mTv_title_right.setVisibility(View.VISIBLE);
        mTv_title_right.setImageResource(R.mipmap.return_home);

        mTv_title_right.setOnClickListener(this);
        mRg_parent = (RadioGroup) findViewById(R.id.rg_parent);
        mRg_parent.setOnCheckedChangeListener(this);
        mBt_pay = (Button) findViewById(R.id.bt_pay);
        mBt_pay.setOnClickListener(this);


        api = WXAPIFactory.createWXAPI(this, null);
        // 将该app注册到微信
        api.registerApp(ConstUtils.WEIXIN_ADDID);

    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getOrderId(AddDebtBean oId) {
        orderId = oId.getOrderId();
        money = oId.getQianshu();
        state = oId.getState();//state=1开行0备案
        L.e("----orderId----" + orderId + "----money-----" + money + "--state---" + state);
    }


    @Override
    public void onClick(View v) {
        if (v == mLl_ivParent_title_tb) {//点击返回按钮
            finish();
        } else if (v == mTv_title_right) {//返回首页
            ActivityCollector.startA(ToPay.this, MainActivity.class, "currMenu", -1);
        } else if (v == mBt_pay) {//点击支付
            if (PAY_WAY == 1) {//支付宝支付
                initOrderInfo();
            } else if (PAY_WAY == 2) {//微信支付
                initWeiXinOrderInfo();
            }
        }
    }


    /**
     * 调起支付
     */
    public void initOrderInfo() {

        if (ShowDrawDialog(this)) {
            HttpMessageInfo<OrderInfo> info = new HttpMessageInfo<>(BaseUrl.GOPAY, new OrderInfo());
            info.setiMessage(this);
            if (orderId != null) {
                L.e("-------獲取 odrea--------" + orderId);
                if (state == 0) {//债事备案
                    L.e("-----债事备案--------");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("orderId", orderId)
                            .add("type", "1")
                            .build();
                    info.setFormBody(requestBody);
                } else if (state == 1) {//开通债行
                    L.e("-----开通债行--------");
                    RequestBody requestBody = new FormBody.Builder()
                            .add("orderId", orderId)
                            .add("type", "2")
                            .build();
                    info.setFormBody(requestBody);
                }
                AddHeaders headers = new AddHeaders();
                headers.add("Accept", "application/json");
                headers.add("Content-Types", "application/json");
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
            } else {
                L.e("----- odrea-Id----空---");
            }
        }
    }

    /**
     * 调起微信支付
     */
    public void initWeiXinOrderInfo() {


        if (ShowDrawDialog(this)) {
            HttpMessageInfo<WeiXinBean> info = new HttpMessageInfo<>(BaseUrl.WX_GOPAY, new WeiXinBean());
            info.setiMessage(this);
            if (state == 0) {//债事备案
                RequestBody requestBody = new FormBody.Builder()
                        .add("prepayid", orderId)
                        .add("type", "1")
                        .build();
                info.setFormBody(requestBody);
            } else if (state == 1) {//开通债行
                RequestBody requestBody = new FormBody.Builder()
                        .add("prepayid", orderId)
                        .add("type", "2")
                        .build();
                info.setFormBody(requestBody);
            }
            AddHeaders headers = new AddHeaders();
            headers.add("Accept", "application/json");
            headers.add("Content-Types", "application/json");
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
        }
    }


    /**
     * //支付宝支付
     */
    private void alipayPay(final String orderInfo) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                if (orderInfo != null) {
                    PayTask alipay = new PayTask(ToPay.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
                    Log.i("msp", result.toString());
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } else {
                    Log.e("---orderInfo为空---", "" + orderInfo);
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof OrderInfo) {
            OrderInfo info = (OrderInfo) o;
            if (info != null) {
                DismissDialog();
                if (info.getCode().equals("ok")) {
                    if (PAY_WAY == 1) {
                        alipayPay(info.getData());
                    }

                }
            }
        } else if (o instanceof WeiXinBean) {
            WeiXinBean weiXinBean= (WeiXinBean) o;
            if(weiXinBean!=null){
                DismissDialog();
                if(weiXinBean.getState().equals("ok")){
                    if(PAY_WAY==2){
                        WeiXinBean.DataBean.CallWxBean callWxBean= weiXinBean.getData().getCallWx();
                        weixPay(callWxBean);
                    }
                }else if(weiXinBean.getState().equals("error")){
                    ToastUtil.show(this,weiXinBean.getMessage(),Toast.LENGTH_SHORT);
                }
            }
        }
    }

    //微信支付
    private void weixPay(WeiXinBean.DataBean.CallWxBean callWxBean) {

//        Intent intent=new Intent(this,WeiXinPayActivity.class);
//        intent.putExtra("weixin",callWxBean);
//        startActivity(intent);


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


    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == mRg_parent) {
            if (checkedId == R.id.rb_zfb) {//支付宝支付
                PAY_WAY = 1;
            } else if (checkedId == R.id.rb_yl) {//微信支付
                PAY_WAY = 2;
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == SDK_PAY_FLAG) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();//返回6001 失败
            //判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                Toast.makeText(ToPay.this, payResult.getMemo(), Toast.LENGTH_SHORT).show();
                if (state == 0) {//债事备案
                    EventBus.getDefault().postSticky(new DebtBeiAn("1"));
                } else if (state == 1) {//开通债行
                    ToastUtil.showShort(ToPay.this, "开通债行成功");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ActivityCollector.startA(ToPay.this, PaySuccess.class);
                        finish();
                    }
                }, 500);
            } else if (TextUtils.equals(resultStatus, "6001")) {
                Toast.makeText(ToPay.this, payResult.getMemo(), Toast.LENGTH_SHORT).show();
            }
        }

        return false;
    }


}
