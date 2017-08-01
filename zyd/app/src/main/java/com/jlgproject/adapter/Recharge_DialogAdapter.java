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

public class Recharge_DialogAdapter extends BaseAdapter {

    private Context mContext;
    private boolean idSelect=false;

   public  Recharge_DialogAdapter(Context mContext){
       this.mContext=mContext;
   }

    @Override
    public int getCount() {
        return 2;
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
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolde vh=null;
        if(convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.recharge_dialog_list_item,null);
            vh=new ViewHolde();
            vh.iv_list_ite_tub= (ImageView) convertView.findViewById(R.id.iv_list_ite_tub);
            vh.tv_list_item_yhk= (TextView) convertView.findViewById(R.id.tv_list_item_yhk);
            vh.tv_list_item_ed= (TextView) convertView.findViewById(R.id.tv_list_item_ed);
            vh.iv_list_item_dg= (ImageView) convertView.findViewById(R.id.iv_list_item_dg);
//            vh.ll_dialog_parent= (LinearLayout) convertView.findViewById(R.id.ll_dialog_parent);

            convertView.setTag(vh);

        }else{
            vh= (ViewHolde) convertView.getTag();

        }

        Glide.with(mContext).load(R.mipmap.recharge_tjyhk).into(vh.iv_list_ite_tub);
        vh.tv_list_item_yhk.setText("招商银行卡储蓄卡（0506）"+position);
        vh.tv_list_item_ed.setText("可用额度50000.00元"+position);
        vh.iv_list_item_dg.setVisibility(View.GONE);
        final ViewHolde finalVh = vh;
//        vh.ll_dialog_parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showShort(mContext,position+"");
//                if(idSelect){
//
//                    finalVh.iv_list_item_dg.setVisibility(View.GONE);
//                    idSelect=false;
//                }else{
//                    finalVh.iv_list_item_dg.setVisibility(View.VISIBLE);
//                    idSelect=true;
//                }
//
//            }
//        });

        return convertView;
    }

    public class ViewHolde{
        private ImageView iv_list_ite_tub;//银行卡图标
        private TextView tv_list_item_yhk;//银行卡名称
        private TextView tv_list_item_ed;//银行卡额度
        private ImageView iv_list_item_dg;//选中时状态
//        private LinearLayout ll_dialog_parent;//父布局
    }
}
