package com.jlgproject.base;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.NetUtils;
import com.umeng.analytics.MobclickAgent;

//fragment 基类

/**
 * @author 王锋 on 2017/4/21.
 */
public abstract class BaseFragment extends Fragment implements IBase {

    protected View view;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(this.getLoadViewId(),container, false);
        initDatas();
        initView(view);
        return view;
    }
   //获取fragment布局
    public abstract int getLoadViewId();
    //通过view布局初始化控件
    public abstract View initView(View view);

    //初始化数据
    @Override
    public void initDatas() {}

    //fragment 不中不使用
    @Override
    public void initViews() {}


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }


    /**
     * 显示dialog进度条
     *
     * @param context
     */
    public boolean ShowDrawDialog(Context context) {
        boolean is=false;
        if(NetUtils.isConnected(context)){
            if (dialog == null) {
                // dialog = DialogUtils.ShowProDialog(context);
                dialog = DialogUtils.ShowDefaultProDialog(context);
            } else {
                dialog.show();
            }
            is=true;
        }else{
            NetUtils.openSetting(context);
        }
        return is;
    }


    /**
     * 关闭dialog进度条
     */
    public void DismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
