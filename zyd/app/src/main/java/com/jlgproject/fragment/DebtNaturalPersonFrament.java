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
import com.jlgproject.model.DebtPerson;
import com.jlgproject.util.ProvinceCq;
import com.jlgproject.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 债事自然人
 */

public class DebtNaturalPersonFrament extends BaseFragment {

    private Button button_next;
    private EditText mEt_nuturel_icard_t,
            mEt_debrpeople_name_t,
            mEt_nuturel_province_t,
            mEt_nuturel_phone_t,
            mEt_nuturel_contact_address_t,
            mEt_nuturel_email_t,
            mEt_nuturel_qq_t,
            mEt_wetchat_t;
    private String isOwer;//判断是添加自己的债事，还是别人的 1是登录用户，2是其他用户
    private Spinner mProvinceSpinner_zsr;
    private Spinner mCitySpinner_zsr;
    private Spinner mAreaSpinner_zsr;

    private Add ssq;//地址
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


    @Override
    public int getLoadViewId() {
        return R.layout.frament_debt_nuturel_person;
    }

    @Override
    public View initView(View view) {
        isOwer = New_Debt_Matter_Preson.isOwer;
        mEt_nuturel_icard_t = (EditText) view.findViewById(R.id.et_nuturel_icard_t);//身份证
        mEt_debrpeople_name_t = (EditText) view.findViewById(R.id.et_debrpeople_name_t);//名称
        mEt_nuturel_phone_t = (EditText) view.findViewById(R.id.et_nuturel_phone_t);//手机号
        mEt_nuturel_contact_address_t = (EditText) view.findViewById(R.id.et_nuturel_contact_address_t);//地址
        mEt_nuturel_email_t = (EditText) view.findViewById(R.id.et_nuturel_email_t);//邮箱
        mEt_nuturel_qq_t = (EditText) view.findViewById(R.id.et_nuturel_qq_t);//QQ
        mEt_wetchat_t = (EditText) view.findViewById(R.id.et_wetchat_t);
        button_next = (Button) view.findViewById(R.id.btn_nuturel_next);
        button_next.setOnClickListener(new View.OnClickListener() {//下一步
            @Override
            public void onClick(View v) {
                next();
            }
        });
        mProvinceSpinner_zsr = (Spinner) view.findViewById(R.id.spinner_pro);
        mCitySpinner_zsr = (Spinner) view.findViewById(R.id.spinner_city);
        mAreaSpinner_zsr = (Spinner) view.findViewById(R.id.spinner_area);
        jsonCitisData();
        initSpinner();
        return view;
    }


    private void next() {
        String qq = null;
        String weixin = null;
        if (TextUtils.isEmpty(mEt_nuturel_icard_t.getText().toString())) {
            ToastUtil.showShort(getActivity(), "请输入身份证号/护照");
            return;
        }
        if (mEt_nuturel_icard_t.getText().toString().length() != 18) {
            ToastUtil.showShort(getActivity(), "输入有误，请重试");
            return;
        }
        if (TextUtils.isEmpty(mEt_debrpeople_name_t.getText().toString())) {
            ToastUtil.showShort(getActivity(), "请输入真实姓名");
            return;
        }
        if (mEt_debrpeople_name_t.getText().toString().length() < 2) {
            ToastUtil.showShort(getActivity(), "姓名长度不能小于2个字符");
            return;
        }

        if (TextUtils.isEmpty(mEt_nuturel_phone_t.getText().toString())) {
            ToastUtil.showShort(getActivity(), "请输入手机号");
            return;
        }
        if (mEt_nuturel_phone_t.getText().toString().length() != 11) {
            ToastUtil.showShort(getActivity(), "请输入正确的手机号");
            return;
        }


        if (TextUtils.isEmpty(mEt_nuturel_contact_address_t.getText().toString())) {
            ToastUtil.showShort(getActivity(), "请输入联系地址");
            return;
        }

        if (!TextUtils.isEmpty(mEt_nuturel_qq_t.getText().toString())) {//qq
            qq = mEt_nuturel_qq_t.getText().toString();
        }
        if (!TextUtils.isEmpty(mEt_wetchat_t.getText().toString())) {//微信
            weixin = mEt_wetchat_t.getText().toString();
        }

        String sfzh = mEt_nuturel_icard_t.getText().toString();//身份证
        String name = mEt_debrpeople_name_t.getText().toString();//姓名
        String phone = mEt_nuturel_phone_t.getText().toString();//电话
        String address = mEt_nuturel_contact_address_t.getText().toString();//地址
        String email = mEt_nuturel_email_t.getText().toString();//邮箱


        DebtPerson debtPerson = new DebtPerson();
        debtPerson.setIdCode(sfzh);
        debtPerson.setName(name);
        debtPerson.setProvinceCode(shengId);
        debtPerson.setCityCode(shiId);
        debtPerson.setPrCode(quId);
        debtPerson.setPhoneNumber(phone);
        debtPerson.setContactAddress(address);
        debtPerson.setEmail(email);
        debtPerson.setQq(qq);
        debtPerson.setWeChat(weixin);

        AddDebtVo add = new AddDebtVo();
        add.setDebtPerson(debtPerson);
        add.setType("1");
        add.setIsOwer(isOwer);
        EventBus.getDefault().postSticky(add);
        startActivity(new Intent(getActivity(), UpdownPhotos.class).putExtra("addpic", 3));
    }


    private void jsonCitisData() {

        Gson gson = new Gson();
        ssq = gson.fromJson(ProvinceCq.InitData(), Add.class);
        int shengSize = ssq.getData().size();//省个数
        mProvinceDatas = new String[shengSize];

        String mProvinceStr;
        for (int i = 0; i < shengSize; i++) {

            mProvinceStr = ssq.getData().get(i).getValue();
            mProvinceDatas[i] = mProvinceStr;
            int shiSize = ssq.getData().get(i).getChild().size();//市个数
            mCitiesDatas = new String[shiSize];

            String mCityStr;
            for (int j = 0; j < shiSize; j++) {
                mCityStr = ssq.getData().get(i).getChild().get(j).getValue();
                mCitiesDatas[j] = mCityStr;
                int quSize = ssq.getData().get(i).getChild().get(j).getChild().size();
                mAreaDatas = new String[quSize];
                for (int k = 0; k < quSize; k++) {
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
        mProvinceSpinner_zsr.setAdapter(mProvinceAdapter);

        // 省份选择
        mProvinceSpinner_zsr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPro = "";
                selectedPro = (String) parent.getSelectedItem();
                // 根据省份更新城市区域信息
                shengId = updateCity(selectedPro);
                Log.e("--省--id--", selectedPro + "------" + shengId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // 市选择
        mCitySpinner_zsr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = "";
                selectedCity = (String) parent.getSelectedItem();
                shiId = updateArea(selectedPro, selectedCity);
                Log.e("--市--id--", selectedCity + "------" + shiId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 区选择
        mAreaSpinner_zsr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedArea = "";
                selectedArea = (String) parent.getSelectedItem();
                quId = getQuId(selectedPro, selectedCity, selectedArea);
                Log.e("--区--id--", selectedArea + "------" + quId);
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
        String[] cities = null;
        List<Add.DataBean.ChildBean> shiNameList;
        for (int i = 0; i < ssq.getData().size(); i++) {
            if (pro.equals(ssq.getData().get(i).getValue())) {
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
        mCitySpinner_zsr.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
        mCitySpinner_zsr.setSelection(0);
        return shengId;
    }

    /**
     * 根据市 选择对应的区  返回市Id
     */
    private String updateArea(String pro, String city) {

        String[] areas = null;
        String shiId = "";

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
                        if (quNameList != null && quNameList.size() != 0) {
                            areas = new String[quNameList.size()];
                            for (int k = 0; k < quNameList.size(); k++) {
                                areas[k] = quNameList.get(k).getValue();
                            }
                        } else {
                            areas = null;
                        }

                    }
                }
            }
        }
        // 存在区
        if (areas != null) {
            // 存在区
            mAreaSpinner_zsr.setVisibility(View.VISIBLE);
            mAreaAdapter = new ArrayAdapter<String>(getActivity(),R.layout.my_spinner_item, areas);
            mAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAreaSpinner_zsr.setAdapter(mAreaAdapter);
            mAreaAdapter.notifyDataSetChanged();
            mAreaSpinner_zsr.setSelection(0);
        } else {
            mAreaSpinner_zsr.setVisibility(View.GONE);
            quId = "";
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
        String quId = "";
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

}
