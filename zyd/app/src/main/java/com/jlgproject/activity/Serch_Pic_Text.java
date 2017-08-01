package com.jlgproject.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jlgproject.R;
import com.jlgproject.adapter.Pic_Text_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Pic_Text_Bean;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/7/21.
 */

public class Serch_Pic_Text extends BaseActivity implements HttpMessageInfo.IMessage, PullToRefreshBase.OnRefreshListener2, View.OnClickListener {

    private PullToRefreshListView serlist;
    private ImageView finish_serch;

    private int pn = 1;
    private int ps = 20;
    private  static  final int NOMAL=0;
    //2.是刷新的状态
    private static final int REFRESH = 2;
    //3.上啦刷新加载更多
    private static final int LOADMORE = 3;
    private int status = 1;
    private Pic_Text_Bean pic_text_bean;
    private List<Pic_Text_Bean.DataBean.ItemsBean> items;
    private ImageView serchpic;
    private EditText serch_pivi;
    private Pic_Text_Adapter adapter;
    private TextView jieguo;


    @Override
    public int loadWindowLayout() {
        return R.layout.serch_view;
    }

    @Override
    public void initDatas() {
        super.initDatas();

    }

    @Override
    public void initViews() {
        super.initViews();

        jieguo = (TextView) findViewById(R.id.jieguo);
        serch_pivi = (EditText) findViewById(R.id.serch_pivi);
        finish_serch = (ImageView) findViewById(R.id.finsh_back);
        finish_serch.setOnClickListener(this);
        serlist = (PullToRefreshListView)findViewById(R.id.listview);
        serchpic = (ImageView) findViewById(R.id.serch_pic);
        serchpic.setOnClickListener(this);
        serlist.setOnRefreshListener(this);
        serlist.setMode(PullToRefreshBase.Mode.BOTH);

    }

    private void requestSerchPit() {
        String wd = serch_pivi.getText().toString();
        if (TextUtils.isEmpty(wd)) {
            ToastUtil.showShort(this, "请输入所要搜索的关键字");
            return;
        }
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Pic_Text_Bean> info = new HttpMessageInfo<>(BaseUrl.PIC_TEXT_SERCH, new Pic_Text_Bean());
            info.setiMessage(this);
            GetParmars parmars = new GetParmars();
            parmars.add("pn", pn);
            parmars.add("ps", ps);
            parmars.add("wd", wd);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        } else {
            ToastUtil.showShort(this, "请登录");
        }

    }

    //抽取的展示数据的方法
    private void displayData(final Pic_Text_Bean manger) {

        if (status==NOMAL) {
            items = manger.getData().getItems();
            if (items.size()==0) {
                jieguo.setVisibility(View.VISIBLE);
                serlist.setVisibility(View.GONE);
            }else{
                jieguo.setVisibility(View.GONE);
                serlist.setVisibility(View.VISIBLE);
                adapter = new Pic_Text_Adapter(this,items);
                //  adapter.setItems(items);
                serlist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }

        if (status == REFRESH) {
            L.e("--------刷新——————");
            items.clear();
            items = manger.getData().getItems();
            adapter.setItems(items);
            serlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if (status == LOADMORE) {
            L.e("--------加载——————");
            List<Pic_Text_Bean.DataBean.ItemsBean> items2 = manger.getData().getItems();
            items.addAll(items2);
            adapter.setItems(items);
            serlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        pn = 1;
        ps = 8;
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Pic_Text_Bean && o != null) {
            pic_text_bean = (Pic_Text_Bean) o;
            if (pic_text_bean!=null) {
                serlist.onRefreshComplete();
                DismissDialog();
                displayData(pic_text_bean);

            } else {
                serlist.onRefreshComplete();
                DismissDialog();
                ToastUtil.showShort(this, pic_text_bean.getMessage());
            }
        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {
        serlist.onRefreshComplete();
        DismissDialog();
        ToastUtil.showLong(this, "服务器繁忙，请稍后再试");
    }



    @Override
    public void onClick(View v) {
        if (v == finish_serch) {
            finish();
        } else if (v == serchpic) {
            status =NOMAL;
            requestSerchPit();

        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        status = REFRESH;
        pn = 1;
        requestSerchPit();

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        status = LOADMORE;
        pn = pn + 1;
        requestSerchPit();

    }
}
