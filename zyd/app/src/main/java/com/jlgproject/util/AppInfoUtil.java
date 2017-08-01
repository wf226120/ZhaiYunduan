package com.jlgproject.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.jlgproject.App;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王锋 on 2017/7/17.
 */

public class AppInfoUtil {

    /**
     * 获取应用程序版本名称信息
     *
     * @return 当前应用的版本名称
     */
    public final static String getVersionName() {
        String versionName = null;

        try {
            //获取ApplicationContent
            Context context = App.getContext();
            //通过上下文获取包管理器
            PackageManager packageManager = context.getPackageManager();
            //通过包管理器获的包信息（通过context.getPackageName()获的包名，或不存在返回0）
            PackageInfo packageInfo = null;
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            //通过包信息获得版本名称
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "";
            e.printStackTrace();
        }

        return versionName;
    }

    /**
     * 获取应用程序包名
     */
    public final static String getAppPkgName() {
        String pkgName = null;

        try {
            Context context = App.getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            pkgName = packageInfo.applicationInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            pkgName = null;
            e.printStackTrace();
        }

        return pkgName;
    }


    /**
     * 获取应用程序版本code
     *
     * @return 当前应用的版本code
     */
    public final static int getVersionCode() {
        int versionCode = 0;

        try {
            Context context = App.getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);

            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 0;
            e.printStackTrace();
        }

        return versionCode;
    }


    /**
     * 获取渠道名称
     *
     * @return 渠道名称
     */
    public final static String getChannel() {
        ApplicationInfo ai = null;
        String channel = null;

        try {
            Context context = App.getContext();
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = (String) ai.metaData.get("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return TextUtils.isEmpty(channel) ? "" : channel;
    }


    //    检查版本更新，跳转到腾讯应用宝进行下载
    public static void intit_getClick(Context context, String packName) {
        if (isAvilible(context, "com.tencent.android.qqdownloader")) {
            // 市场存在
            launchAppDetail(context, packName, "com.tencent.android.qqdownloader");
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + packName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        }
    }
    //    检查版本更新，跳转到腾讯应用宝进行下载
    public static void intit_getClick(Context context, String packName,String marketPkg) {
        if (isAvilible(context,marketPkg)) {
            // 市场存在
            launchAppDetail(context, packName,marketPkg);
        } else {
            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + packName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        }
    }

//    //    检查版本更新，跳转到腾讯应用宝进行下载
//    public static void kaiF_sc(Context context, String packName,String marketPkg) {
//        if (isAvilible(context,marketPkg)) {
//            // 市场存在
//            launchAppDetail(context, packName,marketPkg);
//        } else {
//            Uri uri = Uri.parse("http://a.app.qq.com/o/simple.jsp?pkgname=" + packName);
//            Intent it = new Intent(Intent.ACTION_VIEW, uri);
//            context.startActivity(it);
//        }
//    }

    /**
     * 启动到app详情界面
     *
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 判断市场是否存在的方法
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
    }

    public static void upData(Context context,String packName) {
        String brand = android.os.Build.BRAND;
        if (brand.equals("HUAWEI")) {//华为
            intit_getClick(context,packName,ConstUtils.HUAWEI_SC);
        } else if(brand.equals("Xiaomi")){
            intit_getClick(context,packName,ConstUtils.XIAOMI_SC);
        } else if (brand.equals("vivo")) {//vivo
            intit_getClick(context,packName,ConstUtils.VIVO_SC);
        } else {//应用宝
            intit_getClick(context,packName,ConstUtils.YYB_SC);
        }
    }



    /**
     * 调用系统安装程序
     *
     * @return apk文件路径
     */
    public final static void installApk(String apkPath) {
        Context context = App.getContext();
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
