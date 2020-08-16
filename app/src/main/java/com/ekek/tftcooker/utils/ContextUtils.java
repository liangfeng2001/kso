package com.ekek.tftcooker.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.ekek.tftcooker.common.Logger;

public class ContextUtils {

    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            Logger.getInstance().e(e);
            return "Unknown";
        }
    }
}
