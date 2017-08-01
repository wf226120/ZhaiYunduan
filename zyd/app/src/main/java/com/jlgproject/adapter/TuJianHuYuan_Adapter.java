package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.model.TuJianHuiYan;

import java.util.List;

/**
 * Created by sunbeibei on 2017/7/11.
 */

public class TuJianHuYuan_Adapter extends BaseAdapter {
    public TuJianHuYuan_Adapter(List<TuJianHuiYan.DataBean.ItemsBean> items, Context context) {
        this.items = items;
        this.context = context;
    }

    private List<TuJianHuiYan.DataBean.ItemsBean> items;
    private Context context;
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
        VipHolder vp;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.tjhuyuan_list_item,null);
            vp=new VipHolder();
            vp.name= (TextView) convertView.findViewById(R.id.vip_name);
            vp.phone= (TextView) convertView.findViewById(R.id.vip_phone);
            vp.number = (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(vp);
        }else{
            vp= (VipHolder) convertView.getTag();
        }
        TuJianHuiYan.DataBean.ItemsBean itemsBean = items.get(position);
        vp.name.setText(itemsBean.getName());
        vp.phone.setText(itemsBean.getPhonenumber());
        vp.number.setText((position+1)+"");
        return convertView;
    }
    public  class  VipHolder{
        TextView name,phone,number;
    }
}
