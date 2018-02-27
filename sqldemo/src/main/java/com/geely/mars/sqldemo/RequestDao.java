package com.geely.mars.sqldemo;

/**
 * Created by Shuai.Li13 on 2018/2/9.
 */

public class RequestDao {

    private int status;

    private content content;

    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }
}
