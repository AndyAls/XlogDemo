package com.geely.mars.xlogdemo.xlog;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;


/**
 * Created by Shuai.Li13 on 2017/10/26.
 *
 */
public class XLog {

    /**
     * 系统log日志级别
     */
    private static final int V = android.util.Log.VERBOSE;
    private static final int D = android.util.Log.DEBUG;
    private static final int I = android.util.Log.INFO;
    private static final int W = android.util.Log.WARN;
    private static final int E = android.util.Log.ERROR;
    private static final int A = android.util.Log.ASSERT;
    private static final int J = -1;

    /**
     * xlog日志级别
     */
    private static final int XV = Log.LEVEL_VERBOSE;
    private static final int XD = Log.LEVEL_DEBUG;
    private static final int XI = Log.LEVEL_INFO;
    private static final int XW = Log.LEVEL_WARNING;
    private static final int XE = Log.LEVEL_ERROR;
    private static final int XA = Log.LEVEL_FATAL;
    private static final int XJ = -1;

    /**
     * 默认标签,如果没有初始化标签或标签是空格,使用此默认标签
     */
    private static String TAG = "TAG";
    private static String fileName = "";
    private static int lineNum;
    private static String methodName;
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String ARGS = "args";
    /**
     * 是否使用xlog日志,不使用则切换成系统日志
     */
    private static boolean ISXLOG = true;
    /**
     * 是否对tag初始化,
     */
    private static boolean isInitTag = false;
    private static boolean isConsoleLogOpen = true;

    private XLog() {
        throw new UnsupportedOperationException("XLog can't be instantiated ...");
    }

    public static void initialize(Context mContext, boolean isXlog) {
        XLog.ISXLOG = isXlog;
        if (isXlog) {
            System.loadLibrary("stlport_shared");
            System.loadLibrary("marsxlog");
            final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
            //日志写入本地的文件路径
            final String logPath = sdcard + File.separator + mContext.getPackageName() + "/xlog";
            //当 logPath 不可写时候会写进这个目录
            final String cachePath = mContext.getFilesDir() + "/xlog";
            //参1,日志级别,官方推荐debug版本使用LEVEL_DEBUG,release版本使用LEVEL_INFO,这里统一设置成最低级别 LEVEL_VERBOSE
            //参2,日志的写入模式,统一设置成异步,
            //参5,日志的文件格式前缀,生成的文件名是前缀+当日时间,如 HEARDER_20171106,每天生成的日志保存在当天的同一个文件中
            Xlog.appenderOpen(Xlog.LEVEL_VERBOSE, Xlog.AppednerModeAsync, cachePath, logPath, "HEARDER", "");
            isConsoleLogOpen = isDebugMode(mContext);
            //是否开启控制台日志
            Xlog.setConsoleLogOpen(isConsoleLogOpen);
            Log.appenderFlush(true);
            Log.setLogImp(new Xlog());
        }
    }

    public static void initTAG(Object tag) {
        if (tag == null) {
            XLog.TAG = "TAG";
            XLog.isInitTag = false;
        } else {
            if (tag instanceof String) {
                boolean space = isSpace((String) tag);
                if (space) {
                    XLog.TAG = "TAG";
                } else {
                    XLog.TAG = (String) tag;
                }
            } else {
                if (isPrimitiveType(tag)) {
                    XLog.TAG = String.valueOf(tag);
                } else {
                    XLog.TAG = tag.getClass().getSimpleName();
                }
            }
            XLog.isInitTag = true;
        }

    }


    public static void appenderClose() {
        if (ISXLOG) {
            Log.appenderClose();
        }

    }

    public static void v(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_VERBOSE, TAG, msg);
        } else {
            showLog(android.util.Log.VERBOSE, TAG, msg);
        }
    }

    public static void v(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_VERBOSE, tag, msg);
        } else {
            checkTag(android.util.Log.VERBOSE, tag, msg);
        }
    }

    public static void d(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_DEBUG, TAG, msg);
        } else {
            showLog(android.util.Log.DEBUG, TAG, msg);
        }
    }

    public static void d(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_DEBUG, tag, msg);
        } else {
            checkTag(android.util.Log.DEBUG, tag, msg);
        }
    }

    public static void i(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_INFO, TAG, msg);
        } else {
            showLog(android.util.Log.INFO, TAG, msg);
        }
    }

    public static void i(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_INFO, tag, msg);
        } else {
            checkTag(android.util.Log.INFO, tag, msg);
        }
    }

    public static void w(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_WARNING, TAG, msg);
        } else {
            showLog(android.util.Log.WARN, TAG, msg);
        }
    }

    public static void w(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_WARNING, tag, msg);
        } else {
            checkTag(android.util.Log.WARN, tag, msg);
        }

    }

    public static void e(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_ERROR, TAG, msg);
        } else {
            showLog(android.util.Log.ERROR, TAG, msg);
        }
    }

    public static void e(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_ERROR, tag, msg);
        } else {
            checkTag(android.util.Log.ERROR, tag, msg);
        }
    }

    public static void a(Object msg) {
        if (ISXLOG) {
            showLog(Log.LEVEL_FATAL, TAG, msg);
        } else {
            showLog(android.util.Log.ASSERT, TAG, msg);
        }
    }

    public static void a(Object tag, Object... msg) {
        if (ISXLOG) {
            checkTag(Log.LEVEL_FATAL, tag, msg);
        } else {
            checkTag(android.util.Log.ASSERT, tag, msg);
        }
    }

    public static void json(String json) {
        if (ISXLOG) {
            showLog(XJ, TAG, json);
        } else {
            showLog(J, TAG, json);
        }

    }

    public static void json(Object tag, String json) {
        if (ISXLOG) {
            checkTag(XJ, tag, json);
        } else {
            checkTag(J, tag, json);
        }
    }


    private static void showLog(int level, String tag, Object... msg) {
        if (ISXLOG) {
            switch (level) {
                case XV:
                    getStackTraceElement(tag);
                    Log.v(tag, createLog(msg));
                    break;
                case XD:
                    getStackTraceElement(tag);
                    Log.d(tag, createLog(msg));
                    break;
                case XI:
                    getStackTraceElement(tag);
                    Log.i(tag, createLog(msg));
                    break;
                case XW:
                    getStackTraceElement(tag);
                    Log.w(tag, createLog(msg));
                    break;
                case XE:
                    getStackTraceElement(tag);
                    Log.e(tag, createLog(msg));
                    break;
                case XA:
                    getStackTraceElement(tag);
                    Log.f(tag, createLog(msg));
                    break;
                case XJ:
                    getStackTraceElement(tag);
                    Log.i(tag,formatJson(msg[0]==null?"null":msg[0].toString()));
                    break;
                default:
                    break;
            }

        } else {
            if (!isConsoleLogOpen) {
                return;
            }
            switch (level) {
                case V:
                    getStackTraceElement(tag);
                    android.util.Log.v(tag, createLog(msg));
                    break;
                case D:
                    getStackTraceElement(tag);
                    android.util.Log.d(tag, createLog(msg));
                    break;
                case I:
                    getStackTraceElement(tag);
                    android.util.Log.i(tag, createLog(msg));
                    break;
                case W:
                    getStackTraceElement(tag);
                    android.util.Log.w(tag, createLog(msg));
                    break;
                case E:
                    getStackTraceElement(tag);
                    android.util.Log.e(tag, createLog(msg));
                    break;
                case A:
                    getStackTraceElement(tag);
                    android.util.Log.wtf(tag, createLog(msg));
                    break;
                case J:
                    getStackTraceElement(tag);
                    android.util.Log.i(tag,formatJson(msg[0]==null?"null":msg[0].toString()));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 判断是否是基本数据类型及包装类
     */
    private static boolean isPrimitiveType(Object tag) {
        try {
            return ((Class) tag.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException e) {
            return false;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    private static String createLog(Object... msg) {
        if (TextUtils.isEmpty(methodName) && TextUtils.isEmpty(fileName)) {
            return progressMsg(msg);
        }
        return methodName +
                "(" + fileName + ":" + lineNum + "): " + progressMsg(msg);
    }

    private static String progressMsg(Object... msg) {
        String body = "";
        if (msg != null) {
            if (msg.length == 1) {
                Object object = msg[0];
                body = object == null ? "null" : object.toString();
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0, len = msg.length; i < len; ++i) {
                    Object content = msg[i];
                    sb.append(ARGS)
                            .append("[")
                            .append(i)
                            .append("]")
                            .append(" = ")
                            .append(content == null ? "null" : content.toString())
                            .append(LINE_SEP);
                }
                body = sb.toString();
            }
        }
        return body;
    }

    /**
     * 格式化json
     */
    private static String formatJson(String json) {
        try {
            if (json.startsWith("{")) {
                json = new JSONObject(json).toString(4);
            } else if (json.startsWith("[")) {
                json = new JSONArray(json).toString(4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(methodName) && TextUtils.isEmpty(fileName)) {
            return json;
        }
        return methodName +
                "(" + fileName + ":" + lineNum + "): " + json;
    }
    /**
     * 获取堆栈信息,方便控制台定位日志的位置
     */
    private static void getStackTraceElement(String tag) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ((tag + ".java").equals(stackTraceElement.getFileName())) {
                fileName = stackTraceElement.getFileName();
                lineNum = stackTraceElement.getLineNumber();
                methodName = stackTraceElement.getMethodName();
                return;
            } else {
                fileName = "";
                lineNum = -1;
                methodName = "";
            }
        }
    }

    /**
     * 是否是调试模式
     */
    private static boolean isDebugMode(Context mContext) {
        ApplicationInfo appInfo ;
        try {
            appInfo = mContext.getPackageManager().
                    getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return appInfo != null && appInfo.metaData != null && appInfo.metaData.getBoolean("IS_DEBUG_MODE", false);
    }


    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static void checkTag(int level, Object tag, Object... msg) {
        if (tag == null) {
            if (isInitTag) {
                showLog(level, TAG, msg);
            } else {
                showLog(level, "TAG", msg);
            }
        } else {
            if (tag instanceof String) {
                if (isSpace((String) tag)) {
                    showLog(level, "TAG", msg);
                } else {
                    showLog(level, (String) tag, msg);
                }
            } else {
                if (isPrimitiveType(tag)) {
                    showLog(level, String.valueOf(tag), msg);
                } else {
                    showLog(level, tag.getClass().getSimpleName(), msg);
                }
            }
        }
    }

}
