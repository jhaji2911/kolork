package com.apkzube.blindvision.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Static methods for dealing with application version.
 */
public final class Versions {

    public static final String DEFAULT_VERSION_NAME = "unknown name";

    public static String getVersionName(Context context) {
        String versionName;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = DEFAULT_VERSION_NAME;
        }
        return versionName;
    }

    // Non-instantiability
    private Versions() {
    }
}
