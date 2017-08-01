package com.jlgproject.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jlgproject.MainActivity;
import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.DebtRelation1Vo;
import com.jlgproject.model.Debt_Query;
import com.jlgproject.model.UserType;
import com.jlgproject.model.eventbusMode.IntentDebtPresonBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.ConstUtils;
import com.jlgproject.util.DialogUtils;
import com.jlgproject.util.L;
import com.jlgproject.util.NetUtils;
import com.jlgproject.util.SharedUtil;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/2.
 * 点击新增债事。（债事备案）
 */

public class New_Debt_Matter extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, HttpMessageInfo.IMessage {

    private TextView title_name;
    private ImageView iv_title_left;
    private Button button;
    private Button query1;
    private Button query2;
    private int type = 9;
    private LinearLayout rl_debt_zqrzh;//债权人证号
    private LinearLayout ll_debt_matter_zqrmc;//债权人名称
    private RadioGroup rg_debt_xz;//债事性质
    private RadioGroup rg_debt_qiye;//企业性质
    private RadioGroup rg_debt_preson_type;//债事人类型
    private RadioGroup rg_debt_type;//债事类型
    private RadioGroup rg_ss;//诉讼情况
    private EditText et_debtpreson_number;
    private EditText et_creditors_number;
    private EditText et_main_amount;
    private EditText et_referees;
    private TextView select_time;
    private TextView text1, tv_zsr_name;
    private int year;
    private int month;
    private int day;
    private View view;
    private LinearLayout debts_types;
    private Debt_Query quryInformation;
    private TextView debtor_name, creditors_name;

    private String isDebt = "0";//债事人类型
    private String debtPresonType = "1";//债事类型
    private String isLawsuit = "0";//是否诉讼
    private String time;//债事发生时间
    private String natureOf = "1";//债事性质
    private String nature = "2";//企业性质
    private String isHz;
    private int index;
    private String zwr;
    private String zqr;
    String zwrzh = null;//债务人证件号
    String zqrzh = null;//债权人证件号
    String tjr = null;//推荐人手机号

    @Override
    public int loadWindowLayout() {
        return R.layout.new_debt_matter;
    }

    @Override
    public void initViews() {
        initVews();

        Intent intent = new Intent(New_Debt_Matter.this, DialogActivity.class);
        intent.putExtra("dialogIndex", 1);
        startActivity(intent);

    }

    public void initVews() {
        EventBus.getDefault().register(this);
        title_name = (TextView) findViewById(R.id.tv_title_name);
        title_name.setText("债事信息");
        iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_title_left.setImageResource(R.drawable.back3);
        iv_title_left.setVisibility(View.VISIBLE);
        iv_title_left.setOnClickListener(this);
        debts_types = (LinearLayout) findViewById(R.id.debt_types);

        text1 = (TextView) findViewById(R.id.text1);
        tv_zsr_name = (TextView) findViewById(R.id.tv_zsr_name);
        button = (Button) findViewById(R.id.next);
        button.setOnClickListener(this);
        query1 = (Button) findViewById(R.id.query1);
        query1.setOnClickListener(this);
        debtor_name = (TextView) findViewById(R.id.debtor_name);//债务人名称
        creditors_name = (TextView) findViewById(R.id.creditors_name);//债权人名称
        query2 = (Button) findViewById(R.id.query2);
        query2.setOnClickListener(this);
        select_time = (TextView) findViewById(R.id.select_time);
        select_time.setOnClickListener(this);
        rl_debt_zqrzh = (LinearLayout) findViewById(R.id.rl_debt_zqrzh);
        rl_debt_zqrzh = (LinearLayout) findViewById(R.id.rl_debt_zqrzh);

        rl_debt_zqrzh = (LinearLayout) findViewById(R.id.rl_debt_zqrzh);
        ll_debt_matter_zqrmc = (LinearLayout) findViewById(R.id.ll_debt_matter_zqrmc);
        rg_debt_xz = (RadioGroup) findViewById(R.id.rg_debt_xz);
        rg_debt_xz.setOnCheckedChangeListener(this);
        rg_debt_qiye = (RadioGroup) findViewById(R.id.rg_debt_qiye);
        rg_debt_qiye.setOnCheckedChangeListener(this);
        rg_debt_preson_type = (RadioGroup) findViewById(R.id.rg_debt_preson_type);
        rg_debt_preson_type.setOnCheckedChangeListener(this);
        rg_debt_type = (RadioGroup) findViewById(R.id.rg_debt_type);
        rg_debt_type.setOnCheckedChangeListener(this);
        rg_ss = (RadioGroup) findViewById(R.id.rg_ss);
        rg_ss.setOnCheckedChangeListener(this);
        et_debtpreson_number = (EditText) findViewById(R.id.et_debtpreson_number);//债务人证号
        et_creditors_number = (EditText) findViewById(R.id.et_creditors_number);//债权人证号
        et_main_amount = (EditText) findViewById(R.id.et_main_amount);//主体金额
        et_referees = (EditText) findViewById(R.id.et_referees);

        if(UserInfoState.getUserType()==1){
            if(UserInfoState.getUserPhone()!=null){
                et_referees.setText(UserInfoState.getUserPhone());
                et_referees.setEnabled(false);
                tjr=UserInfoState.getUserPhone();
            }
        }
    }

    private void showDirlog() {
        view = LayoutInflater.from(this).inflate(R.layout.select_time, null);
        final AlertDialog builder = new AlertDialog.Builder(this).create();
        builder.setView(view);
        builder.show();
        //得到当前显示设备的宽度，单位是像素
        int width = getWindowManager().getDefaultDisplay().getWidth();
        //得到这个dialog界面的参数对象
        WindowManager.LayoutParams params = builder.getWindow().getAttributes();
        //设置dialog的界面宽度
        params.width = width - (width / 6);
        //设置dialog高度为包裹内容
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的重心
        params.gravity = Gravity.CENTER;
        //dialog.getWindow().setLayout(width-(width/6), LayoutParams.WRAP_CONTENT);
        //用这个方法设置dialog大小也可以，但是这个方法不能设置重心之类的参数，推荐用Attributes设置
        //最后把这个参数对象设置进去，即与dialog绑定
        builder.getWindow().setAttributes(params);
        Button button = (Button) view.findViewById(R.id.confirm);
        DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        // 初始化DatePicker组件，初始化时指定监听器

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                New_Debt_Matter.this.year = year;
                New_Debt_Matter.this.month = month;
                New_Debt_Matter.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });


    }

    private void showDate(int year, int month, int day) {
        time = year + "年" + (month + 1) + "月" + day + "日";
        select_time.setText(time);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group == rg_debt_xz) {//债事性质
            if (checkedId == R.id.single_preson) {//个人
                rg_debt_qiye.setVisibility(View.GONE);
                natureOf = "1";
            } else if (checkedId == R.id.enterprise) {//企业
                rg_debt_qiye.setVisibility(View.VISIBLE);
                natureOf = "2";
            }
        } else if (group == rg_debt_qiye) {//企业性质
            if (checkedId == R.id.rb_debt_gq) {//国企
                nature = "1";
            } else if (checkedId == R.id.rb_debt_sq) {//私企
                nature = "2";
            }
        } else if (group == rg_debt_preson_type) {//债事人类型
            if (checkedId == R.id.rb_zqr) {//债权人
                isDebt = "0";
            } else if (checkedId == R.id.rb_zwr) {//债务人
                isDebt = "1";
            }
        } else if (group == rg_debt_type) {//债事类型
            if (checkedId == R.id.currency) {//货币
                debtPresonType = "1";
            } else if (checkedId == R.id.no_currency) {//非货币
                debtPresonType = "2";
            }
        } else if (group == rg_ss) {
            if (checkedId == R.id.not_suit) {//未诉讼
                isLawsuit = "0";
            } else if (checkedId == R.id.have_action) {//已诉讼
                isLawsuit = "1";
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.query1:
                if (type == 1) {
                    if (!TextUtils.isEmpty(et_debtpreson_number.getText().toString())) {
                        zwr = et_debtpreson_number.getText().toString();
                        if (!TextUtils.isEmpty(et_creditors_number.getText().toString())) {
                            zqr = et_creditors_number.getText().toString();
                            if (zwr.equals(zqr)) {
                                ToastUtil.showShort(this, "债务人与债权人不能是同一个人");
                                return;
                            }
                        }
                        index = 1;
                        getHttpZjh(zwr);
                    } else {
                        ToastUtil.showShort(this, "请输入债务人证件号");
                    }
                } else {
                    if (!TextUtils.isEmpty(et_debtpreson_number.getText().toString())) {
                        zwr = et_debtpreson_number.getText().toString();
                        index = 1;
                        getHttpZjh(zwr);
                        L.e("---证号---" + zwr);
                    } else {
                        ToastUtil.showShort(this, "请输入债事人证件号");
                    }
                }

                break;
            case R.id.query2:
                if (!TextUtils.isEmpty(et_creditors_number.getText().toString())) {
                    zqr = et_creditors_number.getText().toString();
                    zwr = et_debtpreson_number.getText().toString();
                    if (zqr.equals(zwr)) {
                        ToastUtil.showShort(this, "债权人与债务人不能是同一个人");
                    } else {
                        getHttpZjh(zqr);
                        index = 2;
                    }
                } else {
                    ToastUtil.showShort(this, "请输入债权人证件号");
                }
                break;
            case R.id.next://下一步
                next();
                break;
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.select_time:
                showDirlog();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedUtil.getSharedUtil().getInt(this, ConstUtils.CANCEL_AGREEMENT)==1) {
            finish();
        }


        type = UserInfoState.getUserType();
        if (type != 0) {
            L.e("-----type有缓存----");
            if (type == 2 || type == 3) {//是VIP || 普通用户
                L.e("-----非行长----");
                rl_debt_zqrzh.setVisibility(View.GONE);//债权人证号
                ll_debt_matter_zqrmc.setVisibility(View.GONE);//债权人名称
                isHz = "0";
                text1.setText("债事人证号");
                tv_zsr_name.setText("债事人姓名");
            } else if (type == 1) {//行长
                L.e("-----行长----");
                debts_types.setVisibility(View.GONE);
                isHz = "1";
            }
        }
    }

    private void next() {

        if (type == 1 || type == 2 || type == 3) {
            if (type == 1) {//行长提示
                if (TextUtils.isEmpty(et_debtpreson_number.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债务人证件号");
                    return;
                }
                if (TextUtils.isEmpty(debtor_name.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债务人名称");
                    return;
                }

                if (TextUtils.isEmpty(et_creditors_number.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债权人证件号");
                    return;
                }
                if (TextUtils.isEmpty(creditors_name.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债权人名称");
                    return;
                }
            } else {//非行长提示
                if (TextUtils.isEmpty(et_debtpreson_number.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债事人证件号");
                    return;
                }
                if (TextUtils.isEmpty(debtor_name.getText().toString())) {
                    ToastUtil.showShort(this, "请输入债事人名称");
                    return;
                }
            }

        }

        if (TextUtils.isEmpty(et_main_amount.getText().toString().toString())) {
            ToastUtil.showShort(this, "请输入主体金额");
            return;
        }
        String jine = et_main_amount.getText().toString();
        long l = Long.parseLong(jine);
        if (l == 0) {
            ToastUtil.showShort(this, "主体金额不能是0");
            return;
        }

        if (TextUtils.isEmpty(time)) {
            ToastUtil.showShort(this, "请输入债事发生时间");
            return;
        }
        if(type!=1){ //非行长的时候
            if (!TextUtils.isEmpty(et_referees.getText().toString().toString())) {
                if (et_referees.getText().length() == 11) {
                    tjr = et_referees.getText().toString().toString();
                } else {
                    ToastUtil.showShort(this, "请正确输入手机号");
                    return;
                }
            }
        }

        String ztje = et_main_amount.getText().toString().toString();//主体金额
        DebtRelation1Vo debt = new DebtRelation1Vo();
        debt.setIsPresident(isHz);//是否行长
        if (isHz.equals("1")) {//是行长
            debt.setStartId(zwrzh);//债务人
            debt.setEndId(zqrzh);//债权人
            isDebt = "";
        } else {
            if (isDebt.equals("0")) {
                L.e("-----债权人-----" + zqrzh);
                debt.setStartId("");
                debt.setEndId(zwrzh);
            } else if (isDebt.equals("1")) {
                debt.setStartId(zwrzh);
                debt.setEndId("");
                L.e("-----债务人-----" + zqrzh);
            }
        }
        debt.setIsDebt(isDebt);
        debt.setAmout(ztje);
        debt.setNatureOf(natureOf);
        debt.setNature(nature);
        debt.setGenre(debtPresonType);
        debt.setIsLawsuit(isLawsuit);
        debt.setRecordTime(time);
        debt.setRecommend(tjr);
        EventBus.getDefault().postSticky(debt);//发布粘性事件
        ActivityCollector.startA(New_Debt_Matter.this, New_Debt_Matter_Next.class);
    }

    //获取证件号
    public void getHttpZjh(String zh) {
        if (ShowDrawDialog(this)) {
            HttpMessageInfo<Debt_Query> info = new HttpMessageInfo<>(BaseUrl.QUERY, new Debt_Query());
            info.setiMessage(this);
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            GetParmars parmars = new GetParmars();
            parmars.add("wd", zh);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }


    @Override
    public void getServiceData(Object o) {
        if (o instanceof Debt_Query) {
            quryInformation = (Debt_Query) o;
            if (quryInformation != null) {
                DismissDialog();
                if (quryInformation.getState().equals("ok")) {
                    if (index == 1) {//债务人
                        debtor_name.setText(quryInformation.getData().getName());
                        zwrzh = quryInformation.getData().getId();//用户Id
                    } else if (index == 2) {//债权人
                        creditors_name.setText(quryInformation.getData().getName());
                        zqrzh = quryInformation.getData().getId();//债权人id
                    }
                } else if (quryInformation.getState().equals("warn") && quryInformation.getMessage().equals("该证号不存在")) {
                    DialogUtils.showDialogZsr(this, true, "系统内未找到该证号的债事人信息，请到债事人页创建", new DialogUtils.OnButtonEventListener() {
                        @Override
                        public void onConfirm() {
                            //这一步表示登录用户不是自然人  跳转到新增债事人页面
                            ActivityCollector.startA(New_Debt_Matter.this, New_Debt_Matter_Preson.class, ConstUtils.IS_OWER, 2);
                            EventBus.getDefault().postSticky(new IntentDebtPresonBean(1));
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
                } else if (quryInformation.getState().equals("error")) {
                    ToastUtil.showShort(this, "系统异常");
                }
            }

        } else if (o instanceof UserType) {
            UserType userType = (UserType) o;
            if (userType != null) {
                DismissDialog();
                if (userType.getState().equals("ok")) {
                    UserInfoState.setUserPhone(userType.getData().getPhoneNumber());
                    UserInfoState.setUserType(userType.getData().getUserType());
                    int type = UserInfoState.getUserType();
                    if (type != 0) {
                        L.e("----type 已存入缓存------");
                        if (type == 2 || type == 3) {//是VIP || 普通用户
                            rl_debt_zqrzh.setVisibility(View.GONE);//债权人证号
                            ll_debt_matter_zqrmc.setVisibility(View.GONE);//债权人名称
                            isHz = "0";
                            text1.setText("债事人证号");
                            tv_zsr_name.setText("债事人姓名");
                        } else if (type == 1) {//行长
                            debts_types.setVisibility(View.GONE);
                            isHz = "1";
                        }
                    } else {
                        L.e("----type 未存入缓存------");
                    }
                }
            }
        }
    }

    @Override
    public void getErrorData(Call call, IOException e) {
        DismissDialog();
        ToastUtil.show(this, "系统繁忙请稍后再试", Toast.LENGTH_SHORT);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDebePager1(DebtRelation1Vo debt1) {
        L.e("---debtRelation1111111Vo--" + debt1.getNature());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if(SharedUtil.getSharedUtil().getInt(this, ConstUtils.CANCEL_AGREEMENT)!=0){
            SharedUtil.getSharedUtil().remove(this,ConstUtils.CANCEL_AGREEMENT);
        }
    }
}

