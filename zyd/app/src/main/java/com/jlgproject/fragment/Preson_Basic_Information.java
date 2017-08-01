package com.jlgproject.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Preson_Details;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Login_zud;
import com.jlgproject.model.Preson_Basic_Informations;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/9.
 * 债事人----个人 基本信息
 */

public class Preson_Basic_Information extends BaseFragment implements HttpMessageInfo.IMessage {


    private TextView icode;
    private TextView get_legal_person_name;
    private TextView get_legal_person_icard;
    private TextView get_area;
    private TextView get_registered_capital;
    private TextView get_adress;
    private TextView get_industry;
    private String tyep;
    private Long id;

    @Override
    public int getLoadViewId() {
        return R.layout.preson_basic__information;
    }

    @Override
    public void initDatas() {
        super.initDatas();
        id = Debt_Matter_Management_Preson_Details.id;
        tyep = Debt_Matter_Management_Preson_Details.tyep;
        L.e("---基本信息---" + id + "---type----" + tyep);
    }

    @Override
    public View initView(View view) {
        icode = (TextView) view.findViewById(R.id.get_code);//身份证号
        get_legal_person_name = (TextView) view.findViewById(R.id.get_legal_person_name);//所属地
        get_legal_person_icard = (TextView) view.findViewById(R.id.get_legal_person_icard);//手机号
        get_area = (TextView) view.findViewById(R.id.get_area);//联系地址
        get_registered_capital = (TextView) view.findViewById(R.id.get_registered_capital);//电子邮箱
        get_adress = (TextView) view.findViewById(R.id.get_adress);//qq号
        get_industry = (TextView) view.findViewById(R.id.get_industry);//微信号

        requestBasicPerson();

        return view;
    }

    private void requestBasicPerson() {
        if (ShowDrawDialog(getActivity())) {
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            GetParmars parmars = new GetParmars();
            L.e("-----详情Id------" + id);
            parmars.add("id", id);
            parmars.add("type", tyep);
            HttpMessageInfo<Preson_Basic_Informations> info = new HttpMessageInfo<>(BaseUrl.DEBTS_PRESON_INFORMATION, new Preson_Basic_Informations());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Preson_Basic_Informations) {
            Preson_Basic_Informations preson_basic_information = (Preson_Basic_Informations) o;
            if (preson_basic_information != null) {
                DismissDialog();
                if (preson_basic_information.getState().equals("ok")) {
                    Preson_Basic_Informations.DataBean data = preson_basic_information.getData();
                    String idCode2 = data.getIdCode();
                    String idCode1 = data.getLegalPersonId();
                    String area = data.getArea();
                    String phoneNumber = data.getPhoneNumber();
                    String contactAddress = data.getContactAddress();
                    Object email = data.getEmail();
                    Object qq = data.getQq();
                    Object weChat = data.getWeChat();
                    if (!TextUtils.isEmpty(idCode1)) { //债事人身份证号
                        icode.setText(idCode1);
                    } else {
                        icode.setText(idCode2);
                    }
                    get_legal_person_name.setText(area);
                    get_legal_person_icard.setText(phoneNumber);
                    get_area.setText(contactAddress);
                    get_registered_capital.setText(email + "");
                    get_adress.setText(qq + "");
                    get_industry.setText(weChat + "");
                } else {
                    ToastUtil.showShort(getActivity(), preson_basic_information.getMessage().toString());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(getActivity(), "服务器繁忙，请稍后再试");

    }
}
