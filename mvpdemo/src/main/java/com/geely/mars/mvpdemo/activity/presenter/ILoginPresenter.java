package com.geely.mars.mvpdemo.activity.presenter;

/**
 * Created by Shuai.Li13 on 2017/12/7.
 */

public interface ILoginPresenter {
    void clear();
    void doLogin(String name,String password);
    void setProgressVisibity(int visibity);
}
