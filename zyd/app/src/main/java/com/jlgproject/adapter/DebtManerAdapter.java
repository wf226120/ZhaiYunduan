package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.Details_About_Debt;
import com.jlgproject.activity.ToPay;
import com.jlgproject.model.Debts_Manger;
import com.jlgproject.model.eventbusMode.AddDebtBean;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * Created by sunbeibei on 2017/5/2.
 * 债事管理列表所用的适配器
 */

public class DebtManerAdapter extends BaseAdapter {
    private Context context;

    private List<Debts_Manger.DataBean.ItemsBean> items;
    private Long id;

    public DebtManerAdapter(Context context, List<Debts_Manger.DataBean.ItemsBean> items) {
        this.context = context;
        this.items = items;
    }

    public void setItems(List<Debts_Manger.DataBean.ItemsBean> items) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.debt_manger_listview_item, null);
            vh = new ViewHolder();

            vh.entrytime = (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.cretidors = (TextView) convertView.findViewById(R.id.tv_h_zhiquan);
            vh.debtor = (TextView) convertView.findViewById(R.id.tv_h_zhaiwu);
            vh.mainmoney = (TextView) convertView.findViewById(R.id.tv_h_principal_amount);
            vh.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            vh.tv_pay_redor = (TextView) convertView.findViewById(R.id.tv_pay_the_record);
            vh.tv_check_the_details = (TextView) convertView.findViewById(R.id.tv_check_the_details);
            vh.tv_recommend= (TextView) convertView.findViewById(R.id.tv_recommend);//推荐编码

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();

        }
        final Debts_Manger.DataBean.ItemsBean itemsBean = items.get(position);
        id = itemsBean.getId();
        String createTime = itemsBean.getCreateTime();//录入时间
        String to = itemsBean.getTo();//债权人
        String from = itemsBean.getFrom();//债务人
        Long amout = itemsBean.getAmout();//主体金额
        int payStatus = itemsBean.getPayStatus();//支付状态
        String recommend=itemsBean.getRecommend();
        //查看详情
        vh.tv_check_the_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,Details_About_Debt.class).putExtra("id",items.get(position).getId()).putExtra("orderId",itemsBean.getOrderId()));
            }
        });
        vh.entrytime.setText(createTime);
        vh.cretidors.setText(to);
        vh.debtor.setText(from);
        vh.mainmoney.setText(amout + "");
        vh.tv_number.setText(position + 1 + "");
        vh.tv_recommend.setText(recommend);
        if (payStatus == 0) {//点击付费
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                vh.tv_pay_redor.setBackground(context.getDrawable(R.drawable.login_btn));
                vh.tv_pay_redor.setTextColor(context.getResources().getColor(R.color.white));
                vh.tv_pay_redor.setText("付费备案");
            }
            vh.tv_pay_redor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ToPay.class);
                    intent.putExtra(ConstUtils.MONEY, ConstUtils.DEBT_BEIAN);
                    context.startActivity(intent);
                    String oId=itemsBean.getOrderId();
                    if (oId!=null){
                        String qianshu=itemsBean.getQianshu();
                        AddDebtBean addDebtBean=new AddDebtBean(oId,qianshu,0);
                        EventBus.getDefault().postSticky(addDebtBean);
                    }else{
                        ToastUtil.showShort(context,"支付异常");
                    }

                }
            });
        } else if (payStatus == 1) {
            vh.tv_pay_redor.setClickable(false);
            vh.tv_pay_redor.setBackground(null);
            vh.tv_pay_redor.setTextColor(context.getResources().getColor(R.color.gray));
            vh.tv_pay_redor.setText("已付费");
        }


        return convertView;
    }

    public class ViewHolder {
        //依次录入时间，债权人，债务人，查看详情，主体金额,付费备案；
        private TextView entrytime, cretidors, debtor, mainmoney,tv_number,tv_pay_redor,tv_check_the_details,tv_recommend;
    }
}
