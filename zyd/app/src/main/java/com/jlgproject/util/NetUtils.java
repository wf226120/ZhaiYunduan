package com.jlgproject.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jlgproject.R;

import java.util.List;

/**
 * 跟网络相关的工具类
 *
 * @author zhy
 */
public class NetUtils {

    // 没有连接

    public static final int NETWORN_NONE = 0;

// wifi连接

    public static final int NETWORN_WIFI = 1;

// 手机网络数据连接

    public static final int NETWORN_2G = 2;

    public static final int NETWORN_3G = 3;

    public static final int NETWORN_4G = 4;

    public static final int NETWORN_MOBILE = 5;


    private NetUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 如果仅仅是用来判断网络连接
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * 判断是否是wifi连接
//     */
//    public static boolean isWifi(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        if (cm == null)
//            return false;
//        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
//
//    }

    /**
     * 判断GPS是否打开
     *
     * @param context
     * @return
     */

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 判断是否是3G网络
     *
     * @param context
     * @return
     */

    public static boolean is3rd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断是wifi还是3g网络,用户的体现性在这里了，wifi就可以建议下载或者在线播放。
     *
     * @param context
     * @return
     */

    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        if (networkINfo != null
                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 打开网络设置界面
     */
    public final static void openSetting(final Context context) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        final android.app.AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.go_net, null);

        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm_net);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    // 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                    context.startActivity(new Intent(
                            android.provider.Settings.ACTION_SETTINGS));
                } else {
                    context.startActivity(new Intent(
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel_net);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }

    /**
     * 返回当前网络连接类型
     *
     * @param context 上下文
     * @return
     */
    public static int getNetworkState(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == connManager)

            return NETWORN_NONE;

        NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();

        if (activeNetInfo == null || !activeNetInfo.isAvailable()) {

            return NETWORN_NONE;

        }
        // Wifi
        NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (null != wifiInfo) {

            NetworkInfo.State state = wifiInfo.getState();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    return NETWORN_WIFI;

                }

        }
        // 网络
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (null != networkInfo) {

            NetworkInfo.State state = networkInfo.getState();

            String strSubTypeName = networkInfo.getSubtypeName();

            if (null != state)

                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {

                    switch (activeNetInfo.getSubtype()) {

                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g

                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g

                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g

                        case TelephonyManager.NETWORK_TYPE_1xRTT:

                        case TelephonyManager.NETWORK_TYPE_IDEN:

                            return NETWORN_2G;

                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g

                        case TelephonyManager.NETWORK_TYPE_UMTS:

                        case TelephonyManager.NETWORK_TYPE_EVDO_0:

                        case TelephonyManager.NETWORK_TYPE_HSDPA:

                        case TelephonyManager.NETWORK_TYPE_HSUPA:

                        case TelephonyManager.NETWORK_TYPE_HSPA:

                        case TelephonyManager.NETWORK_TYPE_EVDO_B:

                        case TelephonyManager.NETWORK_TYPE_EHRPD:

                        case TelephonyManager.NETWORK_TYPE_HSPAP:

                            return NETWORN_3G;

                        case TelephonyManager.NETWORK_TYPE_LTE:

                            return NETWORN_4G;

                        default://有机型返回16,17

                            //中国移动 联通 电信 三种3G制式

                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {

                                return NETWORN_3G;

                            } else {

                                return NETWORN_MOBILE;

                            }

                    }

                }
        }

        return NETWORN_NONE;

    }

}
