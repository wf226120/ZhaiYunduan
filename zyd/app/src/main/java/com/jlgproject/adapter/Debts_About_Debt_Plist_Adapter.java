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
 * Created by sunbeibei on 2017/6/29.
 * 债市详情显示图片的适配器
 */

public class Debts_About_Debt_Plist_Adapter extends BaseAdapter {
    private Context context;
    private List<String> data;

    public Debts_About_Debt_Plist_Adapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final IcardZm_ViewHolder iz;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.asset_pic_item, null);
            iz = new IcardZm_ViewHolder();
            iz.pic_list = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(iz);
        } else {
            iz = (IcardZm_ViewHolder) convertView.getTag();
        }
        final String s = data.get(position);

        iz.pic_list.setVisibility(View.VISIBLE);
        Glide.with(context).load(s).priority(Priority.HIGH).override(140, 130).centerCrop().into(iz.pic_list);
        return convertView;
    }

    public class IcardZm_ViewHolder {
        private ImageView pic_list;
    }
}
