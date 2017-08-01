package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jlgproject.R;

import java.util.List;

/**
 * Created by sunbeibei on 2017/6/21.
 */

public class Asset_Pic_Adapter extends BaseAdapter {
    private Context context ;
    private List<String> picList;

    public Asset_Pic_Adapter(Context context, List<String> picList) {
        this.context = context;
        this.picList = picList;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.asset_pic_item,null);
            vh=new ViewHolder();
            vh.imageView= (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        String s = picList.get(position);
        Glide.with(context).load(s).priority(Priority.HIGH).override(140,130).centerCrop().into(vh.imageView);


        return convertView;
    }
    public class  ViewHolder{
        ImageView imageView;
    }
}
