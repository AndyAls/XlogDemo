package com.geely.mars.mvpdemo.activity.presenter;

import android.os.Handler;
import android.os.Looper;

import com.geely.mars.mvpdemo.activity.model.IUser;
import com.geely.mars.mvpdemo.activity.model.UserModel;
import com.geely.mars.mvpdemo.activity.view.ILogViwe;

/**
 * Created by Shuai.Li13 on 2017/12/7.
 */

public class LoginPresenterImpl implements ILoginPresenter {

    ILogViwe logView;
    private IUser user;
    private final Handler handler;

    public LoginPresenterImpl(ILogViwe logViwe) {
        this.logView = logViwe;
        initUser();
        handler = new Handler(Looper.getMainLooper());

    }

    private void initUser() {
        user = new UserModel("hello","1234");
    }

    @Override
    public void clear() {
        logView.onClearText();

    }

    @Override
    public void doLogin(String name, String password) {

        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name,password);
        if (code!=0) {
            isLoginSuccess = false;
        }
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                logView.onLoginResult(result, code);
            }
        }, 3000);

    }

    @Override
    public void setProgressVisibity(int visibity) {
        logView.onSetProgressBarVisibility(visibity);
    }
}
