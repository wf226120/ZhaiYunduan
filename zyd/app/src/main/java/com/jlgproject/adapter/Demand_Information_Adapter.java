package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.New_Demand;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Delete_Demands;
import com.jlgproject.model.Demand_Informations;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;
import java.util.List;
import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/6/21.
 */

public class Demand_Information_Adapter extends BaseAdapter implements HttpMessageInfo.IMessage{

    private Context context ;
    private List<Demand_Informations.DataBean.ItemsBean> items;
    private Long id;

    public Demand_Information_Adapter(Context context) {
        this.context = context;
    }

    public List<Demand_Informations.DataBean.ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<Demand_Informations.DataBean.ItemsBean> items) {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.demand_list_item,null);
            vh= new Demand_Information_Adapter.ViewHolder();
            vh.assetname= (TextView) convertView.findViewById(R.id.tv_h_name);
            vh.number= (TextView) convertView.findViewById(R.id.tv_h_zhiquan);
            vh.total_value= (TextView) convertView.findViewById(R.id.tv_h_zhaiwu);
            vh.current_value= (TextView) convertView.findViewById(R.id.tv_h_principal_amount);
            vh.the_editor= (TextView) convertView.findViewById(R.id.tv_check_the_details);
            vh.delete= (TextView) convertView.findViewById(R.id.tv_pay_the_record);
            vh.tv_number= (TextView) convertView.findViewById(R.id.tv_number);
            vh.delete= (TextView) convertView.findViewById(R.id.tv_pay_the_record);

            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        final Demand_Informations.DataBean.ItemsBean itemsBean = items.get(position);
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                requestDelete();
                notifyDataSetChanged();
            }
        });
        vh.the_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, New_Demand.class);
                Bundle bundle=new Bundle();
                Demand_Informations.DataBean.ItemsBean itemsBean=items.get(position);
                bundle.putSerializable("demandBean",itemsBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        String name = itemsBean.getName();
        Long assetNum = itemsBean.getAssetNum();
        Long totalAmout = itemsBean.getTotalAmout();
        int tangible = itemsBean.getTangible();
        id = itemsBean.getId();
        vh.assetname.setText(name);
        vh.tv_number.setText(position+1+"");
        vh.number.setText(assetNum+"");
        vh.total_value.setText(totalAmout+"");
        if (tangible==1) {
            vh.current_value.setText("有形资产");
        }else {
            vh.current_value.setText("无形资产");
        }

        return convertView;
    }
    private void requestDelete(){
        Login_zud zud= (Login_zud) SharedUtil.getSharedUtil().getObject(context, ConstUtils.IOGIN_INFO, null);
        AddHeaders headers = new AddHeaders();
        headers.add("Authorization", zud.getData().getToken());
        Log.e("++++++++++++++++++++++",zud.getData().getToken());
        HttpMessageInfo<Delete_Demands> info=new HttpMessageInfo<>(BaseUrl.DELETE_DEMAND,new Delete_Demands());
        info.setiMessage(this);
        GetParmars parmars = new GetParmars();
        parmars.add("demandid",id+"");
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET,info,parmars,headers,1);
    }

    @Override
    public void getServiceData(Object o) {
       if (o instanceof Delete_Demands){
           Delete_Demands delete_demands = (Delete_Demands) o;
           if (delete_demands.getState().equals("ok")){
               ToastUtil.showShort(context,delete_demands.getMessage());
           }else{
               ToastUtil.showShort(context,"删除失败");
           }
       }
    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }

    public class  ViewHolder{//资产名称，数量，总价值，流通价值；删除，编辑
        private TextView assetname,number,total_value,current_value,delete,the_editor,tv_number;
    }

    }

