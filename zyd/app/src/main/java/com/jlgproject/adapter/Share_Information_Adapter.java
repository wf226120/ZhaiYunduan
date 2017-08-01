package com.jlgproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Delete_Shares;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.Share_Informations;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/5/8.
 */

public class Share_Information_Adapter extends BaseAdapter implements HttpMessageInfo.IMessage{
    private Context context;
   private List<Share_Informations.DataBean.ItemsBean> items;
    private int id;

    public Share_Information_Adapter(Context context, List<Share_Informations.DataBean.ItemsBean> items) {
        this.context=context;
        this.items=items;
    }

    public void setItems(List<Share_Informations.DataBean.ItemsBean> items) {
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
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.share_information_list_item,null);
           vh= new ViewHolder();
            vh.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            vh.share_name= (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.id_number= (TextView) convertView.findViewById(R.id.tv_h_zhiquan);
            vh.adress= (TextView) convertView.findViewById(R.id.tv_h_zhaiwu);
            vh.investment_amount= (TextView) convertView.findViewById(R.id.tv_h_principal_amount);
            vh.the_registered_capital= (TextView) convertView.findViewById(R.id.tv_h_resgier_money);
            vh.investment_proportion= (TextView) convertView.findViewById(R.id.tv_h_investment_proportion);
            vh.actually_realized_capital= (TextView) convertView.findViewById(R.id.tv_h_actually_realized_capital);
            vh.delete= (TextView) convertView.findViewById(R.id.tv_item_submitted);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        Share_Informations.DataBean.ItemsBean itemsBean = items.get(position);
        String id196 = itemsBean.get_$Id196();
        String actualCapital = itemsBean.getActualCapital();//实到资本
        String address = itemsBean.getAddress();//地址
        //投资金额
        long amount = itemsBean.getAmount();
        long createTime = itemsBean.getCreateTime();
        Object createUser = itemsBean.getCreateUser();
        int deleteReason = itemsBean.getDeleteReason();
        //股权id
        id = itemsBean.getId();
        String proportion = itemsBean.getProportion();//投资比例
        String registeredCapital = itemsBean.getRegisteredCapital();//注册资本
        String shareholderCode = itemsBean.getShareholderCode();//股东证件号
        String shareholderName = itemsBean.getShareholderName();//股东名称
        long updateTime = itemsBean.getUpdateTime();
        Object updateUser = itemsBean.getUpdateUser();
        int userId = itemsBean.getUserId();
        int zid = itemsBean.getZid();
        vh.tv_number.setText(position+1+"");
        vh.share_name.setText(shareholderName);
        vh.id_number.setText(shareholderCode);
        vh.adress.setText(address);
        vh.investment_amount.setText(amount+"");
        vh.the_registered_capital.setText(registeredCapital);
        vh.investment_proportion.setText(proportion+"%");
        vh.actually_realized_capital.setText(registeredCapital);
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                requestShareDelete();
                notifyDataSetChanged();
            }
        });
        return convertView;

    }
    private void requestShareDelete(){
        Login_zud zud = (Login_zud) SharedUtil.getSharedUtil().getObject(context, ConstUtils.IOGIN_INFO,null);
        HttpMessageInfo<Delete_Shares>info = new HttpMessageInfo<>(BaseUrl.DELETE_SHARE,new Delete_Shares());
        info.setiMessage(this);
        AddHeaders headers = new AddHeaders();
        headers.add("Authorization", zud.getData().getToken());
        RequestBody requestBody = new FormBody.Builder()
                .add("quityid",id+"")
                .build();
        info.setFormBody(requestBody);
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST,info,null,headers,1);
    }

    @Override
    public void getServiceData(Object o) {
             if (o instanceof Delete_Shares){
                 Delete_Shares shares = (Delete_Shares) o;
                 String state = shares.getState();
                 if (state.equals("ok")){
                     ToastUtil.showShort(context,shares.getMessage());
                 }else{
                     ToastUtil.showShort(context,"删除股权失败");
                 }
             }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
            ToastUtil.showShort(context,"网络异常");
    }

    public class ViewHolder{
        //股东姓名，证件号，地址，投资金额，注册资本，投资比例，实到资本；
        TextView tv_number,share_name,id_number,adress,investment_amount,the_registered_capital,investment_proportion,actually_realized_capital,delete;
    }
}
