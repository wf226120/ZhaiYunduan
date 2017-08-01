package com.jlgproject.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author 王锋 on 2017/4/24.
 * 为解决ScrollView与ListView 滑动冲突
 */

public class HomeListView extends ListView {
    public HomeListView(Context context) {
        super(context);
    }

    public HomeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,//右移运算符，相当于除于4
                MeasureSpec.AT_MOST);//测量模式取最大值
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);//重新测量高度
    }

}
