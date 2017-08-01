package com.jlgproject.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.activity.Debt_Matter_Management_Details;
import com.jlgproject.activity.Login;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.CompanyBasicInformation;
import com.jlgproject.model.Login_zud;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/6.
 * 个人--基本信息
 */

public class Basic_Information extends BaseFragment implements HttpMessageInfo.IMessage {


    private long id;
    private String type;
    private TextView get_code;
    private TextView get_legal_person_name;
    private TextView get_legal_person_icard;
    private TextView get_area;
    private TextView get_phone_number;
    private TextView get_registered_capital;
    private TextView get_industry;
    private TextView get_email;
    private TextView get_qq;
    private TextView get_wetchat;


    @Override
    public int getLoadViewId() {
        return R.layout.basic_information;
    }

    @Override
    public View initView(View view) {
        //组织机构代码
        get_code = (TextView) view.findViewById(R.id.get_code);
        //企业法人姓名
        get_legal_person_name = (TextView) view.findViewById(R.id.get_legal_person_name);
        //法人身份证件
        get_legal_person_icard = (TextView) view.findViewById(R.id.get_legal_person_icard);
        //所属地区
        get_area = (TextView) view.findViewById(R.id.get_area);
        //联系电话
        get_phone_number = (TextView) view.findViewById(R.id.get_phone_number);
        //注册资本
        get_registered_capital = (TextView) view.findViewById(R.id.get_registered_capital);
        //所属行业
        get_industry = (TextView) view.findViewById(R.id.get_industry);
        //电子邮箱
        get_email = (TextView) view.findViewById(R.id.get_email);
        //qq
        get_qq = (TextView) view.findViewById(R.id.get_qq);
        //微信
        get_wetchat = (TextView) view.findViewById(R.id.get_wetchat);

        requstCompanBasic();
        return view;
    }


    @Override
    public void initDatas() {
        super.initDatas();
        //获取 企业信息 id type
        id = Debt_Matter_Management_Details.id;
        type = Debt_Matter_Management_Details.type;
        L.e("-----企业信息 id -----" + id + "--type---" + type);
    }

    private void requstCompanBasic() {
        if (ShowDrawDialog(getActivity())) {
            GetParmars pamar = new GetParmars();
            pamar.add("id", id);
            pamar.add("type", type);
            AddHeaders heards = new AddHeaders();
            heards.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<CompanyBasicInformation> info = new HttpMessageInfo<>(BaseUrl.DEBTS_PRESON_INFORMATION, new CompanyBasicInformation());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, pamar, heards, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof CompanyBasicInformation) {
            CompanyBasicInformation information = (CompanyBasicInformation) o;
            if (information != null) {
                DismissDialog();
                if (information.getState().equals("error")) {
                    ToastUtil.showShort(getActivity(), information.getMessage());
                } else {

                    String name = information.getData().getName();

                    String debtCompanyName = information.getData().getDebtCompanyName();//债事企业名称
                    String area = information.getData().getArea();//所属地区
                    String phoneNumber = information.getData().getPhoneNumber();//手机号
                    int registeredCapital = information.getData().getRegisteredCapital();//注册资本
                    String category = information.getData().getCategory();//所属行业
                    String email = information.getData().getEmail();//电子邮箱
                    String qq = information.getData().getQq();//qq
                    String weChat = information.getData().getWeChat();//微信
                    String organCode = information.getData().getOrganCode();//组织机构代码

                    String faPersonName;//企业法人姓名  1.0
                    if (!TextUtils.isEmpty(information.getData().getLegalPersonName())) {
                        faPersonName = information.getData().getLegalPersonName();
                    } else {  // 2.0
                        faPersonName = information.getData().getName();
                    }

                    String shengfenz;
                    if (!TextUtils.isEmpty(information.getData().getLegalPersonId())) {
                        shengfenz = information.getData().getLegalPersonId();//法人身份证号 1.0
                    } else {   //2.0 数据
                        shengfenz = information.getData().getIdCode();
                    }

                    get_legal_person_name.setText(faPersonName);
                    get_legal_person_icard.setText(shengfenz);
                    get_code.setText(organCode);
                    get_area.setText(area);
                    get_phone_number.setText(phoneNumber);
                    get_registered_capital.setText(registeredCapital + "");
                    get_industry.setText(category);
                    get_email.setText(email);
                    get_qq.setText(qq);
                    get_wetchat.setText(weChat);
                }
            }
        }

    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(getActivity(), "服务繁忙，请稍后再试");
    }
}
