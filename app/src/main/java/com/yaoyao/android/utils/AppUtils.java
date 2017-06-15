package com.yaoyao.android.utils;

import android.content.Context;
import android.content.pm.PackageManager;

import com.yaoyao.android.BaseApplication;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :
 */

public class AppUtils {
    /**
     * 获取当前应用的版本号
     */
    public static int getVersionCode() {
        Context appContext = BaseApplication.getAppContext();
        try {
            return appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName() {
        Context appContext = BaseApplication.getAppContext();
        try {
            return appContext.getPackageManager().getPackageInfo(appContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
