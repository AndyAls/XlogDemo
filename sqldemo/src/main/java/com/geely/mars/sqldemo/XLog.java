package com.geely.mars.sqldemo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 使用时建议用当前类名当做tag,方便在控制台定位打日志的位置,在有生命周期的类中初始化阶段,
 * 如onCreate方法中直接调用XLog.initTAG("当前类名"),当前类可以直接XLog.i(obj)来打印日志;
 * <p>
 * 在应用退出调用XLog.appenderClose()方法结束日志,已调用,无需关注.只需关注打印日志即可
 * <p>
 * 对于没有进行initTag的普通类,可以直接使用XLog.i("当前类名",obj)来打印日志
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
    private static boolean ISXLOG = false;
    /**
     * 是否对tag初始化,
     */
    private static boolean isInitTag = false;
    private static boolean isConsoleLogOpen = true;

    private XLog() {
        throw new UnsupportedOperationException("XLog can't be instantiated ...");
    }

    public static void initTAG(String tag) {
        if (tag == null) {
            XLog.TAG = "TAG";
            XLog.isInitTag = false;
        } else {
            boolean space = isSpace(tag);
            if (space) {
                XLog.TAG = "TAG";
            } else {
                XLog.TAG = tag;
            }
            XLog.isInitTag = true;
        }

    }


    public static void v(Object msg) {
        showLog(android.util.Log.VERBOSE, TAG, msg);
    }

    public static void v(String tag, Object... msg) {
        checkTag(android.util.Log.VERBOSE, tag, msg);
    }

    public static void d(Object msg) {
        showLog(android.util.Log.DEBUG, TAG, msg);
    }

    public static void d(String tag, Object... msg) {
        checkTag(android.util.Log.DEBUG, tag, msg);
    }

    public static void i(Object msg) {
        showLog(android.util.Log.INFO, TAG, msg);
    }

    public static void i(String tag, Object... msg) {
        checkTag(android.util.Log.INFO, tag, msg);
    }

    public static void w(Object msg) {
        showLog(android.util.Log.WARN, TAG, msg);
    }

    public static void w(String tag, Object... msg) {
        checkTag(android.util.Log.WARN, tag, msg);

    }

    public static void e(Object msg) {
        showLog(android.util.Log.ERROR, TAG, msg);
    }

    public static void e(String tag, Object... msg) {
        checkTag(android.util.Log.ERROR, tag, msg);
    }

    public static void a(Object msg) {
        showLog(android.util.Log.ASSERT, TAG, msg);
    }

    public static void a(String tag, Object... msg) {
        checkTag(android.util.Log.ASSERT, tag, msg);
    }

    public static void json(String json) {
        showLog(J, TAG, json);

    }

    public static void json(String tag, String json) {
        checkTag(J, tag, json);
    }


    private static void showLog(int level, String tag, Object... msg) {
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
                android.util.Log.i(tag, formatJson(msg[0] == null ? "null" : msg[0].toString()));
                break;
            default:
                break;
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

    /**
     * 改造输入的日志格式
     */
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
        ApplicationInfo appInfo;
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
                    if ("".equals(tag.getClass().getSimpleName())) {
                        showLog(level, TAG, msg);
                    } else {
                        showLog(level, tag.getClass().getSimpleName(), msg);
                    }

                }
            }
        }
    }

    private static String getAppInfo(Context mContext) {
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo == null ? "null" : packageInfo.versionName + "--->>" + packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "null";
        }
    }

}
