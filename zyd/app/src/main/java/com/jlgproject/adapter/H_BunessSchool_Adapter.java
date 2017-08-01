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
import com.bumptech.glide.Priority;
import com.jlgproject.R;
import com.jlgproject.activity.BusinessVideoPlay;
import com.jlgproject.model.Video_List_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbeibei on 2017/7/18.
 */

public class H_BunessSchool_Adapter extends BaseAdapter {
    private Context context;
    private  List<Video_List_Bean.DataBean.ItemsBean> items;
    private String img;
    private String url;

    public H_BunessSchool_Adapter(Context context,  List<Video_List_Bean.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.buness_video_item, null);
            vh = new VideoHolder();
            vh.video_pic = (ImageView) convertView.findViewById(R.id.video_pic);//图片
            vh.video_title = (TextView) convertView.findViewById(R.id.video_title);//标题
            vh.content = (TextView) convertView.findViewById(R.id.video_jianjie);//简介
            vh.tv_b_v_i_time = (TextView) convertView.findViewById(R.id.tv_b_v_i_time);//时间
            vh.liner_play= (LinearLayout) convertView.findViewById(R.id.liner_play);
            convertView.setTag(vh);
        } else {
            vh = (VideoHolder) convertView.getTag();
        }

        final Video_List_Bean.DataBean.ItemsBean itemsBean = items.get(position);
        vh.video_title.setText(itemsBean.getSubTitle());
        vh.content.setText(itemsBean.getTitle());
        vh.tv_b_v_i_time.setText(itemsBean.getUpdateTime());
        img = itemsBean.getImg();
        url = itemsBean.getUrl();
        Glide.with(context).load(img).priority(Priority.HIGH).into(vh.video_pic);
        vh.liner_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,BusinessVideoPlay.class).putExtra("video",url).putExtra("title",itemsBean.getTitle()).putExtra("sutitle",itemsBean.getSubTitle()).putExtra("time",itemsBean.getUpdateTime()));
            }
        });

        return convertView;
    }

    public class VideoHolder {
        private ImageView video_pic;
        private LinearLayout liner_play;
        private TextView video_title, content, tv_b_v_i_time;
    }
}
