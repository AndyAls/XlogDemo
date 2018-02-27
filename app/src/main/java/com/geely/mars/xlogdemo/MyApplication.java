package com.geely.mars.xlogdemo;

import android.app.Application;

import com.geely.mars.xlogdemo.xlog.XLog;
import com.tencent.mars.xlog.Xlog;

/**
 * Created by Shuai.Li13 on 2017/11/3.
 */

public class MyApplication extends Application {

    /**
     * 是否开启xlog日志,默认true,不开则使用系统log
     */
    private final boolean isXlog=false;
    @Override
    public void onCreate() {
        super.onCreate();
        XLog.initialize(this,isXlog);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        XLog.appenderClose();
    }
}
