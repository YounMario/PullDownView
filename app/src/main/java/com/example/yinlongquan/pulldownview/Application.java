package com.example.yinlongquan.pulldownview;

import android.content.Context;

/**
 * Created by yinlongquan on 18-6-4.
 */

public class Application extends android.app.Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Context getInstance() {
        return mInstance;
    }
}
