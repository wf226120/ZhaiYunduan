package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;

import okhttp3.Call;

/**
 * 我的钱包
 */
public class MyWallet extends BaseActivity implements View.OnClickListener,HttpMessageInfo.IMessage{

    private TextView mTv_Title_Wallet,mTv_Title_Wallet_right;
    private ImageView mIv_Title_left_Wallet;
    private LinearLayout mLl_ivParent_title_Wallet;
    private LinearLayout mLl_wallet_cz,mLl_wallet_tx,mLl_wallet_yhk,mLl_wallet_yj,mLl_wallet_jf
            ,mLl_wallet_yhq,mLl_wallet_xnhb;
    private TextView mTv_wallet_yue;
    @Override
    public int loadWindowLayout() {
        return R.layout.activity_my_wallet;
    }

    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_Wallet = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_Wallet.setText(getResources().getText(R.string.wallet));
        mIv_Title_left_Wallet = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_Wallet.setVisibility(View.VISIBLE);
        mLl_ivParent_title_Wallet= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_Wallet.setOnClickListener(this);
        mTv_Title_Wallet_right= (TextView) findViewById(R.id.tv_title_right);
        mTv_Title_Wallet_right.setText(getResources().getText(R.string.mingxi));
        mTv_Title_Wallet_right.setTextColor(getResources().getColor(R.color.bai));
        mTv_Title_Wallet_right.setOnClickListener(this);
        mTv_wallet_yue= (TextView) findViewById(R.id.tv_wallet_yue);//余额
        mLl_wallet_cz= (LinearLayout) findViewById(R.id.ll_wallet_cz);//充值
        mLl_wallet_tx= (LinearLayout) findViewById(R.id.ll_wallet_tx);//提现
        mLl_wallet_yhk= (LinearLayout) findViewById(R.id.ll_wallet_yhk);//银行卡
        mLl_wallet_yj= (LinearLayout) findViewById(R.id.ll_wallet_yj);//佣金
        mLl_wallet_jf= (LinearLayout) findViewById(R.id.ll_wallet_jf);//积分
        mLl_wallet_yhq= (LinearLayout) findViewById(R.id.ll_wallet_yhq);//优惠券
        mLl_wallet_xnhb= (LinearLayout) findViewById(R.id.ll_wallet_xnhb);//虚拟货币
        mLl_wallet_cz.setOnClickListener(this);
        mLl_wallet_tx.setOnClickListener(this);
        mLl_wallet_tx.setOnClickListener(this);
        mLl_wallet_yhk.setOnClickListener(this);
        mLl_wallet_yj.setOnClickListener(this);
        mLl_wallet_jf.setOnClickListener(this);
        mLl_wallet_yhq.setOnClickListener(this);
        mLl_wallet_xnhb.setOnClickListener(this);
//        getBalance();
    }

    @Override
    public void onClick(View v) {

        if(v==mLl_ivParent_title_Wallet){//返回
            finish();
        }else if(v==mTv_Title_Wallet_right){//明细

        }else if(v==mLl_wallet_cz){//充值
            ActivityCollector.startA(MyWallet.this,RechargeAndWithdraw.class,"value",1);
        } else if(v==mLl_wallet_tx){//提现
            ActivityCollector.startA(MyWallet.this,RechargeAndWithdraw.class,"value",2);
        }else if(v==mLl_wallet_yhk){//银行卡
            ActivityCollector.startA(MyWallet.this,BankCard.class);
        }else if(v== mLl_wallet_yj){//佣金
            ActivityCollector.startA(this,MyCommission.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void getBalance() {
        L.e("----钱包-----");
        Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
        if(login!=null){
            HttpMessageInfo<String> info=new HttpMessageInfo<>(BaseUrl.MY_QIANBAO,new String());
            info.setiMessage(this);
            AddHeaders header=new AddHeaders();
            header.add("Authorization",login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST,info,null,header,2);
        }else{
            ToastUtil.showShort(this,"请先登录");
            ActivityCollector.pleaseLogin(this,3);
        }
    }

    @Override
    public void getServiceData(Object o) {

    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }
}
