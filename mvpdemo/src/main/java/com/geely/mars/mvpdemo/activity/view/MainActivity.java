package com.geely.mars.mvpdemo.activity.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.geely.mars.mvpdemo.R;
import com.geely.mars.mvpdemo.activity.presenter.LoginPresenterImpl;

/**
 * @author Shuai.Li13
 */
public class MainActivity extends AppCompatActivity implements ILogViwe {

    private EditText user;
    private EditText password;
    private ProgressBar progressBar;
    private LoginPresenterImpl loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.setProgressVisibity(View.INVISIBLE);
    }

    private void findView() {

        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        final OverLineEdixView edit = (OverLineEdixView) findViewById(R.id.edit);
        edit.setOnOverLineChangerListener(new OverLineEdixView.OnOverLineChangerListener() {
            @Override
            public void onOverLine(boolean inOverLine) {
                if (inOverLine){
                    edit.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }else {
                }
            }
        });

    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login:
                loginPresenter.setProgressVisibity(View.VISIBLE);
                loginPresenter.doLogin(user.getText().toString(),password.getText().toString());
                break;
            case R.id.reset:
                loginPresenter.clear();
                break;
            default:
                break;
        }

    }

    @Override
    public void onClearText() {
        user.setText("");
        password.setText("");
    }

    @Override
    public void onLoginResult(boolean result, int code) {
        loginPresenter.setProgressVisibity(View.INVISIBLE);
        if (result){
            Toast.makeText(this,"成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visible) {
        progressBar.setVisibility(visible);
    }
}
