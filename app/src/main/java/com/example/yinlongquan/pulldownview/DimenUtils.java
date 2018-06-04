package com.example.yinlongquan.pulldownview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;


import java.lang.reflect.Field;


/**
 * Created by yinlongquan on 18-5-2.
 */

public class DimenUtils {

    public static final int DENSITY_LOW = 120;
    public static final int DENSITY_MEDIUM = 160;
    public static final int DENSITY_HIGH = 240;
    public static final int DENSITY_XHIGH = 320;
    public final static float BASE_SCREEN_HEIGHT = 1280f;
    public final static float BASE_SCREEN_DENSITY = 2f;
    public static Float sScaleW, sScaleH;
    private static float mScreenSize;

    public static int sStatusHeight;
    public static final DisplayMetrics mMetrics = Application.getInstance().getResources()
            .getDisplayMetrics();

    private static final int DP_TO_PX = TypedValue.COMPLEX_UNIT_DIP;
    private static final int SP_TO_PX = TypedValue.COMPLEX_UNIT_SP;
    private static final int PX_TO_DP = TypedValue.COMPLEX_UNIT_MM + 1;
    private static final int PX_TO_SP = TypedValue.COMPLEX_UNIT_MM + 2;
    private static final int DP_TO_PX_SCALE_H = TypedValue.COMPLEX_UNIT_MM + 3;

    public static final int LDPI = 1;
    public static final int MDPI = 2;
    public static final int HDPI = 3;
    public static final int XHDPI = 4;
    public static final int XXHDPI = 5;
    public static final int XXXHDPI = 6;
    public static final int OTHER_DPI = 7;
    public static final int NULL_DPI = 0;
    // -- dimens convert

    private static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case DP_TO_PX:
            case SP_TO_PX:
                return TypedValue.applyDimension(unit, value, metrics);
            case PX_TO_DP:
                return value / metrics.density;
            case PX_TO_SP:
                return value / metrics.scaledDensity;
            case DP_TO_PX_SCALE_H:
                return TypedValue.applyDimension(DP_TO_PX, value * getScaleFactorH(), metrics);
        }
        return 0;
    }

    public static int dp2px(float value) {
        return (int) applyDimension(DP_TO_PX, value, mMetrics);
    }

    public static int sp2px(float value) {
        return (int) applyDimension(SP_TO_PX, value, mMetrics);
    }

    public static int px2dp(float value) {
        return (int) applyDimension(PX_TO_DP, value, mMetrics);
    }

    public static int px2sp(float value) {
        return (int) applyDimension(PX_TO_SP, value, mMetrics);
    }

    public static int dp2pxScaleH(float value) {
        return (int) applyDimension(DP_TO_PX_SCALE_H, value, mMetrics);
    }

    public static float getScaleFactorH() {
        if (sScaleH == null) {
            sScaleH = (mMetrics.heightPixels * BASE_SCREEN_DENSITY)
                    / (mMetrics.density * BASE_SCREEN_HEIGHT);
        }
        return sScaleH;
    }


    public static boolean isLowDensity() {
        float densityDpi = mMetrics.densityDpi;
        if (densityDpi == DENSITY_LOW || densityDpi == DENSITY_MEDIUM) {
            // Log.d("show", "low density");
            return true;
        }
        return false;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private static int sStatusBarHeight = -1;

    public static int getStatusBarHeight2() {
        if (sStatusBarHeight != -1) {
            return sStatusBarHeight;
        }

        try {
            Class<?> cl = Class.forName("com.android.internal.R$dimen");
            Object obj = cl.newInstance();
            Field field = cl.getField("status_bar_height");

            int x = Integer.parseInt(field.get(obj).toString());
            sStatusBarHeight = Application.getInstance().getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sStatusBarHeight;
    }

    public static int getWindowWidth() {
        return mMetrics.widthPixels;
    }

    public static int getWindowHeight() {
        return mMetrics.heightPixels;
    }

    public static float getScreenSize() {
        if (mMetrics.xdpi != 0 && mMetrics.ydpi != 0) {
            double x = Math.pow(getWindowWidth() / mMetrics.xdpi, 2);
            double y = Math.pow(getWindowHeight() / mMetrics.ydpi, 2);
            mScreenSize = (float) (Math.round(Math.sqrt(x + y) * 10) / 10.0);
        }
        return mScreenSize;
    }

    public static int getScreenType() {

        if (mMetrics.densityDpi > 240 && mMetrics.densityDpi <= 320) {
            return XHDPI;

        } else if (mMetrics.densityDpi > 320 && mMetrics.densityDpi <= 480) {
            return XXHDPI;

        } else if (mMetrics.densityDpi > 480 && mMetrics.densityDpi <= 640) {
            return XXXHDPI;

        } else if (mMetrics.densityDpi > 160 && mMetrics.densityDpi <= 240) {
            return HDPI;

        } else if (mMetrics.densityDpi > 120 && mMetrics.densityDpi <= 160) {
            return MDPI;

        } else if (mMetrics.densityDpi <= 120 && mMetrics.densityDpi > 0) {
            return LDPI;

        } else if (mMetrics.densityDpi > 640) {
            return XXXHDPI;

        } else if (mMetrics.densityDpi <= 0) {
            return OTHER_DPI;
        }

        return NULL_DPI;

    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int top = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            x = Integer.parseInt(field.get(obj).toString());
            field = c.getField("status_bar_height");
            top = context.getResources().getDimensionPixelSize(x);
            sStatusHeight = top;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return top;
    }
}

