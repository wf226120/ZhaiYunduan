package com.jlgproject.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.activity.H_BusinessSchool;
import com.jlgproject.activity.H_DebtShoppingMall;
import com.jlgproject.activity.H_GoldEye;
import com.jlgproject.activity.H_OpenDebt;
import com.jlgproject.activity.Login;
import com.jlgproject.activity.New_Debt_Matter;
import com.jlgproject.activity.New_Debt_Matter_Preson;
import com.jlgproject.activity.NewsDetails;
import com.jlgproject.activity.NewsPage;
import com.jlgproject.adapter.HomePageAdapter;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.loader.GlideImageLoader;
import com.jlgproject.model.Bnner_News;
import com.jlgproject.model.UserType;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;
import com.jlgproject.view.HomeListView;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 首页 fragment
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener, OnBannerListener, HttpMessageInfo.IMessage, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTv_title_name, mTv_title_right;//title name
    private HomeListView mListView_home;//首页ListView
    private Banner banner;
    private LinearLayout ll_home_zsba;
    private LinearLayout ll_home_ktzh;
    private LinearLayout ll_home_sxy;
    private LinearLayout ll_home_zssc;
    private LinearLayout ll_home_tjzsr;
    private LinearLayout ll_home_zsbk;
    private LinearLayout ll_home_zjty;
    private LinearLayout ll_home_gyzj;
    private LinearLayout ll_home_qbyy;
    private LinearLayout ll_home_hyzx;
    private TextView tv_home_ckgd;//查看更多
    private HomePageAdapter adapter;//首页适配器
    private SwipeRefreshLayout refreshLayout;
    private Bnner_News bnner_news;//首页实体类
    private List<String> imageList;//轮播图集合
    List<Bnner_News.DataBean.SliderBean> sliderList;

    @Override
    public int getLoadViewId() {
        return R.layout.fragment_home_page;
    }

    /**
     * 初始化数据源
     */
    @Override
    public void initDatas() {
        imageList = new ArrayList<>();
    }


    @Override
    public View initView(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //动态设置标题
        mTv_title_name = (TextView) view.findViewById(R.id.tv_title_name);
        mTv_title_name.setText("中金债事智慧云");
        Drawable drawable = getResources().getDrawable(R.mipmap.zyd_logo);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mTv_title_name.setCompoundDrawables(drawable, null, null, null);
        mTv_title_right = (TextView) view.findViewById(R.id.tv_title_right);//快速登录按钮
        mTv_title_right.setText(getResources().getText(R.string.string_login_btn));
        mTv_title_right.setTextColor(getResources().getColor(R.color.bai));
        mTv_title_right.setOnClickListener(this);
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.home_header, null);
        mListView_home = (HomeListView) view.findViewById(R.id.lv_home_List);
        mListView_home.addHeaderView(header);
        initClickLinearLayout(header);
        //轮播图
        banner = (Banner) header.findViewById(R.id.banner);
        // 设置动画
        banner.setBannerAnimation(Transformer.Default);
        tv_home_ckgd = (TextView) header.findViewById(R.id.tv_home_ckgd);
        tv_home_ckgd.setOnClickListener(this);
        adapter = new HomePageAdapter();
        adapter.setContext(getActivity());
        //首页信息不为空
        final Bnner_News bnner_news = (Bnner_News) SharedUtil.getSharedUtil().getObject(getActivity(), ConstUtils.HOME_INFO, null);
        if (bnner_news != null) {//有缓存
            L.e("----有缓存---");
            //新闻
            adapter.setmHomeList(bnner_news.getData().getNews());
            mListView_home.setAdapter(adapter);
            //轮播图
            for (int i = 0; i < bnner_news.getData().getSlider().size(); i++) {
                imageList.add(bnner_news.getData().getSlider().get(i).getUrl());
            }
            banner.setImages(imageList)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(this)
                    .start();
            mListView_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url = bnner_news.getData().getNews().get(position - 1).getUrl();
                    Intent intent = new Intent();
                    intent.putExtra("url", url);
                    intent.setClass(getActivity(), NewsDetails.class);
                    startActivity(intent);
                }
            });
        } else {//没有
            L.e("--没有--缓存---");
            refreshLayout.setRefreshing(true);
            resquestNews();
        }


        getUserType();
        return view;
    }


    /**
     * 初始化点击线性布局
     *
     * @param header
     */
    private void initClickLinearLayout(View header) {
        ll_home_zsba = (LinearLayout) header.findViewById(R.id.ll_home_zsba);
        ll_home_ktzh = (LinearLayout) header.findViewById(R.id.ll_home_ktzh);
        ll_home_sxy = (LinearLayout) header.findViewById(R.id.ll_home_sxy);
        ll_home_zssc = (LinearLayout) header.findViewById(R.id.ll_home_zssc);
        ll_home_tjzsr = (LinearLayout) header.findViewById(R.id.ll_home_tjzsr);
        ll_home_zsbk = (LinearLayout) header.findViewById(R.id.ll_home_zsbk);
        ll_home_zjty = (LinearLayout) header.findViewById(R.id.ll_home_zjty);
        ll_home_gyzj = (LinearLayout) header.findViewById(R.id.ll_home_gyzj);
        ll_home_hyzx = (LinearLayout) header.findViewById(R.id.ll_home_hyzx);
        ll_home_qbyy = (LinearLayout) header.findViewById(R.id.ll_home_qbyy);

        ll_home_zsba.setOnClickListener(this);
        ll_home_ktzh.setOnClickListener(this);
        ll_home_sxy.setOnClickListener(this);
        ll_home_zssc.setOnClickListener(this);
        ll_home_tjzsr.setOnClickListener(this);
        ll_home_zsbk.setOnClickListener(this);
        ll_home_zjty.setOnClickListener(this);
        ll_home_gyzj.setOnClickListener(this);
        ll_home_hyzx.setOnClickListener(this);
        ll_home_qbyy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ll_home_zsba) {//<!--债事备案-->
            if (UserInfoState.isLogin()) {
                ActivityCollector.startA(getActivity(), New_Debt_Matter.class);
            } else {
                ActivityCollector.startA(getActivity(), Login.class);
            }
        } else if (v == ll_home_ktzh) {//开通债行
            if (UserInfoState.isLogin()) {
                ActivityCollector.startA(getActivity(), H_OpenDebt.class);
            } else {
                ActivityCollector.startA(getActivity(), Login.class);
            }
        } else if (v == ll_home_sxy) {//商学院
            //   ToastUtil.showShort(getActivity(),"该功能正在开发中");
            if (UserInfoState.isLogin()) {
                ActivityCollector.startA(getActivity(), H_BusinessSchool.class);
            } else {
                ActivityCollector.startA(getActivity(), Login.class);
            }
        } else if (v == ll_home_zssc) {//债事商城
            ActivityCollector.startA(getActivity(), H_DebtShoppingMall.class);
        } else if (v == ll_home_tjzsr) {//添加债事人
            if (UserInfoState.isLogin()) {
                ActivityCollector.startA(getActivity(), New_Debt_Matter_Preson.class);
            } else {
                ActivityCollector.startA(getActivity(), Login.class);
            }
        } else if (v == ll_home_zsbk) {//债事百科
            ToastUtil.showShort(getActivity(), "该功能正在开发中");
//            ActivityCollector.startA(getActivity(), H_DebtEncyclopedias.class);
        } else if (v == ll_home_zjty) {//中金天眼
            ActivityCollector.startA(getActivity(), H_GoldEye.class);
        } else if (v == ll_home_gyzj) {//中金动态
            ActivityCollector.startA(getActivity(), NewsPage.class);
        } else if (v == ll_home_hyzx) {//会员中心
            ActivityCollector.startA(getActivity(), MainActivity.class, "currMenu", 3);
        } else if (v == ll_home_qbyy) {//全部应用
            ToastUtil.showShort(getActivity(), "该功能正在开发中");
//            ActivityCollector.startA(getActivity(), H_WholeApplication.class);
        } else if (v == mTv_title_right) {//登录页
            //当控件处于显示状态下，才可以去登录（即未登录才可登录）
            if (mTv_title_right.getVisibility() == View.VISIBLE) {
                ActivityCollector.startA(getActivity(), Login.class);
            }
        } else if (v == tv_home_ckgd) {//查看跟多
            ActivityCollector.startA(getActivity(), NewsPage.class);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        modifyState();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            modifyState();
        } else {
            if (!NetUtils.isConnected(getActivity())) {
                ToastUtil.showShort(getActivity(), "请检查网络连接");
                return;
            } else {
                getUserType();
            }
        }
    }


    //获取用户身份状态
    private void getUserType() {
        if (UserInfoState.isLogin()) {
            if(ShowDrawDialog(getActivity())){
                L.e("---------獲取 type---------");
                HttpMessageInfo<UserType> info = new HttpMessageInfo<>(BaseUrl.USER_TYPE, new UserType());
                info.setiMessage(this);
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, null, headers, 1);
            }
        }
    }

    /**
     * 通过判断登录状态，修改界面显示
     */
    public void modifyState() {
        if (UserInfoState.isLogin()) {
            mTv_title_right.setVisibility(View.GONE);
        } else {
            mTv_title_right.setVisibility(View.VISIBLE);
            mTv_title_right.setText(getResources().getText(R.string.string_login_btn));
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Bnner_News) {
            bnner_news = (Bnner_News) o;
            if(bnner_news!=null){
                //获取数据成功后停止刷新
                refreshLayout.setRefreshing(false);
                DismissDialog();
                if (bnner_news.getState().equals("ok")) {
                    Bnner_News bbbb = (Bnner_News) SharedUtil.getSharedUtil().getObject(getActivity(), ConstUtils.HOME_INFO, null);
                    if (bbbb != null) {
                        L.e("---getServiceData-----有缓存---");
                        SharedUtil.getSharedUtil().remove(getActivity(), ConstUtils.HOME_INFO);
                        L.e("---getServiceData-----有缓存----先删--");
                        Bnner_News bbNN = (Bnner_News) SharedUtil.getSharedUtil().getObject(getActivity(), ConstUtils.HOME_INFO, null);
                        if (bbNN == null) {
                            L.e("---getServiceData-----删除成功后----添加--");
                            SharedUtil.getSharedUtil().putObject(getActivity(), ConstUtils.HOME_INFO, bnner_news);
                        }
                    } else {
                        L.e("---getServiceData-----添加缓存---");
                        SharedUtil.getSharedUtil().putObject(getActivity(), ConstUtils.HOME_INFO, bnner_news);
                    }

                    //设置新闻
                    adapter.setmHomeList(bnner_news.getData().getNews());
                    mListView_home.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    mListView_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String url = bnner_news.getData().getNews().get(position - 1).getUrl();
                            Intent intent = new Intent();
                            intent.putExtra("url", url);
                            intent.setClass(getActivity(), NewsDetails.class);
                            startActivity(intent);
                        }
                    });

                    //设置轮播图
                    imageList.clear();
                    for (int i = 0; i < bnner_news.getData().getSlider().size(); i++) {
                        imageList.add(bnner_news.getData().getSlider().get(i).getUrl());
                    }
                    banner.setImages(imageList)
                            .setImageLoader(new GlideImageLoader())
                            .setOnBannerListener(this)
                            .start();
                } else {
                    ToastUtil.showShort(getActivity(), bnner_news.getMessage());
                    refreshLayout.setRefreshing(false);
                }
            }
        } else if (o instanceof UserType) {
            UserType userType = (UserType) o;
            if (userType != null) {
                DismissDialog();
                //获取数据成功后停止刷新
                refreshLayout.setRefreshing(false);
                if (userType.getState().equals("ok")) {
                    L.e("------UserType------" + userType.getData().getUserType());
                    UserInfoState.setUserType(userType.getData().getUserType());
                    UserInfoState.setUserPhone(userType.getData().getPhoneNumber());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        ToastUtil.showShort(getActivity(), "服务器繁忙,请稍后再试");
        refreshLayout.setRefreshing(false);
        DismissDialog();
    }


    private void resquestNews() {
        if(ShowDrawDialog(getActivity())){
            HttpMessageInfo<Bnner_News> info = new HttpMessageInfo<Bnner_News>(BaseUrl.BAN_NEWS, new Bnner_News());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, null, null, 1);
        }
    }

    @Override
    public void onRefresh() {
        //刷新
        if (ShowDrawDialog(getActivity())) {
            resquestNews();
        }
    }

    //为了更好的体验
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        if (bnner_news != null) {
            if (!TextUtils.isEmpty(bnner_news.getData().getSlider().get(position).getOpenUrl())) {
                Intent intent = new Intent();
                intent.putExtra("url", bnner_news.getData().getSlider().get(position).getOpenUrl());
                intent.putExtra("name", bnner_news.getData().getSlider().get(position).getName());
                intent.setClass(getActivity(), NewsDetails.class);
                startActivity(intent);
            }else{
                L.e("----轮播图--OpenUrl--没有---"+position);
            }
        }else{
            L.e("----数据源-null--");
        }
    }
}
