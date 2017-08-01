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
import com.jlgproject.model.Buness_Informations_Info;
import com.jlgproject.model.Delete_Buness_Information;
import com.jlgproject.model.Login_zud;
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

public class Business_Infromation_Adapter extends BaseAdapter implements HttpMessageInfo.IMessage {
    private Context context;
    private List<Buness_Informations_Info.DataBean.ItemsBean> items;
    private int id;

    public Business_Infromation_Adapter(Context context, List<Buness_Informations_Info.DataBean.ItemsBean> items) {
        this.context=context;
        this.items=items;
    }

    public void setItems(List<Buness_Informations_Info.DataBean.ItemsBean> items) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.business_information_list_item,null);
            vh=new ViewHolder();
            vh.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            vh.share_name= (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.id_number= (TextView) convertView.findViewById(R.id.tv_h_zhiquan);
            vh.adress= (TextView) convertView.findViewById(R.id.tv_h_zhaiwu);
            vh.investment_amount= (TextView) convertView.findViewById(R.id.tv_h_principal_amount);
            vh.the_registered_capital= (TextView) convertView.findViewById(R.id.tv_h_resgier_money);
            vh.investment_proportion= (TextView) convertView.findViewById(R.id.tv_h_investment_proportion);
            vh.actually_realized_capital= (TextView) convertView.findViewById(R.id.tv_h_actually_realized_capital);
            vh.tv_h_its_annual= (TextView) convertView.findViewById(R.id.tv_h_its_annual);
            vh.tv_h_the_profit_margin= (TextView) convertView.findViewById(R.id.tv_h_the_profit_margin);
            vh.tv_h_total_investment= (TextView) convertView.findViewById(R.id.tv_h_total_investment);
           vh.deletebuness= (TextView) convertView.findViewById(R.id.tv_item_submitted);
            convertView.setTag(vh);


        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        Buness_Informations_Info.DataBean.ItemsBean itemsBean = items.get(position);
        vh.deletebuness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                requesetDeleteBuness();
                notifyDataSetChanged();
            }
        });
        id = itemsBean.getId();
        vh.tv_number.setText(position+1+"");
        vh.share_name.setText(itemsBean.getLegalPersonName());
        vh.id_number.setText(itemsBean.getTaxNumber());
        vh.adress.setText(itemsBean.getPhoneNumber());
        vh.investment_amount.setText(itemsBean.getAddress());
        vh.the_registered_capital.setText(itemsBean.getLastSales()+"");
        vh.investment_proportion.setText(itemsBean.getGross()+"");
        vh.actually_realized_capital.setText(itemsBean.getLastElectricityBills());
        vh.tv_h_its_annual.setText(itemsBean.getYear());
        vh.tv_h_the_profit_margin.setText(itemsBean.getProfitMargin()+"%");
        vh.tv_h_total_investment.setText(itemsBean.getTotalInvestment());
        return convertView;
    }

    private void requesetDeleteBuness() {
        Login_zud zud = (Login_zud) SharedUtil.getSharedUtil().getObject(context, ConstUtils.IOGIN_INFO,null);
        AddHeaders headers = new AddHeaders();
        headers.add("Authorization", zud.getData().getToken());
        RequestBody requestBody = new FormBody.Builder()
                .add("manageStateId",id+"")
                .build();

        HttpMessageInfo<Delete_Buness_Information>info =new HttpMessageInfo<>(BaseUrl.DELETE_BUNESS,new Delete_Buness_Information());
        info.setiMessage(this);
        info.setFormBody(requestBody);
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST,info,null,headers,1);


    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Delete_Buness_Information){
            Delete_Buness_Information delete_buness_information = (Delete_Buness_Information) o;
            if (delete_buness_information.getState().equals("ok")){
                ToastUtil.showShort(context,"删除经营信息成功");
            }else{
                ToastUtil.showShort(context,"系统异常");
            }
        }else {
            ToastUtil.showShort(context,"操作失败");
        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {
        ToastUtil.showShort(context,"网络异常");
    }

    public class ViewHolder{
        //法人名称,税号，法人电话，经营地址，上年度销售额，总收入，年度电费，所属年度，利润率，总投资
        private TextView tv_number,share_name
                ,id_number,adress,investment_amount,
                the_registered_capital, investment_proportion,
                actually_realized_capital,tv_h_its_annual
                ,tv_h_the_profit_margin,tv_h_total_investment,deletebuness;

    }
}
