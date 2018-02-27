package me.kaede.mvp.login;

import android.support.v7.widget.LinearSnapHelper;

/**
 * Created by Shuai.Li13 on 2017/12/13.
 */

public enum EnumDemo {

    //akjdf
    ONE("AKJJDKA", 1),
    TWO("sksk", 2);

    private String desc;
    private int age;
    public String getDesc() {
        return desc;
    }


    public int getAge() {
        return age;
    }


    private EnumDemo(String akjjdka, int i) {
        this.desc = akjjdka;
        this.age = i;
    }

}
