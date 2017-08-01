package com.jlgproject.view.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.jlgproject.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Nathen
 * On 2016/04/22 00:54
 */
public class JCVideoPlayerStandardShowShareButtonAfterFullscreen extends JCVideoPlayerStandard {

    public ImageView shareButton;

    public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context) {
        super(context);
    }

    public JCVideoPlayerStandardShowShareButtonAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_standard_with_share_button;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
