package com.jlgproject.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.TuJianHuYuan_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.TuJianHuiYan;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class RecommendMember extends BaseActivity implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    private TextView mTv_Title_member;
    private ImageView mIv_Title_left_member;
    private LinearLayout mLl_ivParent_member;
    private PullToRefreshListView lv_recommend_member;
    private int pn = 1;
    private int ps = 8;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Handler handler = new Handler();
    private List<TuJianHuiYan.DataBean.ItemsBean> items;
    private TuJianHuYuan_Adapter adapter;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_recommend_member;
    }

    @Override
    public void initViews() {
        mTv_Title_member = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_member.setText(getResources().getText(R.string.recommend_vip));
        mIv_Title_left_member = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_member.setVisibility(View.VISIBLE);
        mLl_ivParent_member = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_recommend_member = (PullToRefreshListView) findViewById(R.id.listview);
        lv_recommend_member.setOnRefreshListener(this);
        lv_recommend_member.setMode(PullToRefreshBase.Mode.BOTH);
        requestTuJianHuiYun();
    }

    private void requestTuJianHuiYun() {
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(this)) {
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", UserInfoState.getToken());
                GetParmars parmars = new GetParmars();
                parmars.add("pn", pn);
                parmars.add("ps", ps);
                HttpMessageInfo<TuJianHuiYan> info = new HttpMessageInfo<>(BaseUrl.TJHY, new TuJianHuiYan());
                info.setiMessage(this);
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
            }
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof TuJianHuiYan && o != null) {
            TuJianHuiYan data = (TuJianHuiYan) o;
            if (data != null) {
                DismissDialog();
                lv_recommend_member.onRefreshComplete();
                List<TuJianHuiYan.DataBean.ItemsBean> items = data.getData().getItems();
                if (data.getState().equals("ok")) {
                    displayData(data);
                } else {
                    ToastUtil.showShort(this, data.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        lv_recommend_member.onRefreshComplete();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }

    private void displayData(TuJianHuiYan data) {
        if (items == null) {
            items = data.getData().getItems();
            adapter = new TuJianHuYuan_Adapter(items, this);
            lv_recommend_member.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else if (status == REFRESH) {
            items.clear();
            items = data.getData().getItems();
            if (items != null) {
                adapter = new TuJianHuYuan_Adapter(items, this);
                lv_recommend_member.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                ToastUtil.showShort(this, "暂无数据");
            }
        } else if (status == LOADMORE) {
            int size = items.size();
            if (size != 0) {
                List<TuJianHuiYan.DataBean.ItemsBean> items1 = data.getData().getItems();
                items.addAll(items1);
                adapter = new TuJianHuYuan_Adapter(items, this);
                lv_recommend_member.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                ToastUtil.showShort(this, "暂无数据");
            }

        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status = REFRESH;
        requestTuJianHuiYun();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status = LOADMORE;
        requestTuJianHuiYun();
    }
}
