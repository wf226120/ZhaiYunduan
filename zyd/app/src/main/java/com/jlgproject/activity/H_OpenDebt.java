package com.jlgproject.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.AddRess.Add;
import com.jlgproject.model.Open_Debts;
import com.jlgproject.model.Open_DebtsInfo;
import com.jlgproject.model.eventbusMode.AddDebtBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.JsonUtil;
import com.jlgproject.util.L;
import com.jlgproject.util.ProvinceCq;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * 开通债行
 */
public class H_OpenDebt extends BaseActivity implements View.OnClickListener, HttpMessageInfo.IMessage {

    /**
     * 页标题
     */
    private TextView mTv_Title_op;
    private ImageView mIv_Title_left;
    private LinearLayout mLl_ivParent_title;
    private Button mEt_kh_tj;//提交
    private EditText mEt_kh_username, mEt_kh_sfzh, mEt_kh_lxdh, mEt_kh_tjr;//输入框
    private Spinner mProvinceSpinner;//省
    private Spinner mCitySpinner;//市
    private Spinner mAreaSpinner;//区
    // 列表选择的省市区
    public String selectedPro = "";
    public String selectedCity = "";
    public String selectedArea = "";
    // 省份
    public String[] mProvinceDatas;
    // 城市
    public String[] mCitiesDatas;
    // 地区
    public String[] mAreaDatas;

    public ArrayAdapter<String> mProvinceAdapter;
    public ArrayAdapter<String> mCityAdapter;
    public ArrayAdapter<String> mAreaAdapter;
    // 存储省对应的所有市 name
    private Map<String, String[]> mCitiesDataMap = new HashMap<String, String[]>();
    // 存储市对应的所有区 name
    private Map<String, String[]> mAreaDataMap = new HashMap<String, String[]>();
    //省市區Id
    private String shengId = null, shiId = null, quId = null;
    private Add ssq;//地址

    @Override
    public void initDatas() {
        super.initDatas();

    }

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_h__open_debt;
    }

    @Override
    public void initViews() {

        initSinner();
        //动态设置标题
        mTv_Title_op = (TextView) findViewById(R.id.tv_title_name);
        mTv_Title_op.setText("开通债行");
        mIv_Title_left = (ImageView) findViewById(R.id.iv_title_left);
        mIv_Title_left.setVisibility(View.VISIBLE);
        mLl_ivParent_title = (LinearLayout) findViewById(R.id.ll_ivParent_title);
        mLl_ivParent_title.setOnClickListener(this);
        mEt_kh_tj = (Button) findViewById(R.id.et_kh_tj);
        mEt_kh_tj.setOnClickListener(this);

        mEt_kh_username = (EditText) findViewById(R.id.et_kh_username);//用户名
        mEt_kh_sfzh = (EditText) findViewById(R.id.et_kh_sfzh);//身份证号
        mEt_kh_lxdh = (EditText) findViewById(R.id.et_kh_lxdh);//联系电话
        mEt_kh_tjr = (EditText) findViewById(R.id.et_kh_tjr);//推荐人

        jsonCitisData();
        initSpinner();
        ActivityCollector.startA(H_OpenDebt.this, DialogActivity.class, "dialogIndex", 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initSinner() {
        mProvinceSpinner = (Spinner) findViewById(R.id.spinner_pro);
        mCitySpinner = (Spinner) findViewById(R.id.spinner_city);
        mAreaSpinner = (Spinner) findViewById(R.id.spinner_area);
    }

    //开通债行
    private void requstOpenDebts(Open_DebtsInfo open_debtsInfo) {
        if (ShowDrawDialog(this)) {
            String string = JsonUtil.toJson(open_debtsInfo);
            L.e("----开通债行---" + string);
            RequestBody body = RequestBody.create(ConstUtils.JSON, string);
            HttpMessageInfo<Open_Debts> info = new HttpMessageInfo<>(BaseUrl.OPEN_DEBTS, new Open_Debts());
            info.setiMessage(this);
            info.setFormBody(body);
            AddHeaders header = new AddHeaders();
            header.add("Authorization", UserInfoState.getToken());
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_POST, info, null, header, 1);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_ivParent_title) {
            finish();
        } else if (id == R.id.et_kh_tj) {
            commit();
        }
    }

    public void commit() {
        if (TextUtils.isEmpty(mEt_kh_username.getText().toString())) {
            toastUtil("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(mEt_kh_sfzh.getText().toString())) {
            toastUtil("请输入身份证号");
            return;
        }


        if (TextUtils.isEmpty(mEt_kh_lxdh.getText().toString())) {
            toastUtil("请输入联系电话");
            return;
        }

        if (TextUtils.isEmpty(shengId)) {
            toastUtil("请选择省");
            return;
        }
        if (TextUtils.isEmpty(shiId)) {
            toastUtil("请选择市");
            return;
        }

        String code = null;
        if (!TextUtils.isEmpty(mEt_kh_tjr.getText().toString())) {
            if (mEt_kh_tjr.getText().length() == 11) {
                code = mEt_kh_tjr.getText().toString();
            } else {
                ToastUtil.showShort(this, "请正确输入手机号码");
                return;
            }
        }

        String name = mEt_kh_username.getText().toString();

        String phoneNumber = mEt_kh_lxdh.getText().toString();
        String proId = shengId;
        String cId = shiId;
        String qId = quId;
        String icard = mEt_kh_sfzh.getText().toString();
        Open_DebtsInfo open_debtsInfo = new Open_DebtsInfo(name, icard, phoneNumber, proId, cId, qId, code);
        requstOpenDebts(open_debtsInfo);
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
        mProvinceAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, mProvinceDatas);
        //设置下拉时的布局
        mProvinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProvinceSpinner.setAdapter(mProvinceAdapter);

        // 省份选择
        mProvinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        mCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        mAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        String shengId = "";
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
        mCityAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, cities);
        mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCitySpinner.setAdapter(mCityAdapter);
        mCityAdapter.notifyDataSetChanged();
        mCitySpinner.setSelection(0);
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
            mAreaSpinner.setVisibility(View.VISIBLE);
            mAreaAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, areas);
            mAreaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mAreaSpinner.setAdapter(mAreaAdapter);
            mAreaAdapter.notifyDataSetChanged();
            mAreaSpinner.setSelection(0);
        } else {
            L.e("-------隱藏-------");
            mAreaSpinner.setVisibility(View.GONE);
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

    private void toastUtil(String string) {
        ToastUtil.showShort(H_OpenDebt.this, string);
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Open_Debts) {  //开通债行
            final Open_Debts open_debts = (Open_Debts) o;
            if (open_debts != null) {
                DismissDialog();
                if (open_debts.getState().equals("ok")) {
                    if (open_debts.getMessage().equals("已录入信息，请去支付") || open_debts.getMessage().equals("已录入过信息，未支付，请支付")) { //开通债行
                        DialogUtils.showConfirmDialog(this, open_debts.getMessage(), "确认", true, new DialogUtils.OnButtonEventListenerConfirm() {
                            @Override
                            public void onConfirm() {
                                String orderId = open_debts.getData().getOpenOrderId().toString();
                                String money = open_debts.getData().getPayAmount().toString();
                                AddDebtBean addDebtBean = new AddDebtBean(orderId, money, 1);
                                EventBus.getDefault().postSticky(addDebtBean);
                                ActivityCollector.startA(H_OpenDebt.this, ToPay.class);
                            }
                        });
                    }
                } else  {//添加失败
                    ToastUtil.showShort(this, open_debts.getMessage());
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.showShort(this, "服务器繁忙请稍后再试");
    }
}
