package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Details;
import com.jlgproject.activity.Debt_Matter_Management_Preson_Details;
import com.jlgproject.model.Debt_Preson_Manger;

import java.util.List;

/**
 * Created by sunbeibei on 2017/5/2.
 */

public class DebtManerPresonAdapter extends BaseAdapter {

    private Context context;
    private Intent intent;
    private Bundle bundle;
    private Debt_Preson_Manger.DataBean.ItemsBean itemsBean=null;

    private List<Debt_Preson_Manger.DataBean.ItemsBean> items;

    public DebtManerPresonAdapter(Context context, List<Debt_Preson_Manger.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
        intent=new Intent();
        bundle=new Bundle();
    }

    public void setItems(List<Debt_Preson_Manger.DataBean.ItemsBean> items) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.debt_matter_person_list_item, null);
            vh = new ViewHolder();
            vh.debt_person_name = (TextView) convertView.findViewById(R.id.debt_person_name);
            vh.debt_person_number = (TextView) convertView.findViewById(R.id.debt_person_number);
            vh.debt_person_type = (TextView) convertView.findViewById(R.id.debt_person_type);
            vh.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            vh.ll_zsrgl_lb= (LinearLayout) convertView.findViewById(R.id.ll_zsrgl_lb);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final String type = items.get(position).getType();

        vh.debt_person_number.setText(items.get(position).getIdCode());
        if ("human".equals(type)) {
            vh.debt_person_name.setText(items.get(position).getName());
            vh.debt_person_type.setText("自然人");
        } else if ("company".equals(type)) {
            String companyName;
            vh.debt_person_type.setText("企业");

            if(!TextUtils.isEmpty(items.get(position).getCompanyName())){// 企业 2.0
                companyName=items.get(position).getCompanyName();//2.0数据
            }else{   //1.0 数据
                companyName=items.get(position).getName();//兼容1.0数据
            }
            vh.debt_person_name.setText(companyName);
        }
        vh.tv_number.setText(position + 1 + "");
        vh.ll_zsrgl_lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemsBean= items.get(position);
                String typeName=itemsBean.getType().toString();

                if (typeName.equals("human")) {//个人
                    intent.setClass(context,Debt_Matter_Management_Preson_Details.class);
                    bundle.putSerializable("grInfo",itemsBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                } else if(typeName.equals("company")){ //企业
                    intent.setClass(context,Debt_Matter_Management_Details.class);
                    bundle.putSerializable("qyInfo",itemsBean);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
                itemsBean=null;
            }
        });
        return convertView;
    }

    public class ViewHolder {
        private TextView debt_person_name, debt_person_number, debt_person_type, tv_number;
        private LinearLayout ll_zsrgl_lb;

    }
}
