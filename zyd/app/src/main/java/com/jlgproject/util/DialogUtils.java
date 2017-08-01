package com.jlgproject.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;


/**
 * @author 王锋 on 2017/4/17.
 */

public class DialogUtils {





    /**
     * 显示默认的请求进度条
     *
     * @param context
     */
    public static Dialog ShowDefaultProDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_pro_dialog_layout, null);
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth /3), screenWidth/3);
        dialog.getWindow().setContentView(layout);
        return dialog;
    }


    /*
	 *
	 * 弹出对话框通知用户更新程序
	 *
	 * 弹出对话框的步骤：
	 *  1.创建alertDialog的builder.
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮
	 *  3.通过builder 创建一个对话框
	 *  4.对话框show()出来
	 */
    public final static void showDialogVersion(Context context, boolean canceled, String message,final OnButtonEventListener1 buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.updata_vresion, null);
        TextView textView= (TextView) layout.findViewById(R.id.tv_content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel(dialog);
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }

    /**
     * 给用户提示警告的dialog
     *
     * @param context
     * @param msg
     * @param buttonEventListener
     */
    public final static void showWarningDialog(Context context, String msg, final OnButtonEventListenerConfirm buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_warning_dialog_layout, null);
        TextView msgText = (TextView) layout.findViewById(R.id.msg);
        msgText.setText(msg);
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 30), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }


    /**
     * 显示确认对话框(只有一个button, button显示的文字可以自由设置)
     *
     * @param context 上下文索引
     * @param msg     dialog显示的消息内容
     * @param btnText button显示的文字 buttonEventListener 用户点击button时的监听器, 此参数可以传null
     */
    public final static void showConfirmDialog(Context context, String msg, String btnText, boolean isCancel, final OnButtonEventListenerConfirm buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(isCancel);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_confirm_dialog_layout, null);
        TextView msgText = (TextView) layout.findViewById(R.id.msg);
        msgText.setText(msg);
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setText(btnText);
        btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });
        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }

    /**
     * 退出Dialog
     *
     * @param context  上下文索引
     * @param canceled 按返回键是否关闭对话框 true 关闭 false 不关闭
     */
    public final static void showDialogEsc(Context context, boolean canceled, final OnButtonEventListener buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.esc, null);

        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel();
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }



    /**
     * 显示对话框(有两个button, button显示的文字可以自由设置) button按钮位白色，文字为黑色
     *
     * @param context  上下文索引
     * @param canceled 按返回键是否关闭对话框 true 关闭 false 不关闭
     */
    public final static void showDialogZsr(Context context, boolean canceled, String message,final OnButtonEventListener buttonEventListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(canceled);
        dialog.show();
        LinearLayout layout = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loan_dialogs_layout, null);
        TextView textView= (TextView) layout.findViewById(R.id.content);
        textView.setText(message);
        //  点击现在就去
        Button btCommit = (Button) layout.findViewById(R.id.btnConfirm);
        btCommit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onConfirm();
                }
            }
        });

        Button btCancel = (Button) layout.findViewById(R.id.btnCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (buttonEventListener != null) {
                    buttonEventListener.onCancel();
                }
            }
        });

        int screenWidth = ScreenUtil.getScreenWidth(context);
        dialog.getWindow().setLayout((int) (screenWidth - 60), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setContentView(layout);
    }
    private static AlertDialog builder;
    public static AlertDialog getBuilder(View view, Context context,int width) {
        builder = new AlertDialog.Builder(context).create();
        builder.setView(view);
        builder.show();
        //得到当前显示设备的宽度，单位是像素
        /*int width =context.getWindowManager().getDefaultDisplay().getWidth();*/
        //得到这个dialog界面的参数对象
        WindowManager.LayoutParams params = builder.getWindow().getAttributes();
        //设置dialog的界面宽度
        params.width = width-(width/6);
        //设置dialog高度为包裹内容
        params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的重心
        params.gravity = Gravity.CENTER;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        builder.getWindow().setAttributes(params);
        return  builder;
    }


    public interface OnButtonEventListener {

        void onConfirm();

        void onCancel();
    }

    public interface OnButtonEventListener1 {

        void onConfirm();

        void onCancel(AlertDialog dialog);
    }

    public interface OnButtonEventListenerConfirm {

        void onConfirm();
    }
}
