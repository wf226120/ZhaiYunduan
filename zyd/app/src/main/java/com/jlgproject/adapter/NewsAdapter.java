package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlgproject.R;
import com.jlgproject.activity.NewsDetails;
import com.jlgproject.activity.NewsPage;
import com.jlgproject.image.GlideRoundTransform;
import com.jlgproject.model.News;

import java.util.List;

/**
 * @author 王锋 on 2017/4/24.
 */

public class NewsAdapter extends BaseAdapter {

    private List<News.DataBean.NewsBean> mHomeBean;
    private Context context;

    public void setmHomeList(List<News.DataBean.NewsBean> mHomeList) {
        this.mHomeBean = mHomeList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
            return mHomeBean.size();
    }

    @Override
    public Object getItem(int position) {
        return mHomeBean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodle viewHodle;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
            viewHodle = new ViewHodle();
            viewHodle.image = (ImageView) convertView.findViewById(R.id.iv_item);
            viewHodle.tv_title = (TextView) convertView.findViewById(R.id.tv_item_title);
            viewHodle.tv_date = (TextView) convertView.findViewById(R.id.tv_item_date);
            viewHodle.news_line= (LinearLayout) convertView.findViewById(R.id.pic_text_line);
            convertView.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) convertView.getTag();
        }

        final News.DataBean.NewsBean data = mHomeBean.get(position);
        viewHodle.news_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("url", data.getUrl());
                intent.setClass(context, NewsDetails.class);
               context.startActivity(intent);
            }
        });
        Glide.with(context).load(data.getImg())
                .placeholder(R.mipmap.logo)//加载时图片
                .transform(new GlideRoundTransform(context))//圆角
                .error(R.mipmap.logo)//错误
                .into(viewHodle.image);

        viewHodle.tv_title.setText(data.getTitle());
        viewHodle.tv_date.setText(data.getUpdateTime());

        return convertView;
    }

    public class ViewHodle {
        private ImageView image;
        private TextView tv_title;
        private TextView tv_date;
        private LinearLayout news_line;
    }
}
