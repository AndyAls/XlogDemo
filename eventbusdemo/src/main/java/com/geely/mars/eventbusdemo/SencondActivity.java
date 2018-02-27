package com.geely.mars.eventbusdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Shuai.Li13 on 2017/12/13.
 */

public class SencondActivity extends Activity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sencond_activity);

    }

    public void onClick(View view) {
        EventBus.getDefault().post(new Peason("事件总线"));
        Message msg = Message.obtain();
        msg.arg1 = 1;
        EventBus.getDefault().post(msg);
    }


    public void onSheetClick(View view) {

        showSheetDialog();
    }

    private void showSheetDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.item, null);
        ListView ls = view.findViewById(R.id.listview);
        String[] array=new String[]{"item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9","item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9","item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9","item-1","item-2","item-3","item-4","item-5","item-6","item-7","item-8","item-9"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,array);
        ls.setAdapter(adapter);

        dialog.setContentView(view);
        dialog.show();
    }
}
