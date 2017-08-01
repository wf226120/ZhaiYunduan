package com.jlgproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.New_Assets;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Asserts_Infromations;
import com.jlgproject.model.Asset_Delete;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by sunbeibei on 2017/5/7.
 */

public class Asset_Information_Adapter extends BaseAdapter implements HttpMessageInfo.IMessage{
    private Context context;
    List<Asserts_Infromations.DataBean.ItemsBean> items;
    private Long id;

    public Asset_Information_Adapter(Context context) {
        this.context=context;
    }

    public List<Asserts_Infromations.DataBean.ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<Asserts_Infromations.DataBean.ItemsBean> items) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.list_asset_item,null);
           vh= new ViewHolder();
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
        final Asserts_Infromations.DataBean.ItemsBean itemsBean = items.get(position);
        String name = itemsBean.getName();
        id=itemsBean.getId();
        int assetNum = itemsBean.getAssetNum();
        int totalAmout = itemsBean.getTotalAmout();
        Long tradeableAssets = itemsBean.getTradeableAssets();

        vh.assetname.setText(name);
        vh.tv_number.setText(position+1+"");
        vh.number.setText(assetNum+"");
        vh.total_value.setText(totalAmout+"");
        vh.current_value.setText(tradeableAssets+"");
        //删除
        vh.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.remove(position);
                requestDelete();
                notifyDataSetChanged();

            }
        });
        //点击编辑
        vh.the_editor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long assetId=items.get(position).getId();
                L.e("---------适配器 Id--------"+assetId);
                Intent intent=new Intent(context,New_Assets.class);
                intent.putExtra(ConstUtils.ASSET_ID,assetId);
                intent.putExtra("titlename",itemsBean.getName().toString());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    //删除item
    private void requestDelete(){
        Login_zud zud= (Login_zud) SharedUtil.getSharedUtil().getObject(context, ConstUtils.IOGIN_INFO, null);
        HttpMessageInfo<Asset_Delete>info=new HttpMessageInfo<>(BaseUrl.ASSERT_DELETE,new Asset_Delete());
        AddHeaders headers = new AddHeaders();
        headers.add("Authorization", zud.getData().getToken());
        RequestBody requestBody = new FormBody.Builder()
                .add("assetId",id+"")
                .build();

        info.setFormBody(requestBody);
        OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST,info,null,headers,1);
    }



    @Override
    public void getServiceData(Object o) {
              Asset_Delete delete = (Asset_Delete) o;
        String state = delete.getState();
        if (state.equals("ok")){
            ToastUtil.showShort(context,"删除成功");
        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {

    }

    public class  ViewHolder{//资产名称，数量，总价值，流通价值；删除，编辑
        private TextView assetname,number,total_value,current_value,delete,the_editor,tv_number;
    }
}
