package com.jlgproject.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.activity.DialogActivity;
import com.jlgproject.activity.Login;
import com.jlgproject.activity.MyBill;
import com.jlgproject.activity.MyWallet;
import com.jlgproject.activity.My_Personal_Data;
import com.jlgproject.activity.RecommendMember;
import com.jlgproject.activity.RecommendPresident;
import com.jlgproject.activity.TwoCodeDialogActivity;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.MyPagerResponse;
import com.jlgproject.model.UserType;
import com.jlgproject.model.eventbusMode.CurrentItem;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.AppInfoUtil;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;
import com.jlgproject.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.Call;

/**
 * 我的 fragment
 */
public class MyPageFragment extends BaseFragment implements View.OnClickListener, HttpMessageInfo.IMessage, SwipeRefreshLayout.OnRefreshListener {

    //设置title
    private TextView mTv_Title_my, mTv_title_right;
    private TextView mMy_geren,//个人信息
            mTv_my_vip,//我的会员
            mTv_my_gx,//版本更新
            mTv_my_fk,//意见反馈
            mTv_my_lxwm;//联系我们
    private LinearLayout mLl_my_tjbas,//推荐备案数
            mLl_my_tjhzs,//推荐行长数
            mLl_my_jzs,//解债数
            mLl_my_jzhy,//推荐会员
            mLl_my_zd,//我的账单
            mLl_my_qb;//我的钱包
    private int width;
    private TextView tv_banbenCode;

    private AlertDialog alertDialog;
    private View view1;
    private Button cancle;
    private Button confire;
    private ImageView touxiang, mIv_vip_huangguan, miv_ewm;
    private TextView userName, tv_type, tv_jjm, mTv_my_tjbas, mTv_my_jzhy, mTv_my_tjhz, mTv_my_jzs;
    private String updateData = null;
    private SwipeRefreshLayout my_refresh;
    MyPagerResponse my = null;

    @Override
    public int getLoadViewId() {
        return R.layout.fragment_my_page;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    public View initView(View view) {
        EventBus.getDefault().register(this);
        mTv_Title_my = (TextView) view.findViewById(R.id.tv_title_name);
        mTv_Title_my.setText(getResources().getText(R.string.my));
        mTv_title_right = (TextView) view.findViewById(R.id.tv_title_right);//登录
        mTv_title_right.setText(getResources().getText(R.string.string_login_btn));
        mTv_title_right.setTextColor(ContextCompat.getColor(getActivity(), R.color.bai));
        mTv_title_right.setOnClickListener(this);
        touxiang = (ImageView) view.findViewById(R.id.touxiang);
        touxiang.setOnClickListener(this);
        userName = (TextView) view.findViewById(R.id.tv_userName);//用户名
        tv_type = (TextView) view.findViewById(R.id.tv_type);//等级
        mIv_vip_huangguan = (ImageView) view.findViewById(R.id.iv_vip_huangguan);//等级头像
        tv_jjm = (TextView) view.findViewById(R.id.tv_jjm);//推荐码
        mMy_geren = (TextView) view.findViewById(R.id.tv_my_geren);
        mMy_geren.setOnClickListener(this);
        mLl_my_tjbas = (LinearLayout) view.findViewById(R.id.ll_my_tjbas);
        mLl_my_tjbas.setOnClickListener(this);
        mTv_my_tjbas = (TextView) view.findViewById(R.id.tv_my_tjbas);//推荐备案数
        mLl_my_jzs = (LinearLayout) view.findViewById(R.id.ll_my_jzs);
        mLl_my_jzs.setOnClickListener(this);
        mTv_my_jzs = (TextView) view.findViewById(R.id.tv_my_jzs);//接债数
        mLl_my_tjhzs = (LinearLayout) view.findViewById(R.id.ll_my_tjhzs);
        mLl_my_tjhzs.setOnClickListener(this);
        mTv_my_tjhz = (TextView) view.findViewById(R.id.tv_my_tjhz);//推荐行长数
        mLl_my_jzhy = (LinearLayout) view.findViewById(R.id.ll_my_jzhy);
        mLl_my_jzhy.setOnClickListener(this);
        mTv_my_jzhy = (TextView) view.findViewById(R.id.tv_my_jzhy);//推荐会员数
        mLl_my_zd = (LinearLayout) view.findViewById(R.id.ll_my_zd);
        mLl_my_qb = (LinearLayout) view.findViewById(R.id.ll_my_qb);
        mLl_my_zd.setOnClickListener(this);
        mLl_my_qb.setOnClickListener(this);
        mTv_my_vip = (TextView) view.findViewById(R.id.tv_my_vip);
        mTv_my_vip.setOnClickListener(this);
        mTv_my_gx = (TextView) view.findViewById(R.id.tv_my_gx);
        mTv_my_gx.setOnClickListener(this);
        mTv_my_fk = (TextView) view.findViewById(R.id.tv_my_fk);
        mTv_my_fk.setOnClickListener(this);
        mTv_my_lxwm = (TextView) view.findViewById(R.id.tv_my_lxwm);
        mTv_my_lxwm.setOnClickListener(this);
        miv_ewm = (ImageView) view.findViewById(R.id.iv_ewm);//二维码
        miv_ewm.setOnClickListener(this);
        miv_ewm.setVisibility(View.GONE);  //2.0.0暂且隐藏二维码
        tv_banbenCode = (TextView) view.findViewById(R.id.tv_banbenCode);//版本号
        tv_banbenCode.setText(AppInfoUtil.getVersionName());
        my_refresh = (SwipeRefreshLayout) view.findViewById(R.id.my_refresh);
        my_refresh.setOnRefreshListener(this);
        my_refresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        view.findViewById(R.id.ll_my_tjbas);
        if (UserInfoState.isLogin()) {
            getUserType();
            getMyPager();
        } else {
            ToastUtil.show(getActivity(), "请先登录", Toast.LENGTH_SHORT);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mTv_title_right) {//登录
            isLogin();
        } else if (v == mLl_my_tjbas) {//推荐备案数
            if (UserInfoState.isLogin() && UserInfoState.getUserType() == 1) {
                ActivityCollector.startA(getActivity(), MainActivity.class, "currMenu", 1);
            }
        } else if (v == mLl_my_tjhzs) {//推荐行长数
            if (UserInfoState.isLogin() &&UserInfoState.getUserType() == 1) {
                ActivityCollector.startA(getActivity(), RecommendPresident.class);
            }
        } else if (v == mLl_my_jzs) {//解债数
            EventBus.getDefault().postSticky(new CurrentItem(1));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityCollector.startA(getActivity(), MainActivity.class,"currMenu", 1);
                }
            },500);

        } else if (v == mLl_my_jzhy) {//推荐会员
            if (UserInfoState.isLogin() && UserInfoState.getUserType() == 1) {
                ActivityCollector.startA(getActivity(), RecommendMember.class);
            }
        } else if (v == mMy_geren) {//个人资料
            isLoginPreson();
        } else if (v == touxiang) {//点击头像
            isLoginPreson();
        } else if (v == miv_ewm) {//二维码
            startActivity(new Intent(getActivity(), TwoCodeDialogActivity.class));
        } else if (v == mTv_my_vip) {//我的会员
        } else if (v == mLl_my_zd) {//我的账单
            ToastUtil.showShort(getActivity(), "该功能正在开发中");
//            ActivityCollector.startA(getActivity(), MyBill.class);
        } else if (v == mLl_my_qb) {//我的钱包
            ToastUtil.showShort(getActivity(), "该功能正在开发中");
//            ActivityCollector.startA(getActivity(), MyWallet.class);
        } else if (v == mTv_my_gx) {//版本更新

        } else if (v == mTv_my_fk) {//意见反馈
            ActivityCollector.startA(getActivity(), DialogActivity.class, "dialogIndex", 3);
        } else if (v == mTv_my_lxwm) {//联系我们
            tellPhone();
        }
    }

    private void isLoginPreson() {
        if (UserInfoState.isLogin()) {
            startActivity(new Intent(getActivity(), My_Personal_Data.class));
        } else {
            ToastUtil.showShort(getActivity(), "请先登录");

        }
    }

    private void isLogin() {
        if (mTv_title_right.getText().equals("登录")) {
            ActivityCollector.startA(getActivity(), Login.class);
        } else if (mTv_title_right.getText().equals("退出")) {

            DialogUtils.showDialogEsc(getActivity(), true, new DialogUtils.OnButtonEventListener() {
                @Override
                public void onConfirm() {
                    SharedUtil.getSharedUtil().remove(getActivity(), ConstUtils.IOGIN_INFO);
                    if (!UserInfoState.isLogin()) {
                        L.e("----我的页面--信息已清空------");
                        mTv_title_right.setText("登录");
                        //退出后，清空用户信息
                        tv_type.setText("普通用户");
                        userName.setText("");
                        tv_jjm.setText("");//推荐编码
                        mTv_my_tjbas.setText("0");//推荐备案数
                        mTv_my_tjhz.setText("0");//开行数
                        mTv_my_jzs.setText("0");//解债数
                        mTv_my_jzhy.setText("0");
                        touxiang.setImageResource(R.mipmap.my_userimage);
                        morenState();
                        SharedUtil.getSharedUtil().putString(getActivity(), ConstUtils.ESC, "esc");
                    }
                }

                @Override
                public void onCancel() {
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Login_zud login = (Login_zud) SharedUtil.getSharedUtil().getObject(getActivity(), ConstUtils.IOGIN_INFO, null);
        if (login != null) {
            mTv_title_right.setText("退出");
        }
    }

    private void tellPhone() {
        view1 = LayoutInflater.from(getActivity()).inflate(R.layout.tellphone, null);
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        alertDialog = DialogUtils.getBuilder(view1, getActivity(), width);
        cancle = (Button) view1.findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        confire = (Button) view1.findViewById(R.id.confirm);
        confire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + "4000689588"));
                startActivity(intent);
            }
        });
    }

    public void getMyPager() {

        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                HttpMessageInfo<MyPagerResponse> info = new HttpMessageInfo<MyPagerResponse>(BaseUrl.MY_PAGER, new MyPagerResponse());
                info.setiMessage(this);
                AddHeaders header = new AddHeaders();
                header.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, null, header, 1);
            }
        }
    }

    public void getUserType() {
        L.e("----请求用户type----");
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(getActivity())) {
                HttpMessageInfo<UserType> info = new HttpMessageInfo<>(BaseUrl.USER_TYPE, new UserType());
                info.setiMessage(this);
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, null, headers, 1);
            }
        }
    }

    @Override
    public void getServiceData(Object o) {

        if (o instanceof MyPagerResponse) {  //我的页接口
            my = (MyPagerResponse) o;
            if (my != null) {
                DismissDialog();
                if (my.getState().equals("ok")) {
                    L.e("------手机号----" + my.getData().getPhone());
                    setViewParameter();
                    EventBus.getDefault().postSticky(my);
                    my_refresh.setRefreshing(false);
                } else {
                    ToastUtil.showShort(getActivity(), my.getMessage());
                }
            }
        } else if (o instanceof UserType) { //改变用户身份状态
            UserType userType = (UserType) o;
            if (userType != null) {
                DismissDialog();
                if (userType.getState().equals("ok")) {
                    my_refresh.setRefreshing(false);
                    UserInfoState.setUserType(userType.getData().getUserType());
                    UserInfoState.setUserPhone(userType.getData().getPhoneNumber());
                    int userVul = UserInfoState.getUserType();
                    setUserType(userVul);
                }
            }
        }
    }


    private void setUserType(int userVul) {
        if (userVul == 1) {//行长
            if (my != null) {
                hangZhangState();
            } else { //我的页信息若为空去请求服务器
                getMyPager();
            }
        } else if (userVul == 2) {//会员
            tv_type.setText("会员");
            morenState();
        } else if (userVul == 3) {//普通用户
            tv_type.setText("普通用户");
            morenState();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getMyPagerUpdateData(String string) {
        updateData = string;
        L.e("----updateData----" + updateData);
    }

    private void setViewParameter() {
        userName.setText(my.getData().getUsername().toString());
        Glide.with(getActivity()).load(my.getData().getImage().toString()).placeholder(R.mipmap.my_userimage)//加载时图片
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .crossFade(1000)
                .error(R.mipmap.my_userimage)//错误
                .into(touxiang);
        mTv_my_tjbas.setText(my.getData().getZhaishi() + "");//推荐备案数
        mTv_my_tjhz.setText(my.getData().getKaihang() + "");//开行数
        mTv_my_jzs.setText(my.getData().getJiezhai() + "");//解债数
        mTv_my_jzhy.setText(my.getData().getHuiyuan() + "");//推荐会员数

        int identityState = my.getData().getType();//用户身份状态
        if (identityState == 1) {//行长
            hangZhangState();
        } else if (identityState == 2) {//会员
            puTong_vip("会员");
        } else if (identityState == 3) {//普通用户
            puTong_vip("普通用户");
        }
    }

    //行长状态
    private void hangZhangState() {
        tv_type.setText(my.getData().getHangzhang());
        tv_jjm.setText(my.getData().getRecommendCode());//推荐编码
        tv_type.setTextColor(getResources().getColor(R.color.red));
        tv_type.setBackground(getResources().getDrawable(R.mipmap.my_yellow));
        mIv_vip_huangguan.setImageResource(R.mipmap.my_vip);
    }

    //普通用户 && 会员
    private void puTong_vip(String str) {
        tv_type.setText(str);
        morenState();
    }

    //普通用户 && 会员 默认背景
    private void morenState() {
        tv_type.setTextColor(getResources().getColor(R.color.gray));
        tv_type.setBackground(getResources().getDrawable(R.mipmap.my_grey));
        mIv_vip_huangguan.setImageResource(R.mipmap.my_no_vip);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {// 在最前端显示 相当于调用了onResume();
            if (UserInfoState.isLogin()) {
                getUserType();
                getMyPager();
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        my_refresh.setRefreshing(false);
        DismissDialog();
        ToastUtil.showShort(getActivity(), "服务器繁忙，请稍后再试");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh() {

        if (UserInfoState.isLogin()) {
            if (NetUtils.isConnected(getActivity())) {
                getUserType();
                getMyPager();
            }
        } else {
            my_refresh.setRefreshing(false);
            ToastUtil.show(getActivity(), "请先登录", Toast.LENGTH_SHORT);
        }
    }


}




