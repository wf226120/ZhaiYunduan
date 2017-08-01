package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jlgproject.R;

/**
 * @author 王锋 on 2017/5/9.
 */

public class BankCardList_Adapter extends BaseAdapter {

    private Context mContext;

    public BankCardList_Adapter(Context mContext){
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolde viewHolde = null;
        if (convertView == null) {
            viewHolde = new ViewHolde();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.bank_card_list, null);
            viewHolde.iv_bc_yhk_tb = (ImageView) convertView.findViewById(R.id.iv_bc_yhk_tb);
            viewHolde.tv_bn_yhk = (TextView) convertView.findViewById(R.id.tv_bn_yhk);
            viewHolde.tv_bn_lb = (TextView) convertView.findViewById(R.id.tv_bn_lb);
            viewHolde.tv_bn_wh = (TextView) convertView.findViewById(R.id.tv_bn_wh);
            convertView.setTag(viewHolde);
        } else {
            viewHolde = (ViewHolde) convertView.getTag();
        }
        Glide.with(mContext).load(R.mipmap.home_ktzh).into(viewHolde.iv_bc_yhk_tb);
        viewHolde.tv_bn_yhk.setText("招商银行"+position);
        viewHolde.tv_bn_lb.setText("储蓄卡"+position);
        viewHolde.tv_bn_wh.setText("024"+position);

        return convertView;
    }

    class ViewHolde {
        public ImageView iv_bc_yhk_tb;
        public TextView tv_bn_yhk;
        public TextView tv_bn_lb;
        public TextView tv_bn_wh;

    }
}
