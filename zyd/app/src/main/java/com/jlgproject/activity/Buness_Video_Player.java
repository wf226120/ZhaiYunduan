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
import com.jlgproject.fragment.Video_Debt_Sample;
import com.jlgproject.fragment.Video_Legal_Advice;
import com.jlgproject.fragment.Video_Teacher_Dlegant_Demeanour;
import com.jlgproject.fragment.Video_Teacher_lecture;
import com.jlgproject.fragment.Video_The_Answer;

/**
 * Created by sunbeibei on 2017/7/18.
 */

public class Buness_Video_Player extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private TextView title;
    private ImageView title_left;
    private ImageView title_right;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Video_Teacher_lecture video_teacher_lecture;
    private RadioGroup video;
    private FragmentTransaction fragmentTransaction1;
    private Video_Debt_Sample video_debt_sample;
    private Video_The_Answer video_the_answer;
    private Video_Legal_Advice video_legal_advice;
    private Video_Teacher_Dlegant_Demeanour video_teacher_dlegant_demeanour;

    @Override
    public int loadWindowLayout() {
        return R.layout.activiyt_busness_video_player;
    }

    @Override
    public void initDatas() {
        super.initDatas();


    }

    @Override
    public void initViews() {
        super.initViews();
        title = (TextView) findViewById(R.id.tv_title_name);
        title.setText("视频课程");
        title_left = (ImageView) findViewById(R.id.iv_title_left);
        title_left.setVisibility(View.VISIBLE);
        title_left.setOnClickListener(this);
        title_right = (ImageView) findViewById(R.id.iv_title_right2);
        title_right.setVisibility(View.VISIBLE);
        title_right.setOnClickListener(this);
        video = (RadioGroup) findViewById(R.id.video_group);
        video.setOnCheckedChangeListener(this);
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();
        video_teacher_lecture = new Video_Teacher_lecture();
        fragmentTransaction.add(R.id.video_frame, video_teacher_lecture);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        if (v == title_left) {
            finish();
        } else if (v == title_right) {
            startActivity(new Intent(this, Serch_Video.class));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        fragmentTransaction1 = supportFragmentManager.beginTransaction();
        switch (checkedId) {
            case R.id.teacher_lecture://名师讲堂

                if (video_teacher_lecture == null) {
                    video_teacher_lecture = new Video_Teacher_lecture();
                    fragmentTransaction1.add(R.id.video_frame, video_teacher_lecture);
                } else {
                    fragmentTransaction1.show(video_teacher_lecture);
                }
                if (video_debt_sample != null) {
                    fragmentTransaction1.hide(video_debt_sample);
                }
                if (video_legal_advice != null) {
                    fragmentTransaction1.hide(video_debt_sample);
                }
                if (video_teacher_dlegant_demeanour != null) {
                    fragmentTransaction1.hide(video_teacher_dlegant_demeanour);
                }
                if (video_the_answer != null) {
                    fragmentTransaction1.hide(video_the_answer);
                }
                break;
            case R.id.debt_sample://解债案例

                if (video_debt_sample == null) {
                    video_debt_sample = new Video_Debt_Sample();
                    fragmentTransaction1.add(R.id.video_frame, video_debt_sample);
                } else {
                    fragmentTransaction1.show(video_debt_sample);
                }
                if (video_teacher_lecture != null) {
                    fragmentTransaction1.hide(video_teacher_lecture);
                }
                if (video_legal_advice != null) {
                    fragmentTransaction1.hide(video_legal_advice);
                }
                if (video_teacher_dlegant_demeanour != null) {
                    fragmentTransaction1.hide(video_teacher_dlegant_demeanour);
                }
                if (video_the_answer != null) {
                    fragmentTransaction1.hide(video_the_answer);
                }
                break;
            case R.id.the_answer://答疑解惑

                if (video_the_answer == null) {
                    video_the_answer = new Video_The_Answer();
                    fragmentTransaction1.add(R.id.video_frame, video_the_answer);
                } else {
                    fragmentTransaction1.show(video_the_answer);
                }
                if (video_teacher_lecture != null) {
                    fragmentTransaction1.hide(video_teacher_lecture);
                }
                if (video_legal_advice != null) {
                    fragmentTransaction1.hide(video_legal_advice);
                }
                if (video_teacher_dlegant_demeanour != null) {
                    fragmentTransaction1.hide(video_teacher_dlegant_demeanour);
                }
                if (video_debt_sample != null) {
                    fragmentTransaction1.hide(video_debt_sample);
                }
                break;
            case R.id.legal_advice://法律咨询

                if (video_legal_advice == null) {
                    video_legal_advice = new Video_Legal_Advice();
                    fragmentTransaction1.add(R.id.video_frame, video_legal_advice);
                } else {
                    fragmentTransaction1.show(video_legal_advice);
                }
                if (video_teacher_lecture != null) {
                    fragmentTransaction1.hide(video_teacher_lecture);
                }
                if (video_the_answer != null) {
                    fragmentTransaction1.hide(video_the_answer);
                }
                if (video_teacher_dlegant_demeanour != null) {
                    fragmentTransaction1.hide(video_teacher_dlegant_demeanour);
                }
                if (video_debt_sample != null) {
                    fragmentTransaction1.hide(video_debt_sample);
                }
                break;
            case R.id.teacher_elegant_demeanour://名师风采

                if (video_teacher_dlegant_demeanour == null) {
                    video_teacher_dlegant_demeanour = new Video_Teacher_Dlegant_Demeanour();
                    fragmentTransaction1.add(R.id.video_frame, video_teacher_dlegant_demeanour);
                } else {
                    fragmentTransaction1.show(video_teacher_dlegant_demeanour);
                }
                if (video_teacher_lecture != null) {
                    fragmentTransaction1.hide(video_teacher_lecture);
                }
                if (video_the_answer != null) {
                    fragmentTransaction1.hide(video_the_answer);
                }
                if (video_legal_advice != null) {
                    fragmentTransaction1.hide(video_legal_advice);
                }
                if (video_debt_sample != null) {
                    fragmentTransaction1.hide(video_debt_sample);
                }
                break;
        }
        fragmentTransaction1.commit();

    }
}
