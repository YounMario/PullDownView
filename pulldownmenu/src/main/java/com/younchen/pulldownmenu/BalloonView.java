package com.younchen.pulldownmenu;

import android.content.Context;
import android.content.res.TypedArray;
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

    private static final int BALLOON_DEFAULT_SIZE = 80;
    private static final int BALLOON_LINE_DEFAULT_LENGTH = 120;
    private static final int BALLOON_LINE_DEFAULT_WIDTH = 4;
    private static final int BALLOON_LINE_DEFAULT_COLOR = Color.RED;

    private int mBalloonSize;
    private int mLineLength;
    private int mLineWidth;
    private int mLineColor;

    public BalloonView(Context context) {
        this(context, null);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BalloonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BalloonView);
        mBalloonSize = (int) typedArray.getDimension(R.styleable.BalloonView_size, BALLOON_DEFAULT_SIZE);
        mLineLength = (int) typedArray.getDimension(R.styleable.BalloonView_line_length, BALLOON_LINE_DEFAULT_LENGTH);
        mLineWidth = (int) typedArray.getDimension(R.styleable.BalloonView_line_width, BALLOON_LINE_DEFAULT_WIDTH);
        mLineColor = typedArray.getColor(R.styleable.BalloonView_line_color, BALLOON_LINE_DEFAULT_COLOR);
        typedArray.recycle();
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
        setMeasuredDimension(mBalloonSize, mLineLength + mBalloonSize);
        mBalloonHead.set(0, mLineLength, mBalloonSize,
                mLineLength + mBalloonSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw line
        canvas.save();
        mPaint.setColor(mLineColor);
        canvas.translate((getWidth() - mLineWidth) / 2, 0);
        canvas.drawRect(0, 0, mLineWidth, mLineLength, mPaint);
        canvas.restore();
        //draw
        canvas.save();
        canvas.translate(0, mLineLength - 4);
        mBalloonPaintHead.set(0, 0, mBalloonSize, mBalloonSize);
        canvas.drawBitmap(mBitmap, null, mBalloonPaintHead, mPaint);
        canvas.restore();
    }

    @Override
    public boolean isHintDragArea(int lastTouchX, int lastTouchY) {
        return mBalloonHead.contains(lastTouchX, lastTouchY);
    }


}
