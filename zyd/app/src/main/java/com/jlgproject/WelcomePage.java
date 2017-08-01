package com.jlgproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jlgproject.R;

/**
 * 欢迎页
 */
public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        /**
         * 页面跳转
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomePage.this, MainActivity.class);
                WelcomePage.this.startActivity(mainIntent);
                WelcomePage.this.finish();
            }

        }, 2000);
    }
}
