package com.jlgproject.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.jlgproject.activity.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王锋 on 2017/4/21.
 */

public class ActivityCollector {

    //定义一个用于存放Activity的集合
    public final static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 页面跳转方法
     * @param firstActivity
     * @param nextActivity
     */
    public static void startA(Activity firstActivity,Class<? extends Activity> nextActivity){
        Intent intent = new Intent();
        intent.setClass(firstActivity, nextActivity);
        firstActivity.startActivity(intent);
    }

    /**
     * 页面跳转方法
     * @param firstActivity
     * @param nextActivity
     */
    public static void startA(Activity firstActivity,Class<? extends Activity> nextActivity,String string,int currMenu){
        Intent intent = new Intent();
        intent.setClass(firstActivity, nextActivity);
        intent.putExtra(string,currMenu);
        firstActivity.startActivity(intent);
    }



    public static void pleaseLogin(final FragmentActivity activity, final int i){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityCollector.startA(activity,Login.class,ConstUtils.PD,i);
            }
        },500);
    }


}
