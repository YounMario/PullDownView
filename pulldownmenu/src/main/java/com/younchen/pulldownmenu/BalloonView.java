package com.younchen.pulldownmenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.pulldownmenu.R;

/**
 * Created by yinlongquan on 18-6-5.
 */

public class BalloonView extends DragView {

    private Bitmap mBitmap;
    private Paint mPaint;
    private Rect mBalloonHead;
    private Rect mBalloonPaintHead;

    private static final int BALLOON_SIZE = 80;
    private static final int BALLOON_LINE_LENGTH = 120;
    private static final int BALLOON_LINE_WIDTH = 4;
    private static final int BALLOON_LINE_COLOR = Color.GRAY;

    public BalloonView(Context context) {
        this(context, null);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_round);
        mBalloonPaintHead = new Rect();
        mBalloonHead = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //todo
        setMeasuredDimension(BALLOON_SIZE, BALLOON_LINE_LENGTH + BALLOON_SIZE);
        mBalloonHead.set(0, BALLOON_LINE_LENGTH, BALLOON_SIZE, BALLOON_LINE_LENGTH + BALLOON_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw line
        canvas.save();
        mPaint.setColor(BALLOON_LINE_COLOR);
        canvas.translate((getWidth() - BALLOON_LINE_WIDTH) / 2, 0);
        canvas.drawRect(0, 0, BALLOON_LINE_WIDTH, BALLOON_LINE_LENGTH, mPaint);
        canvas.restore();
        //draw
        canvas.save();
        canvas.translate(0, BALLOON_LINE_LENGTH - 8);
        mBalloonPaintHead.set(0, 0, BALLOON_SIZE, BALLOON_SIZE);
        canvas.drawBitmap(mBitmap, null, mBalloonPaintHead, mPaint);
        canvas.restore();
    }

    @Override
    public boolean isHintDragArea(int lastTouchX, int lastTouchY) {
        return mBalloonHead.contains(lastTouchX, lastTouchY);
    }


}
