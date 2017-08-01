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
import com.jlgproject.activity.Pic_Text_Details;
import com.jlgproject.model.Pic_Text_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbeibei on 2017/7/18.
 */

public class Pic_Text_Adapter extends BaseAdapter {
    private Context context;
    private List<Pic_Text_Bean.DataBean.ItemsBean> items;
    private String img;

    public Pic_Text_Adapter(Context context,List<Pic_Text_Bean.DataBean.ItemsBean> items) {
        this.context = context;
        this.items=items;
        notifyDataSetChanged();



    }
    public  void  setItems(List<Pic_Text_Bean.DataBean.ItemsBean> items){
        this.items=items;
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
        ViewHodle viewHodle;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.home_list_item, null);
            viewHodle = new ViewHodle();
            viewHodle.image = (ImageView) convertView.findViewById(R.id.iv_item);
            viewHodle.tv_title = (TextView) convertView.findViewById(R.id.tv_item_title);
            viewHodle.tv_date = (TextView) convertView.findViewById(R.id.tv_item_date);
            viewHodle.pic_text_line= (LinearLayout) convertView.findViewById(R.id.pic_text_line);
            convertView.setTag(viewHodle);
        } else {
            viewHodle = (ViewHodle) convertView.getTag();
        }
        Pic_Text_Bean.DataBean.ItemsBean itemsBean = items.get(position);
        img = itemsBean.getImg();
        final String url = itemsBean.getUrl();
        viewHodle.tv_title.setText(itemsBean.getTitle());
        viewHodle.tv_date.setText(itemsBean.getUpdateTime());
        Glide.with(context).load(img).priority(Priority.HIGH).into(viewHodle.image);
        viewHodle.pic_text_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Pic_Text_Details.class).putExtra("url",url));
            }
        });

        return convertView;
    }
    public class ViewHodle {
        private ImageView image;
        private TextView tv_title;
        private TextView tv_date;
        private LinearLayout pic_text_line;
    }
}
