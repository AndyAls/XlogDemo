package com.geely.mars.sqldemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.security.keystore.KeyProperties;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import javax.crypto.KeyGenerator;

/**
 * Created by Shuai.Li13 on 2018/1/25.
 */

public class TimeActivity extends Activity{

    private TextView tvTime;
    private int now = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_activity);
        initView();
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time);
    }

    public void start(View view) {
        time.start();
    }

    public void cancle(View view) {
        time.cancel();
    }
    CountDownTimer time = new CountDownTimer(60*1000,1000) {
        @Override
        public void onTick(long l) {
            tvTime.setText(l/1000+"");
        }

        @Override
        public void onFinish() {
            tvTime.setText("onFinish");
        }

    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onClick(View view) {
        try {
            KeyGenerator keyGenerator =KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"androidkeystore");

        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
}
