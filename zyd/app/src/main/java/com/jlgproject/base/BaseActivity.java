package com.jlgproject.base;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.NetUtils;
import com.umeng.analytics.MobclickAgent;
import com.jlgproject.util.ActivityCollector;

/**
 * @author 王锋 on 2017/4/21.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBase {


    private View rootView;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载一个布局
        this.rootView = View.inflate(this, this.loadWindowLayout(), null);
        setContentView(this.rootView);
        ActivityCollector.addActivity(this);
        this.initDatas();
        this.initViews();

    }


    //    //该抽象方法用于为Activit设置xml
    public abstract int loadWindowLayout();

    @Override
    public void initViews() {}

    @Override
    public void initDatas() {}

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ActivityCollector.removeActivity(this);
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
