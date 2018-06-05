package com.younchen.pulldownmenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yinlongquan on 18-6-4.
 */

public class BalloonView extends View {

    public BalloonView(Context context) {
        super(context);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       return false;
    }
}
