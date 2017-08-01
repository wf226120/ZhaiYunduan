package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.Details_About_Debt;
import com.jlgproject.model.Debts_Informations;
import com.jlgproject.util.L;

import java.util.List;

/**
 * Created by sunbeibei on 2017/5/8.
 */

public class Debt_Information_Adapter extends BaseAdapter {
    private Context context;
    private List<Debts_Informations.DataBean.ItemsBean> items;

    public Debt_Information_Adapter(Context context, List<Debts_Informations.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(List<Debts_Informations.DataBean.ItemsBean> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.debts_information_list_item, null);
            vh = new ViewHolder();

            vh.entrytime = (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.cretidors = (TextView) convertView.findViewById(R.id.tv_h_zhiquan);
            vh.debtor = (TextView) convertView.findViewById(R.id.tv_h_zhaiwu);
            vh.mainmoney = (TextView) convertView.findViewById(R.id.tv_h_principal_amount);
            vh.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            vh.tv_not_solved = (TextView) convertView.findViewById(R.id.tv_not_solved);
            vh.chakan = (TextView) convertView.findViewById(R.id.tv_check_dianji);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        final Debts_Informations.DataBean.ItemsBean itemsBean = items.get(position);
        String createTime = itemsBean.getCreateTime();//发生时间
        String from = itemsBean.getFrom();//债务人
        String to = itemsBean.getTo();//债权人
        Long amout = itemsBean.getAmout();//主体金额
        Long  id = itemsBean.getId();//债事id
        L.e("------债事id------"+id);
        String orderId = (String) itemsBean.getOrderId();//订单ID
        int  payStatus = itemsBean.getPayStatus();
        Object qianshu = itemsBean.getQianshu();

        //支付状态
        int isSolution = itemsBean.getIsSolution();//债事状态0未解决1已解决

        if (isSolution == 1) {
            vh.tv_not_solved.setText("已解决");
            vh.tv_not_solved.setTextColor(context.getResources().getColor(R.color.gray));
        }


        vh.entrytime.setText(createTime);
        vh.cretidors.setText(from);
        vh.debtor.setText(to);
        vh.mainmoney.setText(amout + "");
        vh.tv_number.setText(position + 1 + "");
        //查看详情
        vh.chakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, Details_About_Debt.class).putExtra("id", items.get(position).getId()).putExtra("orderId", itemsBean.getOrderId()));
            }
        });

        return convertView;
    }

    public class ViewHolder {
        //依次录入时间，债权人，债务人，主体金额,付费备案；

        private TextView entrytime, cretidors, debtor, mainmoney, tv_number, tv_not_solved, chakan;

    }
}
