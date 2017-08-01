package com.jlgproject.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.adapter.DebtFragmentAdapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.fragment.DebtNaturalPersonFrament;
import com.jlgproject.fragment.EnterpriseDebtMatterFragment;
import com.jlgproject.model.eventbusMode.DebtPreson;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbeibei on 2017/4/28.
 * 关于新增债事人activity
 * 管理债事企业和债事自然人的fragment
 */

public class New_Debt_Matter_Preson extends BaseActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{


    private TextView title_name;
    private ImageView img_left;
    public static String isOwer="2";
    public int backIndex;

    private DebtFragmentAdapter debtFragmentAdapter;
    // 碎片集合
    private List<Fragment> fragments;
    // 按钮数组
    private RelativeLayout[] arrBtn = new RelativeLayout[2];
    // 标签文字数组
    private TextView[] arrTxt = new TextView[2];
    // 标签下划线(Indicator)
    private RelativeLayout[] arrLine = new RelativeLayout[2];
    // 滑动页容器
    private ViewPager viewPager;
    // 选中的标签颜色
    int color_selected = R.color.red;
    // 未选中的标签颜色
    int color_unselected = R.color.black;

    @Override
    public int loadWindowLayout() {
        return R.layout.new_debt_matter_preson;
    }

    @Override
    public void initViews() {
        initview();
    }

    private void initview() {
        title_name = (TextView) findViewById(R.id.tv_title_name);
        title_name.setText("添加债事人");
        img_left = (ImageView) findViewById(R.id.iv_title_left);
        img_left.setImageResource(R.drawable.back3);
        img_left.setVisibility(View.VISIBLE);
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initStyle();
        initView_s();
        // 准备碎片
        fragments = new ArrayList<Fragment>();
        fragments.add(new EnterpriseDebtMatterFragment());//债事企业
        fragments.add(new DebtNaturalPersonFrament());//债事个人
        // 实例化适配器
        debtFragmentAdapter = new DebtFragmentAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(debtFragmentAdapter);// 关联适配器
        initListener();
        viewPager.setCurrentItem(0);
        setColor(0);


        int vul=getIntent().getIntExtra(ConstUtils.IS_OWER,0);
        if(vul==1){//该值若为1时，表示添加的债事人或债事企业 是登录用户的
            isOwer=1+"";
        }else{ //其他债事人 或 债事企业
            isOwer=2+"";
        }
    }

    // 改变颜色
    private void initStyle() {
        color_selected = R.color.red;
    }

    private void initListener() {
        // 添加按钮的监听
        for (int i = 0; i < arrBtn.length; i++) {
            arrBtn[i].setOnClickListener(this);
        }
        // 添加滑动页的监听
        viewPager.addOnPageChangeListener(this);
    }

    private void initView_s() {
        // 初始化下划线(逐帧动画)
        String packageName = getApplicationContext().getPackageName();//获取当前包名
        for (int i = 0; i < 2; i++) {
            //从图片名称反射资源ID  R.id.line1
            int id = this.getResources().getIdentifier("line_Wf" + (i + 1), "id", packageName);
            arrLine[i] = (RelativeLayout) findViewById(id);
            int id2 = this.getResources().getIdentifier("btn_Wf" + (i + 1), "id", packageName);
            arrBtn[i] = (RelativeLayout) findViewById(id2);
            int id3 = this.getResources().getIdentifier("txt_Wf" + (i + 1), "id", packageName);
            arrTxt[i] = (TextView)findViewById(id3);
            // 设置标签名
            arrTxt[i].setText(ConstUtils.Tab_Zsr_Name[i]);
        }
        // 获取ViewPager对象
        viewPager = (ViewPager) findViewById(R.id.vp_tjzrr);
    }

    /**
     * 1.将所有的背景统一颜色
     * 2.将当前选中的背景设置特殊颜色
     * @param index
     */
    public void setColor(int index){
        // "所有人"都回复最初的状态
        for (int i = 0; i<arrBtn.length; i++){
            arrLine[i].setBackgroundColor(getResources().getColor(R.color.xian));
            arrTxt[i].setTextColor(getResources().getColor(color_unselected));
        }
        arrLine[index].setBackgroundColor(getResources().getColor(color_selected));// 特殊
        arrTxt[index].setTextColor(getResources().getColor(color_selected));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 滑动过程中...(写动画)
    }

    @Override
    public void onPageSelected(int position) {
        // 页面的选中(当前的页面已经显示了90%)
        setColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // 滑动的状态改变
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Wf1:
                viewPager.setCurrentItem(0);// 第一页
                break;
            case R.id.btn_Wf2:
                viewPager.setCurrentItem(1);// 第二页
                break;
            default:
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        int finish=getIntent().getIntExtra("finish",0);
        if(finish==1){
            finish();
        }
    }

    //新增债事人 债事企业
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDebePerson(DebtPreson debtPreson) {
        backIndex = debtPreson.getDebtpVul();
        L.e("----backIndex----" + backIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UserInfoState.isLogin()) {
            ActivityCollector.pleaseLogin(this,4);
        }
        if(backIndex==2){
            L.e("----backIndex-----onResume--" + backIndex);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isOwer=null;
    }


}
