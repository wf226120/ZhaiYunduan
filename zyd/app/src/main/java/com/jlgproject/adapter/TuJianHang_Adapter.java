package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jlgproject.R;
import com.jlgproject.model.TuiJianHangZhang;


import java.util.List;



/**
 * Created by sunbeibei on 2017/7/11.
 */

public class TuJianHang_Adapter extends BaseAdapter {
    private List<TuiJianHangZhang.DataBean.ItemsBean> items;

    public TuJianHang_Adapter(List<TuiJianHangZhang.DataBean.ItemsBean> items, Context context) {
        this.items = items;
        this.context = context;
    }

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
        ViewHolder vh;
        if (convertView==null){
           convertView= LayoutInflater.from(context).inflate(R.layout.tujhz_list_item,null);
            vh= new ViewHolder();
            vh.name= (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.phone = (TextView) convertView.findViewById(R.id.tv_h_phone);
            vh.adress= (TextView) convertView.findViewById(R.id.tv_h_adress);
            vh.type= (TextView) convertView.findViewById(R.id.tv_h_type);
            vh.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        TuiJianHangZhang.DataBean.ItemsBean itemsBean = items.get(position);
        vh.name.setText(itemsBean.getName());
        vh.phone.setText(itemsBean.getPhonenumber());
        vh.adress.setText(itemsBean.getAddress());
        vh.tv_number.setText((position+1)+"");
        int type = itemsBean.getType();
        if (type==1){
            vh.type.setText("总公司");
        }else if (type==2){
            vh.type.setText("省公司代表");
        }else if (type==3){
            vh.type.setText("市公司代表");
        }else if (type==4){
            vh.type.setText("服务行代表");
        }else if(type==5){
            vh.type.setText("拓展行");
        }else if (type==6){
            vh.type.setText("云债行");
        }


        return convertView;
    }
    public class  ViewHolder{
        private TextView name,phone,adress,type,tv_number;
    }
}
