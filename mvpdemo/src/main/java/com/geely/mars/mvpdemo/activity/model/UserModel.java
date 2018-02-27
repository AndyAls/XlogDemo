package com.geely.mars.mvpdemo.activity.model;

/**
 * Created by Shuai.Li13 on 2017/12/7.
 */

public class UserModel implements IUser{
    public String name;
    public String password;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int checkUserValidity(String name1, String password1) {
        if (name1.equals(name)&&password1.equals(password)){
            return 0;
        }
        return -1;
    }
}
