package com.yxkj.deliveryman.util;

import android.os.Environment;
import android.util.Log;

import com.yxkj.deliveryman.BuildConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 可以有多个信息点的打印工具
 *
 * @author hhe
 */

public class NewLogUtils {
    private static final String TAG = NewLogUtils.class.getSimpleName();
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    public static int DISPLAY_MIN_PRIORITY = Log.VERBOSE;
    /**
     * 下面这个变量定义日志级别
     */
    public static final int LEVEL = VERBOSE;


    public static void d(String tag, Object... datas) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (tag == null || datas == null) {
            return;
        }

        String data;
        data = converArrayToString(tag, datas, null);

        if (LEVEL <= DEBUG) {
            println(Log.DEBUG, tag, " " + data);
        }

    }

    public static void w(String tag, Object... datas) {
        if (!BuildConfig.DEBUG) {
            return;
        }

        if (tag == null || datas == null) {
            return;
        }

        String data;
        data = converArrayToString(tag, datas, null);
        if (LEVEL <= DEBUG) {
            println(Log.WARN, tag, " " + data);
        }
    }

    public static void e(String tag, Object... datas) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (tag == null || datas == null) {
            return;
        }

        String data;
        data = converArrayToString(tag, datas, null);
        if (LEVEL <= ERROR) {
            println(Log.WARN, tag, " " + data);
        }
    }

    public static void m(String msg) {
        String methodName = new Exception().getStackTrace()[1].getMethodName();
        d(TAG, methodName, msg);
    }

    private static int println(int priority, String tag, String msg) {
        if (priority < DISPLAY_MIN_PRIORITY) {
            priority = DISPLAY_MIN_PRIORITY;
        }

        return Log.println(priority, tag, msg);
    }

    private static String converArrayToString(String tag, Object[] objects, StringBuilder buffer) {
        if (objects == null || objects.length == 0) {
            return "";
        }

        int size = objects.length;
        if (buffer == null) {
            buffer = new StringBuilder(250);
        }
        for (Object item : objects) {
            buffer.append("|");
            if (item != null) {
                if (item instanceof Throwable) {
                    buffer.append(getThrowableInfo((Throwable) item));
                } else {
                    buffer.append(item.toString());
                }
            } else {
                buffer.append("(null)");
            }
        }
        try {

            buffer.append("|");
            buffer.append(tag);
        } catch (Exception e) {

        }
        return buffer.toString();

    }

    private static String getThrowableInfo(Throwable throwable) {
        try {
            return Log.getStackTraceString(throwable);
        } catch (Exception e) {
        }

        Writer info = null;
        PrintWriter printWriter = null;
        try {
            info = new StringWriter();
            printWriter = new PrintWriter(info);
            throwable.printStackTrace(printWriter);

            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
        } catch (Exception e) {
            androidLog("LogUtil", e);
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                if (info != null) {
                    info.close();
                }
            } catch (IOException e1) {
                androidLog("LogUtil", e1);
            }
        }
        if (info != null) {
            return info.toString();
        }
        return "";
    }

    private static void androidLog(String tag, Throwable e) {
        println(Log.ERROR, tag, "", e);
    }

    private static int println(int priority, String tag, String msg, Throwable tr) {
        if (priority < DISPLAY_MIN_PRIORITY) {
            priority = DISPLAY_MIN_PRIORITY;
        }
        return Log.println(priority, tag, msg + '\n' + Log.getStackTraceString(tr));
    }

}
