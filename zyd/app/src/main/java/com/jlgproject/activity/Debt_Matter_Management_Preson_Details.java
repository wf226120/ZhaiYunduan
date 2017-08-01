package com.jlgproject.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.fragment.Asset_Information;
import com.jlgproject.fragment.Debts_Information;
import com.jlgproject.fragment.Demand_Information;
import com.jlgproject.fragment.Preson_Basic_Information;
import com.jlgproject.model.Debt_Preson_Manger;
import com.jlgproject.util.L;

/**
 * Created by sunbeibei on 2017/5/9.
 * 债事----个人----管理类
 */

public class Debt_Matter_Management_Preson_Details extends BaseActivity {

    private FragmentTransaction fragmentTransaction;
    private Preson_Basic_Information preson_basic_information;
    private RadioGroup group;
    private FragmentTransaction tran2;
    private Asset_Information asset_information;
    private Demand_Information demand_information;
    private TextView title_left;
    private Debts_Information debts_information;
    private TextView title;
    private ImageView iv_left;
    private LinearLayout ll_ivParent_title;
    private FragmentManager fragmentManager;
    public static Long id=0L;
    public static String name;
    public static String tyep=null;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_matter_management_preson_details;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Debt_Preson_Manger.DataBean.ItemsBean itemsBean = (Debt_Preson_Manger.DataBean.ItemsBean) getIntent().getExtras().getSerializable("grInfo");
        if(itemsBean!=null){
            id = itemsBean.getId();
            name = itemsBean.getName();
            tyep = itemsBean.getType();
        }
        L.e("---个人----详情管理类---"+id+"-----"+tyep);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        preson_basic_information = new Preson_Basic_Information();
        fragmentTransaction.add(R.id.frame, preson_basic_information);
        fragmentTransaction.commit();
    }


    private void hideAdd() {
        title_left = (TextView) findViewById(R.id.tv_title_right);
        title_left.setVisibility(View.INVISIBLE);

    }

    private void onClickAdd() {
        title_left = (TextView) findViewById(R.id.tv_title_right);
        title_left.setText("新增");
        title_left.setVisibility(View.VISIBLE);

    }


    @Override
    public void initViews() {
        super.initViews();
        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);
        ll_ivParent_title= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        ll_ivParent_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText(name);
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tran2 = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.radio1://基本信息
                        hideAdd();
                        if (preson_basic_information == null) {
                            preson_basic_information = new Preson_Basic_Information();
                            tran2.add(R.id.frame, preson_basic_information);
                        } else {
                            tran2.show(preson_basic_information);
                        }
                        if (asset_information != null) {
                            tran2.hide(asset_information);
                        }
                        if (demand_information != null) {
                            tran2.hide(demand_information);
                        }
                        if (debts_information != null) {
                            tran2.hide(debts_information);
                        }
                        break;
                    case R.id.radio2://资产信息
                        onClickAdd();
                        title_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//新增资产
                                startActivity(new Intent(Debt_Matter_Management_Preson_Details.this, New_Assets.class).putExtra("titlename", name).putExtra("id", id));
                            }
                        });
                        if (asset_information == null) {
                            asset_information = new Asset_Information();
                            tran2.add(R.id.frame, asset_information);
                        } else {
                            tran2.show(asset_information);
                        }
                        if (preson_basic_information != null) {
                            tran2.hide(preson_basic_information);
                        }
                        if (demand_information != null) {
                            tran2.hide(demand_information);
                        }
                        if (debts_information != null) {
                            tran2.hide(debts_information);
                        }
                        break;
                    case R.id.radio3:
                        onClickAdd();
                        title_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Debt_Matter_Management_Preson_Details.this, New_Demand.class).putExtra("titlename", name).putExtra("id", id));
                            }
                        });
                        if (demand_information == null) {
                            demand_information = new Demand_Information();
                            tran2.add(R.id.frame, demand_information);
                        } else {
                            tran2.show(demand_information);
                        }
                        if (preson_basic_information != null) {
                            tran2.hide(preson_basic_information);
                        }
                        if (asset_information != null) {
                            tran2.hide(asset_information);
                        }
                        if (debts_information != null) {
                            tran2.hide(debts_information);
                        }
                        break;
                    case R.id.radio4:
                        onClickAdd();
                        title_left.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Debt_Matter_Management_Preson_Details.this, New_Debt_Matter.class).putExtra("titlename", name));
                            }
                        });
                        if (debts_information == null) {
                            debts_information = new Debts_Information();
                            tran2.add(R.id.frame, debts_information);
                        } else {
                            tran2.show(debts_information);
                        }
                        if (preson_basic_information != null) {
                            tran2.hide(preson_basic_information);
                        }
                        if (asset_information != null) {
                            tran2.hide(asset_information);
                        }
                        if (demand_information != null) {
                            tran2.hide(demand_information);
                        }
                        break;

                }
                tran2.commit();
            }
        });
    }
}
