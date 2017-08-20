package com.major.audiodev;

import android.util.Log;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/8/20 12:57
 */
public class SL{

    private static final String TAG = "ele_b";

    public static void i(String msg){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if(stackTrace.length >= 3){
            StackTraceElement element = stackTrace[3];
            String pkgName = getPkgName(element.getClassName()); // 获取包名
            sb.append(element.toString().replace(pkgName + ".", ""));
            sb.append(": ");
        }
        sb.append(msg);

        Log.i(TAG, sb.toString());
    }

    public static void e(String msg){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder();
        if(stackTrace.length >= 3){
            StackTraceElement element = stackTrace[3];
            String pkgName = getPkgName(element.getClassName()); // 获取包名
            sb.append(element.toString().replace(pkgName + ".", ""));
            sb.append(": ");
        }
        sb.append(msg);

        Log.e(TAG, sb.toString());
    }

    private static String getPkgName(String className){
        if(className.contains("$")){
            // 内部类
            className = className.substring(0, className.indexOf("$"));
        }
        className = className.substring(0, className.lastIndexOf("."));

        return className;
    }
}
