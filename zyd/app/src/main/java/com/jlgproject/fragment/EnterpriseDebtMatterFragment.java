package com.jlgproject.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.jlgproject.R;
import com.jlgproject.activity.New_Debt_Matter_Preson;
import com.jlgproject.activity.UpdownPhotos;
import com.jlgproject.base.BaseFragment;
import com.jlgproject.model.AddDebtVo;
import com.jlgproject.model.AddRess.Add;
import com.jlgproject.model.DebtCompany;
import com.jlgproject.util.L;
import com.jlgproject.util.ProvinceCq;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunbeibei on 2017/4/28.
 * 债事企业
 */

public class EnterpriseDebtMatterFragment extends BaseFragment implements View.OnClickListener {

    private Button button_next;
    private EditText mEt_organization_code, mEt_enterpeise_name,
            mEt_legal_person_name,
            mEt_legal_person_icard,
            mEt_industry,
            mEt_contact_phone_number,
            mEt_the_registered_capital,
            mEt_email, mEt_qq, mEt_wetchcat;
    private Spinner mProvinceSpinner_zsqy,//省市区
            mCitySpinner_zsqy,
            mAreaSpinner_zsqy;

    // 列表选择的省市区
    private String selectedPro = "";
    private String selectedCity = "";
    public String selectedArea = "";
    // 省份
    private String[] mProvinceDatas;
    // 城市
    private String[] mCitiesDatas;
    // 地区
    private String[] mAreaDatas;

    private ArrayAdapter<String> mProvinceAdapter;
    private ArrayAdapter<String> mCityAdapter;
    private ArrayAdapter<String> mAreaAdapter;
    // 存储省对应的所有市 name
    private Map<String, String[]> mCitiesDataMap = new HashMap<String, String[]>();
    // 存储市对应的所有区 name
    private Map<String, String[]> mAreaDataMap = new HashMap<String, String[]>();
    //省市區Id
    private String shengId = null, shiId = null, quId = null;

    private String isOwer;//判断是添加自己的债事企业，还是别人的 1是登录用户，2是其他用户
    private Add ssq ;//地址

    @Override
    public int getLoadViewId() {
        return R.layout.fragment_enterprese_debt;
    }

    @Override
    public View initView(View view) {
        isOwer = New_Debt_Matter_Preson.isOwer;
        mEt_organization_code = (EditText) view.findViewById(R.id.et_organization_code);//组织机构代码
        mEt_enterpeise_name = (EditText) view.findViewById(R.id.et_enterpeise_name);//债事企业名称
        mEt_legal_person_name = (EditText) view.findViewById(R.id.et_legal_person_name);//企业法人姓名
        mEt_legal_person_icard = (EditText) view.findViewById(R.id.et_legal_person_icard);//法人身份证号
        mEt_industry = (EditText) view.findViewById(R.id.et_industry);//所属行业
        mEt_contact_phone_number = (EditText) view.findViewById(R.id.et_contact_phone_number);//联系电话
        mEt_the_registered_capital = (EditText) view.findViewById(R.id.et_the_registered_capital);//注册资本
        mEt_email = (EditText) view.findViewById(R.id.et_email);//邮箱
        mEt_qq = (EditText) view.findViewById(R.id.et_qq);
        mEt_wetchcat = (EditText) view.findViewById(R.id.et_wetchcat);//微信
        button_next = (Button) view.findViewById(R.id.btn_next);//下一步
        button_next.setOnClickListener(this);
        mProvinceSpinner_zsqy = (Spinner) view.findViewById(R.id.spinner_pro);
        mCitySpinner_zsqy = (Spinner) view.findViewById(R.id.spinner_city);
        mAreaSpinner_zsqy = (Spinner) view.findViewById(R.id.spinner_area);
        jsonCitisData();
        initSpinner();

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == button_next) {
            next();
        }
    }

    private void next() {

        String qq = "";
        String weixin = "";
        mEt_qq = (EditText) view.findViewById(R.id.et_qq);
        mEt_wetchcat = (EditText) view.findViewById(R.id.et_wetchcat);//微信

        if (TextUtils.isEmpty(mEt_organization_code.getText().toString())) {
            toastMeass("请输入组织机构代码");
            return;
        }
        if (TextUtils.isEmpty(mEt_enterpeise_name.getText().toString())) {
            toastMeass("请输入债事企业名称");
            return;
        }
        if (TextUtils.isEmpty(mEt_legal_person_name.getText().toString())) {
            toastMeass("请输入企业法人姓名");
            return;
        }
        if (TextUtils.isEmpty(mEt_legal_person_icard.getText().toString())) {
            toastMeass("请输入法人身份证号");
            return;
        }

        if (TextUtils.isEmpty(mEt_industry.getText().toString())) {
            toastMeass("请输入公司所属行业");
            return;
        }

        if (TextUtils.isEmpty(mEt_contact_phone_number.getText().toString())) {
            toastMeass("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(mEt_the_registered_capital.getText().toString())) {
            toastMeass("请输入注册资本");
            return;
        }
        String jine=mEt_the_registered_capital.getText().toString();
        Long vul=Long.parseLong(jine);
        if(vul==0){
            toastMeass("注册资本不能为0");
            return;
        }

        if (!TextUtils.isEmpty(mEt_qq.getText().toString())) {
            qq = mEt_qq.getText().toString();
        }
        if (!TextUtils.isEmpty(mEt_wetchcat.getText().toString())) {
            weixin = mEt_wetchcat.getText().toString();
        }

        String zzjgdm = mEt_organization_code.getText().toString();
        String zsqymc = mEt_enterpeise_name.getText().toString();
        String zsqyfrmc = mEt_legal_person_name.getText().toString();
        String frffzh = mEt_legal_person_icard.getText().toString();
        String sshy = mEt_industry.getText().toString();
        String sjh = mEt_contact_phone_number.getText().toString();
        String zczb = mEt_the_registered_capital.getText().toString();
        String dzyx = mEt_email.getText().toString();


        DebtCompany debtCompany = new DebtCompany();
        debtCompany.setOrganCode(zzjgdm);
        debtCompany.setDebtCompanyName(zsqymc);
        debtCompany.setName(zsqyfrmc);
        debtCompany.setIdCode(frffzh);
        debtCompany.setProvinceCode(shengId);
        debtCompany.setCityCode(shiId);
        debtCompany.setPrCode(quId);
        debtCompany.setCategory(sshy);
        debtCompany.setPhoneNumber(sjh);
        debtCompany.setRegisteredCapital(zczb);
        debtCompany.setEmail(dzyx);
        debtCompany.setQq(qq);
        debtCompany.setWeChat(weixin);
        AddDebtVo addQy = new AddDebtVo();
        addQy.setDebtCompany(debtCompany);
        addQy.setType("2");
        L.e("----isOwer-----" + isOwer);
        addQy.setIsOwer(isOwer);
        EventBus.getDefault().postSticky(addQy);
        startActivity(new Intent(getActivity(), UpdownPhotos.class).putExtra("addpic", 6));
    }

    private void jsonCitisData() {

        Gson gson=new Gson();
        ssq=gson.fromJson(ProvinceCq.InitData(),Add.class);
        int shengSize=ssq.getData().size();//省个数
        mProvinceDatas= new String[shengSize];

        String mProvinceStr;
        for(int i=0;i<shengSize;i++){

            mProvinceStr=ssq.getData().get(i).getValue();
            mProvinceDatas[i] = mProvinceStr;
            int shiSize=ssq.getData().get(i).getChild().size();//市个数
            mCitiesDatas = new String[shiSize];

            String mCityStr;
            for(int j=0;j<shiSize;j++){
                mCityStr=ssq.getData().get(i).getChild().get(j).getValue();
                mCitiesDatas[j] = mCityStr;
                int quSize=ssq.getData().get(i).getChild().get(j).getChild().size();
                mAreaDatas = new String[quSize];
                for(int k=0;k<quSize;k++){
                    mAreaDatas[k] = ssq.getData().get(i).getChild().get(j).getChild().get(k).getValue();
                }
                // 存储市对应的所有第三级区域
                mAreaDataMap.put(mCityStr, mAreaDatas);
            }
            // 存储省份对应的所有市
            mCitiesDataMap.put(mProvinceStr, mCitiesDatas);
        }
    }

    private void initSpinner() {

        //设置未下拉时的布局
        mProvinceAdapter = new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_item, mProvinceDatas);
        //设置下拉时的布局
        mProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProvinceSpinner_zsqy.setAdapter(mProvinceAdapter);

        // 省份选择
        mProvinceSpinner_zsqy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPro = "";
                selectedPro = (String) parent.getSelectedItem();
                // 根据省份更新城市区域信息
                shengId = updateCity(selectedPro);
                Log.d("--H_OpenDebt-", "mProvinceSpinner has excuted !" + "selectedPro is " + selectedPro);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 市选择
        mCitySpinner_zsqy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = "";
                selectedCity = (String) parent.getSelectedItem();
                shiId = updateArea(selectedPro, selectedCity);
                Log.d("----H_OpenDebt--", "mCitySpinner has excuted !" + "selectedCity is " + selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 区选择
        mAreaSpinner_zsqy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = "";
                selectedArea = (String) parent.getSelectedItem();
                quId = getQuId(selectedPro, selectedCity, selectedArea);
                Log.d("-------", "mAreaSpinner has excuted !" + "selectedArea is " + selectedArea);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 根据省份更新城市数据    返回省id
     */
    private String updateCity(String pro) {
        String shengId = null;
        String[] cities =null;
        List<Add.DataBean.ChildBean> shiNameList;
        for(int i=0;i<ssq.getData().size();i++){
            if(pro.equals(ssq.getData().get(i).getValue())){
                shiNameList = ssq.getData().get(i).getChild();
                shengId = ssq.getData().get(i).getId();
                Log.e("-----", "---选择---省 Id---- " + shengId);
                cities = new String[shiNameList.size()];
                for (int j = 0; j < shiNameList.size(); j++) {
                    cities[j] = shiNameList.get(j).getValue();
                }
            }
        }

        // 存在区
        mCityAdapter = new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_item, cities);
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitySpinner_zsqy.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
        mCitySpinner_zsqy.setSelection(0);
        return shengId;
    }

    /**
     * 根据市 选择对应的区  返回市Id
     */
    private String updateArea(String pro, String city) {

        String[] areas = null;
        String shiId = null;

        //市 集合
        List<Add.DataBean.ChildBean> shiNameList = null;
        //区 集合
        List<com.jlgproject.model.AddRess.ChildBean> quNameList = null;

        for (int i = 0; i < ssq.getData().size(); i++) {//循环省

            //找到指定省
            if (pro.equals(ssq.getData().get(i).getValue())) {
                //拿到指定省下的市
                shiNameList = ssq.getData().get(i).getChild();
                //循环市
                for (int j = 0; j < shiNameList.size(); j++) {

                    //找到指定市
                    if (city.equals(shiNameList.get(j).getValue())) {
                        shiId = shiNameList.get(j).getId();
                        quNameList = shiNameList.get(j).getChild();
                        if(quNameList!=null && quNameList.size()!=0){
                            areas = new String[quNameList.size()];
                            for (int k = 0; k < quNameList.size(); k++) {
                                areas[k] = quNameList.get(k).getValue();
                            }
                        }else{
                            areas=null;
                        }

                    }
                }
            }
        }
        // 存在区
        if (areas != null) {
            // 存在区
            mAreaSpinner_zsqy.setVisibility(View.VISIBLE);
            mAreaAdapter = new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_item, areas);
            mAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAreaSpinner_zsqy.setAdapter(mAreaAdapter);
            mAreaAdapter.notifyDataSetChanged();
            mAreaSpinner_zsqy.setSelection(0);
        } else {
            mAreaSpinner_zsqy.setVisibility(View.GONE);
            quId="";
        }
        return shiId;
    }

    /**
     * 获取区Id
     *
     * @param pro
     * @param city
     * @param area
     * @return
     */
    private String getQuId(String pro, String city, String area) {

        String shiId = null;
        String quId = null;
        //市 集合
        List<Add.DataBean.ChildBean> shiNameList = null;
        //区 集合
        List<com.jlgproject.model.AddRess.ChildBean> quNameList = null;
        for (int i = 0; i < ssq.getData().size(); i++) {//循环省

            //找到指定省
            if (pro.equals(ssq.getData().get(i).getValue())) {
                //拿到指定省下的市
                shiNameList = ssq.getData().get(i).getChild();
                //循环市
                for (int j = 0; j < shiNameList.size(); j++) {
                    //找到指定市
                    if (city.equals(shiNameList.get(j).getValue())) {

                        shiId = shiNameList.get(j).getId();
                        quNameList = shiNameList.get(j).getChild();
                        for (int k = 0; k < quNameList.size(); k++) {
                            if (area.equals(quNameList.get(k).getValue())) {
                                quId = quNameList.get(k).getId();
                                Log.e("---", "---选择---区---- " + quNameList.get(k).getValue());
                                Log.e("---", "---选择---区--Id-- " + quNameList.get(k).getId());
                            }
                        }
                    }

                }
            }
        }
        return quId;
    }

    private void toastMeass(String mes) {
        ToastUtil.showShort(getActivity(), mes);
    }
}
