package com.jlgproject.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.CzResponse;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * 充值 and 提现
 */
public class RechargeAndWithdraw extends BaseActivity implements View.OnClickListener,HttpMessageInfo.IMessage{
    //动态设置标题
    private TextView mTv_Title_Recharge;
    private ImageView mIv_Title_left_Recharge;

    private int indexPage;
    private LinearLayout mLl_recharge_item_parent;//银行卡item父布局
    private Button mBt_recharge_cz;
    private RelativeLayout mRl_recharge_cz;//充值
    private LinearLayout mLl_recharge_tx;//提现
    private View mV_line_tx;//提现上线
    private View mV_line; //提现下方线
    private TextView mTv_recharge_zde;//提示金额
    private EditText et_recharge_je;//充值输入框



    @Override
    public int loadWindowLayout() {
        return R.layout.activity_recharge_and_withdraw;
    }

    @Override
    public void initViews() {

        //动态设置标题
        mTv_Title_Recharge = (TextView) findViewById(R.id.tv_title_name);
        mIv_Title_left_Recharge = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_Recharge.setVisibility(View.VISIBLE);
        mIv_Title_left_Recharge.setOnClickListener(this);
        mLl_recharge_item_parent= (LinearLayout) findViewById(R.id.ll_recharge_item_parent);
        mLl_recharge_item_parent.setOnClickListener(this);
        mBt_recharge_cz= (Button) findViewById(R.id.bt_recharge_cz);
        mBt_recharge_cz.setOnClickListener(this);

        mRl_recharge_cz= (RelativeLayout) findViewById(R.id.rl_recharge_cz);//充值布局
        mLl_recharge_tx= (LinearLayout) findViewById(R.id.ll_recharge_tx);//提现布局
        mV_line_tx=findViewById(R.id.v_line_tx);//上方线
        mV_line=findViewById(R.id.v_line);//下方线
        mTv_recharge_zde= (TextView) findViewById(R.id.tv_recharge_zde);//充值提现金额

        mRl_recharge_cz.setVisibility(View.GONE);
        mLl_recharge_tx.setVisibility(View.GONE);
        mV_line_tx.setVisibility(View.GONE);
        mV_line.setVisibility(View.GONE);
        mTv_recharge_zde.setVisibility(View.GONE);
        indexPage=getIntent().getIntExtra("value",0);

        et_recharge_je= (EditText) findViewById(R.id.et_recharge_je);

        if(indexPage==1){//充值
            mTv_Title_Recharge.setText(getResources().getText(R.string.recharge));
            mBt_recharge_cz.setText("充值");
            mRl_recharge_cz.setVisibility(View.VISIBLE);
            mTv_recharge_zde.setVisibility(View.VISIBLE);
        }else if(indexPage==2){//提现
            mTv_Title_Recharge.setText(getResources().getText(R.string.tixain));
            mBt_recharge_cz.setText("提现");
            mLl_recharge_tx.setVisibility(View.VISIBLE);
            mV_line_tx.setVisibility(View.VISIBLE);
            mV_line.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void onClick(View v) {
        if(v==mLl_recharge_item_parent){//点击选择银行卡

            if(indexPage==1){//充值页
                ActivityCollector.startA(RechargeAndWithdraw.this,RechargeDialogActivity.class,"dialogValue",1);
            }else if(indexPage==2){//提现页
                ActivityCollector.startA(RechargeAndWithdraw.this,RechargeDialogActivity.class,"dialogValue",2);
            }

        }else if(v==mBt_recharge_cz){//提交 and 充值

            if(indexPage==1){//充值
                String cz=et_recharge_je.getText().toString().trim();
                if(!TextUtils.isEmpty(cz)){
                    setCz(cz);
                }else{
                    ToastUtil.showShort(this,"请输入充值额度");
                }

            }else if(indexPage==2){//提现

            }

        }else if(v==mIv_Title_left_Recharge){//返回
            finish();
        }
    }

    public void setCz(String cz) {
        Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
        HttpMessageInfo<CzResponse> info=new HttpMessageInfo<CzResponse>(BaseUrl.MY_CZ,new CzResponse());
        info.setiMessage(this);
        RequestBody requestBody = new FormBody.Builder().add("amount",cz).build();
        info.setFormBody(requestBody);
        if (login != null) {
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", login.getData().getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, headers, 1);
        }else {
            ToastUtil.showShort(this,"请先登录");
            ActivityCollector.pleaseLogin(this,2);
        }
    }

    @Override
    public void getServiceData(Object o) {

        if(o instanceof CzResponse){
            CzResponse cz= (CzResponse) o;
            if(cz.getState().equals("ok")){
                ToastUtil.showShort(this,cz.getMessage());
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }
}
