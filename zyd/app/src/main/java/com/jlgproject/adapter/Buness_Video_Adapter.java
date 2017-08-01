package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

public class Buness_Video_Adapter extends BaseAdapter {
    private Context context;
    private List<Video_List_Bean.DataBean.ItemsBean> items;
    private String url;

    public Buness_Video_Adapter(Context context, List<Video_List_Bean.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
        notifyDataSetChanged();
    }

    public void setItems(List<Video_List_Bean.DataBean.ItemsBean> items) {
        this.items = items;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.vg_voideo_item, null);
            vh = new VideoHolder();
            vh.iv_video_pic = (ImageView) convertView.findViewById(R.id.iv_video_pic);
            vh.tv_video_title = (TextView) convertView.findViewById(R.id.tv_video_title);
            vh.tv_video_jianjie = (TextView) convertView.findViewById(R.id.tv_video_jianjie);
            convertView.setTag(vh);
        } else {
            vh = (VideoHolder) convertView.getTag();
        }
        final Video_List_Bean.DataBean.ItemsBean itemsBean = items.get(position);

        String updateTime = itemsBean.getUpdateTime();
        url = itemsBean.getUrl();
        Glide.with(context).load(itemsBean.getImg()).priority(Priority.HIGH).centerCrop().fitCenter().into(vh.iv_video_pic);
        vh.tv_video_title.setText(itemsBean.getTitle());
        vh.tv_video_jianjie.setText(itemsBean.getSubTitle());
        vh.iv_video_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BusinessVideoPlay.class).putExtra("video", url).putExtra("title",itemsBean.getTitle()).putExtra("sutitle",itemsBean.getSubTitle()).putExtra("time",itemsBean.getUpdateTime()));
            }
        });

        return convertView;
    }


}

class VideoHolder {
    public ImageView iv_video_pic;
    public TextView tv_video_title, tv_video_jianjie;
}