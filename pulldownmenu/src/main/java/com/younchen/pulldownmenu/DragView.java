package com.younchen.pulldownmenu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yinlongquan on 18-6-4.
 */

public abstract class DragView extends View {

    public DragView(Context context) {
        super(context);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
       return false;
    }


    public abstract boolean isHintDragArea(int lastTouchX, int lastTouchY);

}
