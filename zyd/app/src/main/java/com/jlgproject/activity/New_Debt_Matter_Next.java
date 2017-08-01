package com.jlgproject.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.model.DebtRelation1Vo;
import com.jlgproject.model.DebtRelation2Vo;
import com.jlgproject.util.ActivityCollector;
import com.jlgproject.util.L;
import com.jlgproject.view.flowTag.FlowTagLayout;
import com.jlgproject.view.flowTag.OnTagSelectListener;
import com.jlgproject.view.flowTag.TagAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunbeibei on 2017/5/3.
 * 新增债事下一步
 */

public class New_Debt_Matter_Next extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private ImageView iv_left;
    private TextView tv_title_name;
    private Button next;
    private RadioGroup group1;
    private RadioGroup group2;
    private RadioGroup group3;
    private RadioGroup group4;
    private String chzm = "1";//证明
    private String dbcl = "1";//担保
    private String zrcl = "1";//债务转让
    private String ws = "1";//文书
    private String yyzz = "0";//营业执照
    private String zzjgdm = "0";//组织机构代码
    private String frsfz = "0";//法人身份证

    private String wx = "0";//微信
    private String qQ = "0";//qq
    private String ms = "0";//msn
    private String wb = "0";//微博
    private String dzqt = "0";//电子数据其他

    private List<String> identification = null;//身份证明
    private List<String> evidence = null;//票据证明
    private List<String> electron = null;//电子数据
    private DebtRelation1Vo debt1 = null;
    //身份证明
    private FlowTagLayout sfzm;
    private TagAdapter<String> mSfzmAdapter = null;
    private List<Integer> sfzmList = null;
    //票据证明
    private FlowTagLayout pjzm;
    private TagAdapter<String> mPjzmAdapter = null;
    private List<Integer> pjzmList = null;
    //电子数据
    private FlowTagLayout dzsj;
    private TagAdapter<String> mDzsjAdapter = null;
    private List<Integer> dzsjList = null;


    @Override
    public int loadWindowLayout() {
        return R.layout.new_debt_matter_next;
    }

    @Override
    public void initViews() {
        super.initViews();
        iv_left = (ImageView) findViewById(R.id.iv_title_left);
        iv_left.setImageResource(R.drawable.back3);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_name.setText("债事信息");
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        group1 = (RadioGroup) findViewById(R.id.group1);
        group2 = (RadioGroup) findViewById(R.id.group2);
        group3 = (RadioGroup) findViewById(R.id.group3);
        group4 = (RadioGroup) findViewById(R.id.group4);
        group1.setOnCheckedChangeListener(this);
        group2.setOnCheckedChangeListener(this);
        group3.setOnCheckedChangeListener(this);
        group4.setOnCheckedChangeListener(this);
        identification = new ArrayList<>();
        evidence = new ArrayList<>();
        electron = new ArrayList<>();
        sfzm();
        pjzm();
        dzsj();

    }


    //身份证明 标签
    private void sfzm() {
        sfzm = (FlowTagLayout) findViewById(R.id.mobile_flow_layout1);
        //移动研发标签
        mSfzmAdapter = new TagAdapter<>(this);
        sfzm.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        sfzm.setAdapter(mSfzmAdapter);
        sfzm.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    sfzmList = new ArrayList<Integer>();
                    sfzmList = selectedList;
                    for (int i : selectedList) {
                        String vul = (String) parent.getAdapter().getItem(i);
                        if (vul.equals("营业执照副本复印件")) {
                            L.e("----------", "营业执照副本复印件");
                        }
                    }
                }
            }
        });
        initSfzmData();
    }

    //票据证明 标签
    private void pjzm() {
        pjzm = (FlowTagLayout) findViewById(R.id.mobile_flow_layout2);
        //移动研发标签
        mPjzmAdapter = new TagAdapter<>(this);
        pjzm.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        pjzm.setAdapter(mPjzmAdapter);
        pjzm.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    pjzmList = new ArrayList<Integer>();
                    pjzmList = selectedList;
                    for (int i : selectedList) {
                        String vul = (String) parent.getAdapter().getItem(i);
                        if (vul.equals("合同")) {
                            L.e("----------", "合同");
                        }
                    }
                }
            }
        });
        initPjzmData();
    }

    //电子数据
    private void dzsj() {
        dzsj = (FlowTagLayout) findViewById(R.id.mobile_flow_layout3);
        //移动研发标签
        mDzsjAdapter = new TagAdapter<>(this);
        dzsj.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_MULTI);
        dzsj.setAdapter(mDzsjAdapter);
        dzsj.setOnTagSelectListener(new OnTagSelectListener() {
            @Override
            public void onItemSelect(FlowTagLayout parent, List<Integer> selectedList) {
                if (selectedList != null && selectedList.size() > 0) {
                    dzsjList = new ArrayList<Integer>();
                    dzsjList = selectedList;
                    for (int i : selectedList) {
                        String vul = (String) parent.getAdapter().getItem(i);
                        if (vul.equals("QQ")) {
                            L.e("-----电子数据-----", "QQ");
                        }
                    }
                }
            }
        });
        initDzsjData();
    }


    @Override
    public void onClick(View v) {
        if (v == iv_left) {
            finish();
        } else if (v == next) {
            next();
        }

    }

    public void next() {
        DebtRelation2Vo debt2 = new DebtRelation2Vo();
        debt2.setProof(chzm);
        debt2.setMortgagee(dbcl);
        debt2.setAttorn(zrcl);
        debt2.setLawsuit(ws);
        confirmSfzm();
        debt2.setIdentification(identification);
        confirmPjzm();
        debt2.setEvidence(evidence);
        confirmDjsj();
        debt2.setElectron(electron);
        EventBus.getDefault().postSticky(debt2);
        ActivityCollector.startA(this, UpdownPhotos.class, "addpic", 0);
    }


    //身份数据源
    private void initSfzmData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("营业执照副本复印件");
        dataSource.add("组织机构代码证复印件");
        dataSource.add("法人身份证复印件");
        mSfzmAdapter.onlyAddAll(dataSource);
    }

    //身份证明确认数据
    private void confirmSfzm() {
        if (sfzmList != null && sfzmList.size() > 0) {
            for (int i : sfzmList) {
                String vul = (String) sfzm.getAdapter().getItem(i);
                if (vul.equals("营业执照副本复印件")) {
                    identification.add("1");
                }
                if (vul.equals("组织机构代码证复印件")) {
                    identification.add("2");
                }
                if (vul.equals("法人身份证复印件")) {
                    identification.add("3");
                }
            }
            for (int j = 0; j < identification.size(); j++) {
                Log.e("---身份证明----", identification.get(j).toString());
            }
        } else {
            L.e("---身份空----");
        }

    }

    //票据证明数据源
    private void initPjzmData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("合同");
        dataSource.add("收据");
        dataSource.add("借据");
        dataSource.add("欠据");
        dataSource.add("协议");
        dataSource.add("信件");
        dataSource.add("电报");
        dataSource.add("提货单");
        dataSource.add("仓单发票");
        dataSource.add("其他");
        mPjzmAdapter.onlyAddAll(dataSource);
    }

    //票据证明确认数据
    private void confirmPjzm() {
        if (pjzmList != null && pjzmList.size() > 0) {
            for (int i : pjzmList) {
                String vul = (String) pjzm.getAdapter().getItem(i);
                if (vul.equals("合同")) {
                    evidence.add("1");
                }
                if (vul.equals("收据")) {
                    evidence.add("2");
                }
                if (vul.equals("借据")) {
                    evidence.add("3");
                }
                if (vul.equals("欠据")) {
                    evidence.add("4");
                }
                if (vul.equals("协议")) {
                    evidence.add("5");
                }
                if (vul.equals("信件")) {
                    evidence.add("6");
                }
                if (vul.equals("电报")) {
                    evidence.add("7");
                }
                if (vul.equals("提货单")) {
                    evidence.add("8");
                }
                if (vul.equals("仓单发票")) {
                    evidence.add("9");
                }
                if (vul.equals("其他")) {
                    evidence.add("10");
                }
            }
            for (int j = 0; j < evidence.size(); j++) {
                Log.e("---票据证明----", evidence.get(j).toString());
            }
        } else {
            L.e("---票据空----");
        }

    }

    //电子数据数据源
    private void initDzsjData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("微信");
        dataSource.add("QQ");
        dataSource.add("MSN");
        dataSource.add("微博");
        dataSource.add("其他");
        mDzsjAdapter.onlyAddAll(dataSource);
    }

    //电子数据 确认数据
    private void confirmDjsj() {
        if (dzsjList != null && dzsjList.size() > 0) {
            for (int i : dzsjList) {
                String vul = (String) dzsj.getAdapter().getItem(i);
                if (vul.equals("微信")) {
                    electron.add("1");
                }
                if (vul.equals("QQ")) {
                    electron.add("2");
                }
                if (vul.equals("MSN")) {
                    electron.add("3");
                }
                if (vul.equals("微博")) {
                    electron.add("4");
                }
                if (vul.equals("其他")) {
                    electron.add("5");
                }
            }
            for (int j = 0; j < electron.size(); j++) {
                Log.e("---电子数据----", electron.get(j).toString());
            }
        } else {
            L.e("---电子数据空----");
        }


    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (group == group1) {//证据
            if (checkedId == R.id.ra_btn1) {//有
                chzm = "1";
            } else if (checkedId == R.id.ra_btn2) {
                chzm = "0";
            }
        } else if (group == group2) {//担保证明材料
            if (checkedId == R.id.ra_btn3) {//是
                dbcl = "1";
                L.e("****--担保证明材料---****" + dbcl);
            } else if (checkedId == R.id.ra_btn4) {
                dbcl = "0";
                L.e("****---证据--****" + dbcl);
            }
        } else if (group == group3) {//债务转让材料
            if (checkedId == R.id.ra_btn5) {
                zrcl = "1";
            } else if (checkedId == R.id.ra_btn6) {
                zrcl = "0";
            }
        } else if (group == group4) {//文书
            if (checkedId == R.id.ra_btn7) {
                ws = "1";
            } else if (checkedId == R.id.ra_btn8) {
                ws = "0";
            }
        }
    }

}
