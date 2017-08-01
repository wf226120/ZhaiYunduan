package com.jlgproject;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jlgproject.R;
import com.jlgproject.activity.Login;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.fragment.DebtMatterManagmentFragment;
import com.jlgproject.fragment.DebtMatterPersonFragment;
import com.jlgproject.fragment.HomePageFragment;
import com.jlgproject.fragment.MyPageFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.VersionBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.AppInfoUtil;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity implements HttpMessageInfo.IMessage {

    private final static int BOTTOM_MENU_HOME_PAGE = 0;//首页常量
    private final static int BOTTOM_MENU_ZHAISHI_GL = 1;//债事管理常量
    private final static int BOTTOM_MENU_ZS_RERSON_GL = 2;//债事人管理常量
    private final static int BOTTOM_MENU_MY_ACCOUNT = 3;//我的常量
    private int currMenu = BOTTOM_MENU_HOME_PAGE;//默认首页

    private FragmentManager fragmentManager;
    private HomePageFragment homePageFragment;//首页
    private DebtMatterManagmentFragment debtMatterManagmentFragment;// 债事管理
    private DebtMatterPersonFragment debtMatterPersonFragment;//债事人管理
    private MyPageFragment myPageFragment;//我的


    private ImageButton btnHome;//首页
    private ImageButton btnDebtMatterManag;//债事管理
    private ImageButton btnDebtMatterPerson;//债事人管理
    private ImageButton btnMy;//我的
    private LinearLayout bottomBarLayout;//外层包裹

    private long singTime = 0L;//记录时长

    private LinearLayout ll_homePage;
    private LinearLayout ll_debt_manag;
    private LinearLayout ll_debt_manag_person;
    private LinearLayout ll_myLayout;
    public static int newCurrMenu;
    private int index;
    private int state=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(state==1){
            // 检查应用是否有更新
            getServiceApkMessage();
        }

        SharedUtil.getSharedUtil().remove(this, ConstUtils.CANCEL_AGREEMENT);
        if (this.currMenu == BOTTOM_MENU_HOME_PAGE) {// 首页
            ll_homePage.performClick();
        } else if (this.currMenu == BOTTOM_MENU_ZHAISHI_GL) {// 债市管理
            ll_debt_manag.performClick();
        } else if (this.currMenu == BOTTOM_MENU_ZS_RERSON_GL) {// 债事人管理
            ll_debt_manag_person.performClick();
        } else if (this.currMenu == BOTTOM_MENU_MY_ACCOUNT) {// 我的
            ll_myLayout.performClick();
        }
    }

    @Override
    public void initViews() {
        bottomBarLayout = (LinearLayout) findViewById(R.id.ll_linear);
        fragmentManager = getSupportFragmentManager();
        //首页
        btnHome = (ImageButton) findViewById(R.id.ib_btnHomePage);
        btnHome.setSelected(true);
        ll_homePage = (LinearLayout) findViewById(R.id.ll_homePage);
        ll_homePage.setOnClickListener(btnClickListener);

        //债事管理
        btnDebtMatterManag = (ImageButton) findViewById(R.id.ib_btn_Debt_manag);
        ll_debt_manag = (LinearLayout) findViewById(R.id.ll_debt_manag);
        ll_debt_manag.setOnClickListener(btnClickListener);

        //债事人管理
        btnDebtMatterPerson = (ImageButton) findViewById(R.id.ib_debt_manag_person);
        ll_debt_manag_person = (LinearLayout) findViewById(R.id.ll_debt_manag_person);
        ll_debt_manag_person.setOnClickListener(btnClickListener);

        //我的
        btnMy = (ImageButton) findViewById(R.id.ib_MyPage);
        ll_myLayout = (LinearLayout) findViewById(R.id.ll_myLayout);
        ll_myLayout.setOnClickListener(btnClickListener);


        // 检查应用是否有更新
        getServiceApkMessage();
        String brand = android.os.Build.BRAND;
        L.e("--------手机型号-------" + brand);
        L.e("----王锋最帅----");
    }

    /**
     * 因为启动模式是singleTask
     * onNewIntent 在onReaume 之后执行
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        newCurrMenu = getIntent().getIntExtra("currMenu", -1);
        index = getIntent().getIntExtra("pager", 0);
        L.e("------index-----" + index);
        if (newCurrMenu == -1 || newCurrMenu == 0) {//从开行页跳回的 or 我的
            currMenu = BOTTOM_MENU_HOME_PAGE;
        } else if (newCurrMenu == 1) {//债事管理
            currMenu = BOTTOM_MENU_ZHAISHI_GL;
        } else if (newCurrMenu == 2) {//债事人管理
            currMenu = BOTTOM_MENU_ZS_RERSON_GL;
        } else if (newCurrMenu == 3) {//支付成功后跳转到我的页
            currMenu = BOTTOM_MENU_MY_ACCOUNT;
        }
    }

    private final View.OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ll_homePage: // 首页
                    changeMenu(BOTTOM_MENU_HOME_PAGE);
                    break;
                case R.id.ll_debt_manag: // 债事管理
//                    financeMenuIndex = 0;
                    changeMenu(BOTTOM_MENU_ZHAISHI_GL);
                    break;
                case R.id.ll_debt_manag_person: // 债事人管理
                    changeMenu(BOTTOM_MENU_ZS_RERSON_GL);
                    break;
                case R.id.ll_myLayout: // 我的
                    changeMenu(BOTTOM_MENU_MY_ACCOUNT);
                    break;
            }
        }
    };


    /**
     * 切换菜单
     *
     * @param menu
     */
    private void changeMenu(int menu) {
        this.currMenu = menu;
        setCurrMenuState();
        changeFragment();
    }

    /**
     * 状态判断
     */
    private void setCurrMenuState() {

        if (this.currMenu == BOTTOM_MENU_HOME_PAGE) {// 首页
            btnHome.setSelected(true);
            btnDebtMatterManag.setSelected(false);
            btnDebtMatterPerson.setSelected(false);
            btnMy.setSelected(false);
        } else if (this.currMenu == BOTTOM_MENU_ZHAISHI_GL) {// 债事管理
            btnHome.setSelected(false);
            btnDebtMatterManag.setSelected(true);
            btnDebtMatterPerson.setSelected(false);
            btnMy.setSelected(false);
        } else if (this.currMenu == BOTTOM_MENU_ZS_RERSON_GL) {// 债事人管理
            btnHome.setSelected(false);
            btnDebtMatterManag.setSelected(false);
            btnDebtMatterPerson.setSelected(true);
            btnMy.setSelected(false);
        } else if (this.currMenu == BOTTOM_MENU_MY_ACCOUNT) {// 我的
            btnHome.setSelected(false);
            btnDebtMatterManag.setSelected(false);
            btnDebtMatterPerson.setSelected(false);
            btnMy.setSelected(true);
        }
    }

    /**
     * 切换fragmnet
     */
    private void changeFragment() {
        FragmentTransaction trans = fragmentManager.beginTransaction();

        if (currMenu == BOTTOM_MENU_HOME_PAGE) {//首页Fragment

            if (homePageFragment == null) {
                homePageFragment = new HomePageFragment();
                trans.add(R.id.fl_contentLayout, homePageFragment);
            } else {
                trans.show(homePageFragment);
            }
//            this.homePageFragment.setOnCurrentPocketListener(this.currentPocketListener);

            //隐藏债事管理Fragment
            if (debtMatterManagmentFragment != null) {
                trans.hide(debtMatterManagmentFragment);
            }
            //隐藏债事人管理Fragment
            if (debtMatterPersonFragment != null) {
                trans.hide(debtMatterPersonFragment);
            }
            //隐藏我的Fragment
            if (this.myPageFragment != null) {
                trans.hide(myPageFragment);
            }

        } else if (currMenu == BOTTOM_MENU_ZHAISHI_GL) {//债事管理

            if (UserInfoState.isLogin()) {
                if (debtMatterManagmentFragment == null) {
                    debtMatterManagmentFragment = new DebtMatterManagmentFragment();
                    trans.add(R.id.fl_contentLayout, debtMatterManagmentFragment);
                } else {
                    trans.show(debtMatterManagmentFragment);
                }
            } else {
                ActivityCollector.startA(this, Login.class, ConstUtils.PD, 1);
            }


            //隐藏首页Fragment
            if (homePageFragment != null) {
                trans.hide(homePageFragment);
            }

            //隐藏债事人管理Fragment
            if (debtMatterPersonFragment != null) {
                trans.hide(debtMatterPersonFragment);
            }
            //隐藏我的Fragment
            if (this.myPageFragment != null) {
                trans.hide(myPageFragment);
            }

        } else if (currMenu == BOTTOM_MENU_ZS_RERSON_GL) {//债事人管理

            if (UserInfoState.isLogin()) {
                //显示债事人管理页
                if (debtMatterPersonFragment == null) {
                    debtMatterPersonFragment = new DebtMatterPersonFragment();
                    trans.add(R.id.fl_contentLayout, debtMatterPersonFragment);
                } else {
                    trans.show(debtMatterPersonFragment);
                }
            } else {
                ActivityCollector.startA(this, Login.class, ConstUtils.PD, 2);
            }

            //隐藏首页Fragment
            if (homePageFragment != null) {
                trans.hide(homePageFragment);
            }
            //隐藏债事管理Fragment
            if (debtMatterManagmentFragment != null) {
                trans.hide(debtMatterManagmentFragment);
            }
            //隐藏我的Fragment
            if (this.myPageFragment != null) {
                trans.hide(myPageFragment);
            }
        } else if (currMenu == BOTTOM_MENU_MY_ACCOUNT) {


            if (!UserInfoState.isLogin()) {
                ToastUtil.show(this, "请先登录", Toast.LENGTH_SHORT);
            }
            //显示我的页
            if (myPageFragment == null) {
                myPageFragment = new MyPageFragment();
                trans.add(R.id.fl_contentLayout, myPageFragment);
            } else {
                trans.show(myPageFragment);
            }

            //隐藏首页Fragment
            if (homePageFragment != null) {
                trans.hide(homePageFragment);
            }
            //隐藏债事管理Fragment
            if (debtMatterManagmentFragment != null) {
                trans.hide(debtMatterManagmentFragment);
            }
            //隐藏债事人管理Fragment
            if (debtMatterPersonFragment != null) {
                trans.hide(debtMatterPersonFragment);
            }
        }
        trans.commitAllowingStateLoss();
    }


    @Override
    public void onBackPressed() {
        if (btnHome.isSelected()) {
            if (this.singTime > 0) {

                long nowTime = System.currentTimeMillis();
                if ((nowTime - this.singTime) < 2000) {
                    finish();
                    ActivityCollector.finishAll();
                } else {
                    this.singTime = nowTime;
                    ToastUtil.showShort(MainActivity.this, "再按一次退出");
                }
            } else {
                this.singTime = System.currentTimeMillis();
                ToastUtil.showShort(MainActivity.this, "再按一次退出");
            }
        } else {
            changeMenu(BOTTOM_MENU_HOME_PAGE);
        }
    }

    private void getServiceApkMessage() {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<VersionBean> info = new HttpMessageInfo<>(BaseUrl.VERSION, new VersionBean());
            info.setiMessage(this);
            GetParmars getParmars = new GetParmars();
            getParmars.add("type", "android");
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, getParmars, null, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof VersionBean) {
            final VersionBean versionBean = (VersionBean) o;
            if (versionBean != null) {
                DismissDialog();
                //本地
                String lockVersion = AppInfoUtil.getVersionName();
                String service = versionBean.getData().getVersionNum();//服务器
                String tishiyu;
                if (!TextUtils.isEmpty(versionBean.getData().getUpdateItems().toString())) {
                    tishiyu = versionBean.getData().getUpdateItems().toString();
                } else {
                    tishiyu = "有新版本可以更新了";
                }
                if (!service.equals(lockVersion)) { //有更新
                    DialogUtils.showDialogVersion(this, true, tishiyu, new DialogUtils.OnButtonEventListener1() {
                        @Override
                        public void onConfirm() {
                            if (versionBean.getData().getIsForce() == 1) {//强制更新
                                state=1;
                            }else{
                                state=2;
                            }
//                            Intent intent = new Intent(Intent.ACTION_VIEW);
//                            intent.setData(Uri.parse("market://details?id="+"com.jlgproject"));//其中的com.lkk.travel是自己app的包名
//                            startActivity(intent);

                            AppInfoUtil.intit_getClick(getApplication(), AppInfoUtil.getAppPkgName());
//                            AppInfoUtil.upData(getApplication(), AppInfoUtil.getAppPkgName());
                        }

                        @Override
                        public void onCancel(AlertDialog dialog) {
                            if (versionBean.getData().getIsForce() == 1) {//强制更新
                                ActivityCollector.finishAll();
                                L.e("---强制更新---取消后--退出应用---");
                            } else {  //
                                dialog.dismiss();
                                L.e("---非强跟- 00000 --");
                            }
                        }
                    });
                } else {
                    L.e("-----------没有更新-------");
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
    }
}
