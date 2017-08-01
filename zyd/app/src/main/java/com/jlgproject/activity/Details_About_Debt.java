package com.jlgproject.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.jlgproject.R;
import com.jlgproject.adapter.DebtsDetailsAdpapter;
import com.jlgproject.adapter.Debts_About_Debt_Plist_Adapter;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.http.AddHeaders;
import com.jlgproject.http.BaseUrl;
import com.jlgproject.http.GetParmars;
import com.jlgproject.http.HttpMessageInfo;
import com.jlgproject.http.OkHttpUtils;
import com.jlgproject.model.Debts_Maner_Details;
import com.jlgproject.model.eventbusMode.AddDebtBean;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;
import com.jlgproject.util.UserInfoState;
import com.jlgproject.view.CustomGridView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by sunbeibei on 2017/5/5.
 * 债事详情。
 */

public class Details_About_Debt extends BaseActivity implements HttpMessageInfo.IMessage {

    private ImageView iv_title;
    private TextView tv_title_name;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private View line2;
    private View line3;
    private TextView payfei;
    private Long id;
    private String orderId;
    private TextView get_time;
    private TextView debt_progress;
    private TextView get_creditors_number;
    private TextView get_creditors_name;
    private TextView get_debtor_number;
    private TextView get_debtor_name;
    private TextView get_debt_type;
    private TextView get_debt_nature;
    private TextView get_is_suit;
    private TextView get_main_money;
    private TextView get_debt_time;
    private TextView get_note;
    private TextView get_is_evidence;
    private TextView get_is_material;
    private TextView get_is_transfer;
    private TextView get_is_legal_document;
    private CustomGridView get_is_icard;
    private CustomGridView get_paper;
    private CustomGridView get_electronic_data;
    private CustomGridView gridview;
    private List<String> identification;
    private List<String> evidence;
    private List<String> electron;
    private List<String> picList;
    private LinearLayout pay_line;
    private ImageView iv_xsdt;
    private String qianshu;
    private String orderId2;
    private int state;//判断是1.0 还是2.0 图片
    //图片数据集合
    private List<String> srcList = new ArrayList<>();

    @Override
    public int loadWindowLayout() {
        return R.layout.details_about_debt;
    }

    @Override
    public void initDatas() {
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 8);
        orderId = intent.getStringExtra("orderId");
        Log.e("------债事Id-------", id + "" + "---orderId" + orderId);
        requestDetail();
    }

    private void progressColor2() {
        image2.setImageResource(R.mipmap.red);
        line2.setBackground(getResources().getDrawable(R.color.red));
    }

    private void progressColor3() {
        image2.setImageResource(R.mipmap.red);
        image3.setImageResource(R.mipmap.red);
        line2.setBackground(getResources().getDrawable(R.color.red));
        line3.setBackground(getResources().getDrawable(R.color.red));
    }


    private void progressColor() {
        image2.setImageResource(R.mipmap.red);
        image3.setImageResource(R.mipmap.red);
        image4.setImageResource(R.mipmap.red);
        line2.setBackground(getResources().getDrawable(R.color.red));
        line3.setBackground(getResources().getDrawable(R.color.red));
    }

    @Override
    public void initViews() {
        iv_title = (ImageView) findViewById(R.id.iv_title_left);
        iv_title.setImageResource(R.drawable.back3);
        iv_title.setVisibility(View.VISIBLE);
        payfei = (TextView) findViewById(R.id.payfei);
        payfei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderId != null) {
                    AddDebtBean addDebtBean = new AddDebtBean(orderId2, qianshu, 0);
                    EventBus.getDefault().postSticky(addDebtBean);
                    ActivityCollector.startA(Details_About_Debt.this, ToPay.class);
                } else {
                    ToastUtil.showShort(Details_About_Debt.this, "支付异常");
                }

            }
        });
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        line2 = findViewById(R.id.line2);
        line3 = findViewById(R.id.line3);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("债事详情");
        iv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pay_line = (LinearLayout) findViewById(R.id.pay_line);
        //录入时间
        get_time = (TextView) findViewById(R.id.get_time);
        //债事进展
        debt_progress = (TextView) findViewById(R.id.debt_progress);
        //债权人证号
        get_creditors_number = (TextView) findViewById(R.id.get_creditors_number);
        //债权人名称
        get_creditors_name = (TextView) findViewById(R.id.get_creditors_name);
        //债务人证号
        get_debtor_number = (TextView) findViewById(R.id.get_debtor_number);
        //债务人名称
        get_debtor_name = (TextView) findViewById(R.id.get_debtor_name);
        //债事类型
        get_debt_type = (TextView) findViewById(R.id.get_debt_type);
        //债事性质
        get_debt_nature = (TextView) findViewById(R.id.get_debt_nature);
        //是否诉讼
        get_is_suit = (TextView) findViewById(R.id.get_is_suit);
        //主体金额
        get_main_money = (TextView) findViewById(R.id.get_main_money);
        //债事发生时间
        get_debt_time = (TextView) findViewById(R.id.get_debt_time);
        //备注
        get_note = (TextView) findViewById(R.id.get_note);
        //	是否有债务人偿还欠款及利息的证据 0无 1 有
        get_is_evidence = (TextView) findViewById(R.id.get_is_evidence);
        //是否有担保或者抵押的证明材料 0无 1 有
        get_is_material = (TextView) findViewById(R.id.get_is_material);
        //是否有债务转让证明材料 0无 1有
        get_is_transfer = (TextView) findViewById(R.id.get_is_transfer);
        //是否曾涉及诉讼、公证及支付令等法律文书 0 无1 有
        get_is_legal_document = (TextView) findViewById(R.id.get_is_legal_document);
        //身份证明 1 营业执照副本复印件 2组织机构代码证复印件 3法人身份证复印件 多选，相隔
        get_is_icard = (CustomGridView) findViewById(R.id.get_is_Icard);
        //票据证明
        get_paper = (CustomGridView) findViewById(R.id.get_paper);
        //电子数据
        get_electronic_data = (CustomGridView) findViewById(R.id.get_electronic_data);
        //图片路径
        gridview = (CustomGridView) findViewById(R.id.gridview);
        iv_xsdt = (ImageView) findViewById(R.id.iv_xsdt);
        iv_xsdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_xsdt.setVisibility(View.GONE);
            }
        });
    }

    private void requestDetail() {
        if (ShowDrawDialog(this)) {
            GetParmars parmars = new GetParmars();
            parmars.add("relationId", id + "");
            AddHeaders headers = new AddHeaders();
            headers.add("Authorization", UserInfoState.getToken());
            HttpMessageInfo<Debts_Maner_Details> info = new HttpMessageInfo<>(BaseUrl.DEBTS_MANGER_DETAIL, new Debts_Maner_Details());
            info.setiMessage(this);
            OkHttpUtils.getInstance().getServiceMassage(OkHttpUtils.TYPE_GET, info, parmars, headers, 1);
        }
    }

    @Override
    public void getServiceData(Object o) {
        if (o instanceof Debts_Maner_Details) {
            final Debts_Maner_Details data = (Debts_Maner_Details) o;
            if (data != null) {
                if (data.getState().equals("ok")) {
                    DismissDialog();
                    String createTime = data.getData().getCreateTime();//录入时间
                    int workflowStatus = data.getData().getWorkflowStatus();//债事进展
                    String idCode = data.getData().getTo().getIdCode();//债权人证号
                    String name = data.getData().getTo().getName();//债权人名称
                    String idCode1 = data.getData().getFrom().getIdCode();//债务人证号
                    String name1 = data.getData().getFrom().getName();//z债务人名称
                    int genre = data.getData().getGenre();//债事类型1货币2非货币
                    int natureOf = data.getData().getNatureOf();//债事性质1个人2企业
                    int isLawsuit = data.getData().getIsLawsuit();//是否诉讼0否1是
                    Long amout = data.getData().getAmout();//主体金额
                    String recordTime = data.getData().getRecordTime();//债事发生时间
                    String remark = data.getData().getRemark();//备注
                    int proof = data.getData().getProof();//	是否有债务人偿还欠款及利息的证据 0无 1 有
                    int mortgagee = data.getData().getMortgagee();//是否有担保或者抵押的证明材料 0无 1 有
                    int attorn = data.getData().getAttorn();//是否有债务转让证明材料 0无 1有
                    int isSolution = data.getData().getIsSolution();//是否曾涉及诉讼、公证及支付令等法律文书 0 无1 有
                    //身份证明 1 营业执照副本复印件 2组织机构代码证复印件 3法人身份证复印件 多选，相隔
                    identification = data.getData().getIdentification();
                    //票据证明 1合同，2收据 3借据4欠据 5协议6信件7电报8提货单9仓单发票10其他
                    evidence = data.getData().getEvidence();
                    //电子数据 1 微信 2 QQ3 MSN 4微博5其他
                    electron = data.getData().getElectron();
                    int payStatus = data.getData().getPayStatus();//支付状态 0未支付 1已支付

                    if (payStatus == 0) {
                        pay_line.setVisibility(View.VISIBLE);
                    } else if (payStatus == 1) {
                        pay_line.setVisibility(View.GONE);
                    }
                    qianshu = data.getData().getQianshu();
                    orderId2 = data.getData().getOrderId();

                    //图片地址
                    picList = data.getData().getPicList();
                    get_time.setText(createTime);
                    if (workflowStatus == 0) {
                        debt_progress.setText("已备案");
                    } else if (workflowStatus == 1) {
                        debt_progress.setText("统筹中");
                        progressColor2();
                    } else if (workflowStatus == 2) {
                        debt_progress.setText("解债中");
                        progressColor3();
                    } else {
                        debt_progress.setText("已解债");
                        progressColor();
                    }
                    get_creditors_number.setText(idCode);
                    get_creditors_name.setText(name);
                    get_debtor_number.setText(idCode1);
                    get_debtor_name.setText(name1);
                    if (genre == 1) {
                        get_debt_type.setText("货币");
                    } else {
                        get_debt_type.setText("非货币");
                    }
                    if (natureOf == 1) {
                        get_debt_nature.setText("个人");
                    } else {
                        get_debt_nature.setText("企业");
                    }
                    if (isLawsuit == 1) {
                        get_is_suit.setText("是");
                    } else {
                        get_is_suit.setText("否");
                    }
                    get_main_money.setText(amout + "");
                    get_debt_time.setText(recordTime);
                    get_note.setText(remark);
                    if (proof == 0) {
                        get_is_evidence.setText("无");
                    } else {
                        get_is_evidence.setText("有");
                    }
                    if (mortgagee == 0) {
                        get_is_material.setText("无");
                    } else {
                        get_is_material.setText("有");
                    }
                    if (attorn == 0) {
                        get_is_transfer.setText("无");
                    } else {
                        get_is_transfer.setText("有");
                    }
                    if (isSolution == 0) {
                        get_is_legal_document.setText("无");
                    } else {
                        get_is_legal_document.setText("有");
                    }
                    get_is_icard.setAdapter(new DebtsDetailsAdpapter(1, identification, this));
                    get_paper.setAdapter(new DebtsDetailsAdpapter(2, evidence, this));
                    get_electronic_data.setAdapter(new DebtsDetailsAdpapter(3, electron, this));

                    //兼容1.0图片数据
                    List<String> picList = data.getData().getPicList();

                    List<String> srcList2 = new ArrayList<>();

                    if (picList.size() > 12) {
                        StringBuffer str = new StringBuffer();
                        for (int i = 0; i < picList.size(); i++) {
                            String strC = picList.get(i);
                            str.append(strC);
                        }
                        String string = new String(str);
                        String[] image = string.split(",");
                        for (int j = 0; j < image.length; j++) {
                            L.e("----图片字符串----" + image[j]);
                            srcList2.add(image[j]);
                        }
                        state = 1;
                        srcList = srcList2;
                    } else {//2.0图片
                        state = 2;
                        srcList = picList;
                    }
//                electron":["1","2","3"],"evidence":["2","3","4","9"],"identification":["1","3"]
                    //图片gridView
                    gridview.setAdapter(new Debts_About_Debt_Plist_Adapter(this, srcList));
                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            iv_xsdt.setVisibility(View.VISIBLE);
                            if (state == 1) {
                                Glide.with(Details_About_Debt.this).load(srcList.get(position)).priority(Priority.HIGH).centerCrop().fitCenter().into(iv_xsdt);
                            } else if (state == 2) {
                                Glide.with(Details_About_Debt.this).load(data.getData().getPicList().get(position)).priority(Priority.HIGH).centerCrop().fitCenter().into(iv_xsdt);
                            }
                        }
                    });
                } else {
                    ToastUtil.showShort(this, data.getMessage());
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
