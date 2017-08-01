package com.jlgproject.activity;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.fragment.Basic_Information;
import com.jlgproject.fragment.Business_Information;
import com.jlgproject.fragment.Company_Asset_Information;
import com.jlgproject.fragment.Company_Debts_Information;
import com.jlgproject.fragment.Company_Demand_Information;
import com.jlgproject.fragment.Share_Information;
import com.jlgproject.model.Debt_Preson_Manger;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.L;

/**
 * Created by sunbeibei on 2017/5/5.
 * 债事 -----企业----管理类
 */

public class Debt_Matter_Management_Details extends BaseActivity implements RadioGroup.OnCheckedChangeListener {


    private RadioGroup group;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Basic_Information basic_information;
    private Share_Information share_information;
    private Business_Information business_information;
    private FragmentTransaction tran2;
    private TextView title_left;
    private TextView title;
    private ImageView title_lefte;
    private LinearLayout ll_ivParent_title_q;
    public static long id;
    public static String type;
    public static String name;
    private Company_Asset_Information company_asset_information;
    private Company_Demand_Information company_demand_information;
    private Company_Debts_Information company_debts_information;
    private String companyName;

    @Override
    public int loadWindowLayout() {
        return R.layout.debt_matter_management_details;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        Debt_Preson_Manger.DataBean.ItemsBean itemsBean = (Debt_Preson_Manger.DataBean.ItemsBean) getIntent().getExtras().getSerializable("qyInfo");
        id = itemsBean.getId();
        type = itemsBean.getType();
        name = itemsBean.getName();
        if(!TextUtils.isEmpty(itemsBean.getCompanyName())){// 2.0 新数据
            companyName=itemsBean.getCompanyName();
        }else {  //兼容1.0旧数据
            companyName=itemsBean.getName();
        }


    }


    @Override
    public void initViews() {
        super.initViews();
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText(companyName);//债事企业名称
        title_lefte = (ImageView) findViewById(R.id.iv_title_left);
        title_lefte.setVisibility(View.VISIBLE);
        title_lefte.setImageResource(R.drawable.back3);
        ll_ivParent_title_q= (LinearLayout) findViewById(R.id.ll_ivParent_title);
        ll_ivParent_title_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        basic_information = new Basic_Information();
        fragmentTransaction.add(R.id.frame, basic_information);
        fragmentTransaction.commit();
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(this);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        tran2 = supportFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.radio1:
                hideAdd();
                if (basic_information == null) {
                    basic_information = new Basic_Information();//个人信息
                } else {
                    tran2.show(basic_information);
                }

                if (company_asset_information != null) {
                    tran2.hide(company_asset_information);
                }
                if (company_demand_information != null) {
                    tran2.hide(company_demand_information);
                }
                if (share_information != null) {
                    tran2.hide(share_information);
                }
                if (business_information != null) {
                    tran2.hide(business_information);
                }
                if (company_debts_information != null) {
                    tran2.hide(company_debts_information);
                }


                break;
            case R.id.radio2://资产信息
                onClickAdd();

                title_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Debt_Matter_Management_Details.this, New_Assets.class).putExtra("titlename", name).putExtra("id", id));
                    }
                });
                if (company_asset_information == null) {
                    company_asset_information = new Company_Asset_Information();
                    tran2.add(R.id.frame, company_asset_information);
                } else {
                    tran2.show(company_asset_information);
                }
                if (basic_information != null) {
                    tran2.hide(basic_information);
                }
                if (company_demand_information!= null) {
                    tran2.hide(company_demand_information);
                }
                if (share_information != null) {
                    tran2.hide(share_information);
                }
                if (business_information != null) {
                    tran2.hide(business_information);
                }
                if (company_debts_information != null) {
                    tran2.hide(company_debts_information);
                }


                break;
            case R.id.radio3:
                onClickAdd();

                title_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Debt_Matter_Management_Details.this, New_Demand.class).putExtra("titlename", name).putExtra("id", id));
                    }
                });
                if (company_demand_information == null) {
                    company_demand_information = new Company_Demand_Information();

                    tran2.add(R.id.frame, company_demand_information);
                } else {
                    tran2.show(company_demand_information);
                }
                if (basic_information != null) {
                    tran2.hide(basic_information);
                }
                if (company_asset_information != null) {
                    tran2.hide(company_asset_information);
                }
                if (share_information != null) {
                    tran2.hide(share_information);
                }
                if (business_information != null) {
                    tran2.hide(business_information);
                }
                if (company_debts_information!= null) {
                    tran2.hide(company_debts_information);
                }

                break;
            case R.id.radio4:
                onClickAdd();
                title_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Debt_Matter_Management_Details.this, New_Share.class).putExtra("titlename", name).putExtra("id", id));
                    }
                });
                if (share_information == null) {
                    share_information = new Share_Information();
                    tran2.add(R.id.frame, share_information);
                } else {
                    tran2.show(share_information);
                }
                if (basic_information != null) {
                    tran2.hide(basic_information);
                }
                if (company_demand_information != null) {
                    tran2.hide(company_demand_information);
                }
                if (company_asset_information != null) {
                    tran2.hide(company_asset_information);
                }
                if (business_information != null) {
                    tran2.hide(business_information);
                }
                if (company_debts_information!= null) {
                    tran2.hide(company_debts_information);
                }

                break;
            case R.id.radio5://经营信息
                onClickAdd();

                title_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Debt_Matter_Management_Details.this, New_Buiness.class).putExtra("titlename", name));
                    }
                });
                if (business_information == null) {
                    business_information = new Business_Information();
                    tran2.add(R.id.frame, business_information);
                } else {
                    tran2.show(business_information);
                }
                if (basic_information != null) {
                    tran2.hide(basic_information);
                }
                if (company_demand_information != null) {
                    tran2.hide(company_demand_information);
                }
                if (company_asset_information!= null) {
                    tran2.hide(company_asset_information);
                }
                if (share_information != null) {
                    tran2.hide(share_information);
                }
                if (company_debts_information != null) {
                    tran2.hide(company_debts_information);
                }

                break;
            case R.id.radio6:
                onClickAdd();
                title_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCollector.startA(Debt_Matter_Management_Details.this, New_Debt_Matter.class,"back",2);
                    }
                });
                if (company_debts_information == null) {
                    company_debts_information = new Company_Debts_Information();
                    tran2.add(R.id.frame, company_debts_information);
                } else {
                    tran2.show(company_debts_information);
                }
                if (basic_information != null) {
                    tran2.hide(basic_information);
                }
                if (company_demand_information != null) {
                    tran2.hide(company_demand_information);
                }
                if (company_asset_information != null) {
                    tran2.hide(company_asset_information);
                }
                if (business_information != null) {
                    tran2.hide(business_information);
                }
                if (share_information != null) {
                    tran2.hide(share_information);
                }
                break;
        }
        tran2.commit();
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
    protected void onDestroy() {
        super.onDestroy();
    }
}

