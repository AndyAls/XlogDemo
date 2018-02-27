package me.kaede.mvp.home.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import org.w3c.dom.ls.LSException;

import me.kaede.mvp.R;
import me.kaede.mvp.home.EncryptUtil;

/**
 * Created by Shuai.Li13 on 2017/12/12.
 */

public class MyActivity extends Activity {

    private final String TAG=this.getClass().getSimpleName();
    private String lishuai;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt1:
                lishuai = EncryptUtil.Base64Encrypt("lishuai");
                Log.e(TAG, "lishuai----->"+ lishuai);
                break;
            case R.id.bt2:
                String s = EncryptUtil.Base64Decode(lishuai);
                Log.e(TAG, "s----->"+s );
                break;
            case R.id.bt3:
                String lishuai = EncryptUtil.md5Encrypt("lishuai");
                Log.e(TAG, "lishuai----->"+ lishuai);
                break;
            case R.id.bt4:
                break;
            case R.id.bt5:
                break;
            default:
                break;
        }
    }
}
