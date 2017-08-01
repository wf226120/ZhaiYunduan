package com.jlgproject.activity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.TuJianHang_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.TuiJianHangZhang;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * 推荐行长
 */
public class RecommendPresident extends BaseActivity implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2 {

    private TextView mTv_Title_president;
    private ImageView mIv_Title_left_president;
    private LinearLayout mLl_ivParent_president;
    private PullToRefreshListView listView;
    private int pn = 1;
    private int ps = 8;
    private List<TuiJianHangZhang.DataBean.ItemsBean> items;
    private TuJianHang_Adapter adapter;
    // 1.设置几个状态码方便我们进行状态的判断
    private static final int NORMAL = 1;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Handler handler = new Handler();

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_recommend_president;
    }

    @Override
    public void initViews() {
        mTv_Title_president = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_president.setText(getResources().getText(R.string.recommend_president));
        mIv_Title_left_president = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left_president.setVisibility(View.VISIBLE);
        mLl_ivParent_president = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_president.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (PullToRefreshListView) findViewById(R.id.listview);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        requestTujhangzhang();
    }

    private void requestTujhangzhang() {
        Login_zud zud = (Login_zud) SharedUtil.getSharedUtil().getObject(this, ConstUtils.IOGIN_INFO, null);
        if (UserInfoState.isLogin()) {
            if (ShowDrawDialog(this)) {
                AddHeaders headers = new AddHeaders();
                headers.add("Authorization", zud.getData().getToken());
                GetParmars parmars = new GetParmars();
                parmars.add("pn", pn);
                parmars.add("ps", ps);
                HttpMessageInfo<TuiJianHangZhang> info = new HttpMessageInfo<>(BaseUrl.TJHZ, new TuiJianHangZhang());
                info.setiMessage(this);
                OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
            }
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof TuiJianHangZhang && o != null) {
            TuiJianHangZhang data = (TuiJianHangZhang) o;
            if (data != null) {
                DismissDialog();
                listView.onRefreshComplete();
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
        listView.onRefreshComplete();
        ToastUtil.showShort(this, "服务器繁忙，请稍后再试");
    }


    //抽取显示数据的方法；
    private void displayData(TuiJianHangZhang data) {
        if (items == null) {
            items = data.getData().getItems();
            adapter = new TuJianHang_Adapter(items, this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else if (status == REFRESH) {
            items.clear();
            items = data.getData().getItems();
            if (items != null) {
                adapter = new TuJianHang_Adapter(items, this);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                L.e("-----REFRESH-----暂无数据----");
                ToastUtil.showShort(this, "暂无数据");
            }
        } else if (status == LOADMORE) {
            int size = items.size();
            if (size != 0) {
                List<TuiJianHangZhang.DataBean.ItemsBean> items1 = data.getData().getItems();
                this.items.addAll(items1);
                adapter = new TuJianHang_Adapter(items, this);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                L.e("-----LOADMORE-----暂无数据----");
                ToastUtil.showShort(this, "暂无数据");
            }

        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        pn = 1;
        status = REFRESH;
        requestTujhangzhang();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pn = pn + 1;
        status = LOADMORE;
        requestTujhangzhang();
    }
}
