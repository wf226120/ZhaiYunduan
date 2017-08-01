package com.jlgproject.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jlgproject.R;
import com.jlgproject.base.BaseActivity;
import com.jlgproject.fragment.PicT_Debt_Sample;
import com.jlgproject.fragment.PicT_Legal_Advice;
import com.jlgproject.fragment.PicT_Teacher_Dlegant_Demeanour;
import com.jlgproject.fragment.PicT_Teacher_Lecture;
import com.jlgproject.fragment.PicT_The_Answer;

/**
 * Created by sunbeibei on 2017/7/18.
 */

public class Buness_Pic_Text extends BaseActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener{
    private TextView title;
    private ImageView title_left;
    private ImageView title_right;
    private RadioGroup picText;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PicT_Teacher_Lecture picT_teacher_lecture;
    private FragmentTransaction fragmentTransaction2;
    private PicT_Debt_Sample picT_debt_sample;
    private PicT_The_Answer picT_the_answer;
    private PicT_Legal_Advice picT_legal_advice;
    private PicT_Teacher_Dlegant_Demeanour picT_teacher_dlegant_demeanour;

    @Override
    public int loadWindowLayout() {
        return R.layout.activity_buness_pic_text;
    }

    @Override
    public void initDatas() {
        super.initDatas();
    }

    @Override
    public void initViews() {
        super.initViews();
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText("图文课程");
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title_left.setVisibility(View.VISIBLE);
        title_left.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.iv_title_right2);
        title_right.setVisibility(View.VISIBLE);
        title_right.setOnClickListener(this);
        picText = (RadioGroup) findViewById(R.id.pic_group);
        picText.setOnCheckedChangeListener(this);
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        picT_teacher_lecture = new PicT_Teacher_Lecture();
        fragmentTransaction.add(R.id.pic_text_frame,picT_teacher_lecture);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v==title_left){
            finish();
        }else if (v==title_right){
              startActivity( new Intent(this,Serch_Pic_Text.class));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fragmentTransaction2 = supportFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.tv_teacher_lecture://名师讲堂

                if ( picT_teacher_lecture == null) {
                    picT_teacher_lecture = new PicT_Teacher_Lecture();
                    fragmentTransaction2.add(R.id.pic_text_frame, picT_teacher_lecture);
                } else {
                    fragmentTransaction2.show(picT_teacher_lecture);
                }
                if (picT_debt_sample != null) {
                    fragmentTransaction2 .hide(picT_debt_sample);
                }
                if (picT_legal_advice!= null) {
                    fragmentTransaction2 .hide(picT_legal_advice);
                }
                if (picT_teacher_dlegant_demeanour != null) {
                    fragmentTransaction2 .hide(picT_teacher_dlegant_demeanour);
                }
                if (picT_the_answer!=null){
                    fragmentTransaction2.hide(picT_the_answer);
                }
                break;
            case R.id.tv_debt_sample://解债案例

                if (picT_debt_sample  == null) {
                    picT_debt_sample = new PicT_Debt_Sample();
                    fragmentTransaction2 .add(R.id.pic_text_frame, picT_debt_sample );
                } else {
                    fragmentTransaction2 .show(picT_debt_sample);
                }
                if (picT_teacher_lecture!=null){
                    fragmentTransaction2.hide(picT_teacher_lecture);
                }
                if (picT_legal_advice!= null) {
                    fragmentTransaction2 .hide(picT_legal_advice);
                }
                if (picT_teacher_dlegant_demeanour != null) {
                    fragmentTransaction2 .hide(picT_teacher_dlegant_demeanour);
                }
                if (picT_the_answer!=null){
                    fragmentTransaction2.hide(picT_the_answer);
                }
                break;
            case R.id.tv_the_answer://答疑解惑

                if ( picT_the_answer  == null) {
                    picT_the_answer = new PicT_The_Answer();
                    fragmentTransaction2 .add(R.id.pic_text_frame,picT_the_answer );
                } else {
                    fragmentTransaction2 .show(picT_the_answer);
                }
                if (picT_teacher_lecture!=null){
                    fragmentTransaction2.hide(picT_teacher_lecture);
                }
                if (picT_legal_advice!= null) {
                    fragmentTransaction2 .hide(picT_legal_advice);
                }
                if (picT_teacher_dlegant_demeanour != null) {
                    fragmentTransaction2 .hide(picT_teacher_dlegant_demeanour);
                }
                if (picT_debt_sample!=null){
                    fragmentTransaction2.hide(picT_debt_sample);
                }
                break;
            case R.id.tv_legal_advice://法律咨询

                if (picT_legal_advice == null) {
                    picT_legal_advice = new PicT_Legal_Advice();
                    fragmentTransaction2 .add(R.id.pic_text_frame, picT_legal_advice);
                } else {
                    fragmentTransaction2 .show(picT_legal_advice);
                }
                if (picT_teacher_lecture!=null){
                    fragmentTransaction2.hide(picT_teacher_lecture);
                }
                if (picT_the_answer!= null) {
                    fragmentTransaction2 .hide(picT_the_answer);
                }
                if (picT_teacher_dlegant_demeanour != null) {
                    fragmentTransaction2 .hide(picT_teacher_dlegant_demeanour);
                }
                if (picT_debt_sample!=null){
                    fragmentTransaction2.hide(picT_debt_sample);
                }
                break;
            case R.id.tv_teacher_elegant_demeanour://名师风采

                if (picT_teacher_dlegant_demeanour == null) {
                    picT_teacher_dlegant_demeanour = new PicT_Teacher_Dlegant_Demeanour();
                    fragmentTransaction2 .add(R.id.pic_text_frame, picT_teacher_dlegant_demeanour);
                } else {
                    fragmentTransaction2 .show(picT_teacher_dlegant_demeanour);
                }
                if (picT_teacher_lecture!=null){
                    fragmentTransaction2.hide(picT_teacher_lecture);
                }
                if (picT_the_answer!= null) {
                    fragmentTransaction2 .hide(picT_the_answer);
                }
                if (picT_legal_advice != null) {
                    fragmentTransaction2 .hide(picT_legal_advice );
                }
                if (picT_debt_sample!=null){
                    fragmentTransaction2.hide(picT_debt_sample);
                }
                break;
        }
        fragmentTransaction2.commit();

    }
}

