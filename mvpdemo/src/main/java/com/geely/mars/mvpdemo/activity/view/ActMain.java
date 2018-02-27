package com.geely.mars.mvpdemo.activity.view;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;

import com.geely.mars.mvpdemo.R;


public class ActMain extends Activity {

    private OverLineEdixView edixView;
    private OverLineTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        edixView = findViewById(R.id.editext);
        textView = findViewById(R.id.textview);
        initView();
        setListener();
    }

    private void initView() {
        edixView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                textView.setText(editable.toString());
            }
        });
    }

    private void setListener() {
        edixView.setOnOverLineChangerListener(new OverLineEdixView.OnOverLineChangerListener() {
            @Override
            public void onOverLine(boolean inOverLine) {
                if (inOverLine){
                    edixView.setTextColor(Color.parseColor("#ff0000"));
                }else {
                    edixView.setTextColor(Color.BLACK);
                }
            }
        });
        textView.setOnOverLineChangerListener(new OverLineTextView.OnOverLineChangerListener() {
            @Override
            public void onOverLine(boolean inOverLine) {
                if (inOverLine){
                    textView.setTextColor(Color.RED);
                }else {
                    textView.setTextColor(Color.parseColor("#00ff00"));
                }

            }
        });
    }

}
