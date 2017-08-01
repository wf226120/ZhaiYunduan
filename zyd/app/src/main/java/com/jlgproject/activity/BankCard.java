package com.jlgproject.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.adapter.BankCardList_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.view.BankCardListView;

/**
 * 银行卡
 */
public class BankCard extends BaseActivity implements View.OnClickListener{

    //动态设置标题
    private TextView mTv_Title_bank;
    private ImageView mIv_Title_left_bank;
    private LinearLayout mLl_ivParent_title_bank;
    private LinearLayout mLl_add;//添加银行卡
    private BankCardListView mLv_card_yhk;//银行卡列表



    @Override
    public int loadWindowLayout() {
        return R.layout.activity_bank_card;
    }


    @Override
    public void initViews() {
        //动态设置标题
        mTv_Title_bank = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_bank.setText(getResources().getText(R.string.bank_card));
        mIv_Title_left_bank = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_bank.setVisibility(View.VISIBLE);
        mLl_ivParent_title_bank= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title_bank.setOnClickListener(this);

        mLl_add= (LinearLayout) findViewById(R.id.ll_add);
        mLl_add.setOnClickListener(this);
        mLv_card_yhk= (BankCardListView) findViewById(R.id.lv_card_yhk);
        mLv_card_yhk.setAdapter(new BankCardList_Adapter(this));
    }

    @Override
    public void onClick(View v) {
        if(v==mLl_ivParent_title_bank){
            finish();
        }else if(v==mLl_add){
            ActivityCollector.startA(BankCard.this,BankCard_Message.class);
        }
    }
}
