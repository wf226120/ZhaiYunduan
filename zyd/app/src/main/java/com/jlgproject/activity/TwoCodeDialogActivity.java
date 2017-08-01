package com.jlgproject.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.util.ScreenUtil;

public class TwoCodeDialogActivity extends BaseActivity {


    private ImageView iv_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 去除Activity的标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_two_code_dialog;
    }

    @Override
    public void initViews() {
        dialogActivityInit();
        iv_close= (ImageView) findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * ActivityDialog 初始化配置
     */
    public void dialogActivityInit() {

        WindowManager windowManager = getWindowManager();
        // 获取对话框当前的参数值
        WindowManager.LayoutParams p = getWindow().getAttributes();
        // 宽度设置为屏幕的1
        p.width = (int) (ScreenUtil.getScreenWidth(TwoCodeDialogActivity.this) * 0.9);
        // 设置透明度,0.0为完全透明，1.0为完全不透明
        p.alpha = 0.95f;
        p.height=(int) (ScreenUtil.getScreenHeight(TwoCodeDialogActivity.this) * 0.9);
        // 设置布局参数
        getWindow().setAttributes(p);
        // 设置点击弹框外部不可消失
        setFinishOnTouchOutside(false);
    }
}
