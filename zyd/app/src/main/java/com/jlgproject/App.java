package com.jlgproject;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.jlgproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 王锋 on 2017/4/24.
 */

public class App extends Application {

    private static Context mContext;
    public static List<?> images = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        //友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        //使用集成测试环境
//        MobclickAgent.setDebugMode(true);
        MobclickAgent. startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this,"5962ffb65312dd802a000796", "TengXu_YYB", MobclickAgent.EScenarioType.E_UM_NORMAL));
    }

    public static Context getContext() {
        return mContext;
    }
}
