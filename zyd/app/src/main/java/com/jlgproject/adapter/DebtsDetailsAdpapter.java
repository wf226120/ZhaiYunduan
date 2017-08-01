package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jlgproject.R;

import java.util.List;

/**
 * Created by sunbeibei on 2017/6/22.
 */

public class DebtsDetailsAdpapter extends BaseAdapter {
    public DebtsDetailsAdpapter(int state, List<String> data, Context context) {
        this.state = state;
        this.data = data;
        this.context=context;
    }
    private Context context;
    private  int state;
    private List<String> data;
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
        IcardZm_ViewHolder iz;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.tv1_item,null);
            iz=new IcardZm_ViewHolder();
            iz.textView= (TextView) convertView.findViewById(R.id.tv1);
            iz.pic_list= (ImageView) convertView.findViewById(R.id.pic_list);
            convertView.setTag(iz);
        }else{
            iz= (IcardZm_ViewHolder) convertView.getTag();
        }
        String s = data.get(position);
        if (state==1){
            if ("1".equals(s)){
                iz.textView.setText("营业执照副本复印件");
            }else if("2".equals(s)){
                iz.textView.setText("组织机构代码证复印件");
            }else{
                iz.textView.setText("法人身份证复印件");
            }
        }else if (state==2){
            //票据证明 1合同，2收据 3借据4欠据 5协议6信件7电报8提货单9仓单发票10其他
            if ("1".equals(s)){
                iz.textView.setText("合同");
            }else if("2".equals(s)){
                iz.textView.setText("收据");
            }else if("3".equals(s)){
                iz.textView.setText("借据");
            }else if("4".equals(s)){
                iz.textView.setText("欠据");
            }else if("5".equals(s)){
                iz.textView.setText("信件");
            }else if("6".equals(s)){
                iz.textView.setText("收据");
            }else if("7".equals(s)){
                iz.textView.setText("电报");
            }else if("8".equals(s)){
                iz.textView.setText("提货单");
            }else if("9".equals(s)){
                iz.textView.setText("仓单发票");
            }else if("10".equals(s)){
                iz.textView.setText("其他");
            }
        }else if(state==3) {
            //电子数据 1 微信 2 QQ3 MSN 4微博5其他
            if ("1".equals(s)){
                iz.textView.setText("微信");
            }else if("2".equals(s)){
                iz.textView.setText("QQ");
            }else if("3".equals(s)){
                iz.textView.setText("MSN");
            }else if("4".equals(s)){
                iz.textView.setText("微博");
            }else if("5".equals(s)){
                iz.textView.setText("其他");
            }
        }


        return convertView;
    }
    public  class IcardZm_ViewHolder{
            private  TextView textView;
            private ImageView pic_list;
    }
}
