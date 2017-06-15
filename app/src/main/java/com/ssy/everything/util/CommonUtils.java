package com.ssy.everything.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by ssy on 2017/6/8.
 */

public class CommonUtils {
    public static float dpToPx(Context context, float dp) {
        if (context == null) {
            return -1;
        }
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(Context context, float px) {
        if (context == null) {
            return -1;
        }
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int) (pxToDp(context, px) + 0.5f);
    }
    public static boolean hasNavigationBar(Context context) {

        boolean flag = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            flag = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                flag = false;
            } else if ("0".equals(navBarOverride)) {
                flag = true;
            }
        } catch (Exception e) {
            Log.w("TAG", e);
        }


        return flag;
    }
}
