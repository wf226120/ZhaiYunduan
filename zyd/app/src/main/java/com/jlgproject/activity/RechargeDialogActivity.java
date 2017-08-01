package com.jlgproject.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.adapter.Recharge_DialogAdapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ScreenUtil;

public class RechargeDialogActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mQuxiao;//取消
    private TextView mTv_dialog_leixin;//弹窗title
    private TextView mTv_dialog_ka_title;//卡的titleName
    private int indexDialog;
    private LinearLayout mLl_dialog_yuezhifu;//余额支付item
    private LinearLayout mLl_recharge_tjyhkzf;//添加银行卡
    private ListView mLv_dialog_yhkxx;
    private LinearLayout mLl_recharge_ka;//银行卡
    private ListView mLv_recharge_ka;//银行卡列表
    private boolean idSelect = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除Activity的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_recharge_dialog;
    }

    @Override
    public void initViews() {
        dialogActivityInit();
        mQuxiao = (ImageView) findViewById(R.id.iv_dialog_close);
        mQuxiao.setOnClickListener(this);
        mTv_dialog_leixin = (TextView) findViewById(R.id.tv_dialog_leixin);
        mTv_dialog_leixin.setOnClickListener(this);
        mTv_dialog_ka_title = (TextView) findViewById(R.id.tv_dialog_ka_title);
        mLl_dialog_yuezhifu = (LinearLayout) findViewById(R.id.ll_dialog_yuezhifu);
        mLl_dialog_yuezhifu.setVisibility(View.GONE);
        mLl_recharge_tjyhkzf = (LinearLayout) findViewById(R.id.ll_recharge_tjyhkzf);
        mLl_recharge_tjyhkzf.setOnClickListener(this);

        mLv_recharge_ka = (ListView) findViewById(R.id.lv_recharge_ka);
        mLv_recharge_ka.setAdapter(new Recharge_DialogAdapter(this));


        indexDialog = getIntent().getIntExtra("dialogValue", 0);
        if (indexDialog == 1) {//充值Dialog
            mTv_dialog_leixin.setText("选择付款方式");
            mTv_dialog_ka_title.setText("添加银行卡支付");
            mLl_dialog_yuezhifu.setVisibility(View.VISIBLE);
        } else if (indexDialog == 2) {//提现Dialog
            mTv_dialog_leixin.setText("选择银行卡");
            mTv_dialog_ka_title.setText("添加银行卡");
        }
    }

    /**
     * ActivityDialog 初始化配置
     */
    public void dialogActivityInit() {

        WindowManager windowManager = getWindowManager();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = getWindow().getAttributes();
        // 宽度设置为屏幕的1
        p.width = (int) (ScreenUtil.getScreenWidth(RechargeDialogActivity.this));
        // 设置透明度,0.0为完全透明，1.0为完全不透明
        p.alpha = 0.95f;
        // 设置布局参数
        getWindow().setAttributes(p);
        // 设置点击弹框外部不可消失
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v) {
        if (v == mQuxiao) {//取消
            finish();
        } else if (v == mLl_recharge_tjyhkzf) {//添加银行卡
            ActivityCollector.startA(RechargeDialogActivity.this, BankCard.class);
        }
    }


}
