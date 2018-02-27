package com.geely.mars.xlogdemo;

import com.geely.mars.xlogdemo.xlog.XLog;

/**
 * Created by Shuai.Li13 on 2017/11/7.
 */

class Peason {
    public Peason() {
        XLog.i("---Peason");
        XLog.i(this,"Peason");
        XLog.i("----","Peason====");
    }
}
