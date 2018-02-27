package com.geely.mars.eventbusdemo;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    private  final String TAG = this.getClass().getSimpleName();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        textView = (TextView) findViewById(R.id.text);
    }

    public void onClick(View view) {
        startActivity(new Intent(this,SencondActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void getEvent(Peason peason){
        textView.setText(peason.s);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(Message msg){
        int arg1 = msg.arg1;
        Log.e(TAG, "getMessage: "+arg1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
