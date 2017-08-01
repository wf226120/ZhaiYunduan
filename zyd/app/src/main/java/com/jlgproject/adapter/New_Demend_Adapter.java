package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlgproject.R;

import java.util.ArrayList;

/**
 * Created by sunbeibei on 2017/5/8.
 * 新增需求信息适配器
 */

public class New_Demend_Adapter extends BaseAdapter {
    private int state=0;
    private Context context;
    private ArrayList<String>list;


    public New_Demend_Adapter(Context context, ArrayList<String>list) {
        this.context=context;
        this.list=list;
    }

    @Override

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.new_demend_list_item,null);
            vh=new ViewHolder();
            vh.asset_name= (TextView) convertView.findViewById(R.id.asset_name);
            vh.accout= (TextView) convertView.findViewById(R.id.acount);
            vh.total_volve= (TextView) convertView.findViewById(R.id.total_volva);
            vh.currut_volue= (TextView) convertView.findViewById(R.id.current_value);
            vh.image_button= (ImageView) convertView.findViewById(R.id.imagebutton);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.asset_name.setText("别墅");
        vh.accout.setText("7");
        vh.total_volve.setText("800万");
        vh.currut_volue.setText(list.get(position));
        vh.image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state==0){
                    vh.image_button.setImageResource(R.mipmap.gray);
                    state=1;
                }else if(state==1){
                    vh.image_button.setImageResource(R.mipmap.redbutton);
                    state=0;
                }
            }
        });
        return convertView;
    }
    public class ViewHolder{
        private TextView asset_name,accout,total_volve,currut_volue;
        private ImageView image_button;
    }
}
