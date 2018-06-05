package com.younchen.pulldownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.example.pulldownmenu.R;

/**
 * Created by yinlongquan on 18-6-4.
 */

public class DragContainer extends ViewGroup {

    private int mContentId;
    private int mDragChildId;
    private boolean mIsDragging;

    private View mContentView;
    private DragView mDragView;

    private static final String TAG = "DragContainer";


    private float mTouchx;
    private float mTouchy;

    private Scroller mScroller;

    private static final int OPENED = 0;
    private static final int OPENING = 1;
    private static final int CLOSED = 2;
    private int mContentStatus = CLOSED;

    public DragContainer(Context context) {
        this(context, null);
    }

    public DragContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DragContainer);
        mDragChildId = typedArray.getResourceId(R.styleable.DragContainer_drag_child_id, -1);
        mContentId = typedArray.getResourceId(R.styleable.DragContainer_content_view_id, -1);
        typedArray.recycle();
        checkValid();
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpView();
        reset();
    }

    private void openContentView() {
        Log.d(TAG, "open content view");
        int scrollY = getScrollY();
        //scrollY startY , dy distance in y axis
        mScroller.startScroll(0, scrollY, 0, -mContentView.getMeasuredHeight() - scrollY);
        invalidate();
        updateStatus(OPENED);
    }

    private void updateStatus(int status) {
        mContentStatus = status;
    }

    public void closeContentView() {
        Log.d(TAG, "close content view");
        int scrollY = getScrollY();
        mScroller.startScroll(0, scrollY, 0, -scrollY);
        invalidate();
        updateStatus(CLOSED);
    }


    private void setUpView() {
        mContentView = findViewById(mContentId);
        mDragView = findViewById(mDragChildId);
    }

    private void reset() {
        closeContentView();
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mIsDragging;
    }

    private boolean isContentOpened() {
        return mContentStatus == OPENED;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mDragView != null) {
            mDragView.layout(l, t, l + mDragView.getMeasuredWidth(),
                    t + mDragView.getMeasuredHeight());
        }
        if (mContentView != null) {
            mContentView.layout(0, -mContentView.getMeasuredHeight(), mContentView.getMeasuredWidth(), 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChild(mDragView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int lastTouchX = (int) event.getX();
        int lastTouchY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mDragView.isHintDragArea(lastTouchX,lastTouchY)) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    mIsDragging = true;
                    mTouchx = event.getX();
                    mTouchy = event.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsDragging) {
                    openingContentView(-lastTouchY);
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragging = false;
                boolean needOpen = lastTouchY - mTouchy >
                        mContentView.getMeasuredHeight() / 2 - mDragView.getMeasuredHeight();
                if (needOpen) {
                    openContentView();
                } else {
                    closeContentView();
                }
                break;
        }
        return mIsDragging;
    }

    private void openingContentView(int lastTouchY) {
        scrollTo((int) getX(), lastTouchY);
        updateStatus(OPENING);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo((int) getX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    private void checkValid() {
        if (mContentId == -1 || mDragChildId == -1) {
            throw new RuntimeException("content id or dragChild id not set!");
        }
    }

}
