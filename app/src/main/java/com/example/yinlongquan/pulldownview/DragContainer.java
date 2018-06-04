package com.example.yinlongquan.pulldownview;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by yinlongquan on 18-6-4.
 */

public class DragContainer extends RelativeLayout {

    private int mContentId;
    private int mDragChildId;
    private boolean mIsDragging;

    private ViewDragHelper mViewDragHelper;
    private View mContentView;
    private View mDragView;
    private int mScreenHeight;

    private Point mDragViewOrigin;
    private static final String TAG = "DragContainer";

    Animator mContentOpenAnimator;
    Animator mContentCloseAnimator;
    private Animator mCurrentAnimator;


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
        mScreenHeight = DimenUtils.getWindowHeight();
        typedArray.recycle();
        checkValid();
        initAnimator();


        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                initOriginPositionIfNeed();
                mIsDragging = child.getId() == mDragChildId;
                return mIsDragging;
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return child.getLeft();
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return Math.max(0, Math.min(top, getBottom() - child.getMeasuredHeight()));
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                mIsDragging = false;
                boolean needOpen = releasedChild.getTop() >
                        mScreenHeight / 2 - releasedChild.getMeasuredHeight();
                if (needOpen) {
                    openContentView();
                } else {
                    closeContentView();
                }
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                openingContentView(dy);
            }
        });
    }

    private void initAnimator() {
        mContentOpenAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", 300);
    }

    private void initOriginPositionIfNeed() {
        if (mDragViewOrigin.x == Integer.MIN_VALUE &&
                mDragViewOrigin.y == Integer.MIN_VALUE) {
            mDragViewOrigin.x = mDragView.getLeft();
            mDragViewOrigin.y = mDragView.getTop();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setUpView();
        reset();
    }

    private void openingContentView(int dy) {
        mContentView.setVisibility(VISIBLE);
        mContentView.setTranslationY(mContentView.getTranslationY() + dy);
    }

    private void openContentView() {
//        mContentView.setTranslationY(0);
        Log.d(TAG, "open content view");
        scrollDragView(mDragViewOrigin.x, mScreenHeight);
        mCurrentAnimator = animateTo(mContentView, mContentView.getTranslationY(), 0);
        mCurrentAnimator.start();
    }


    private void closeContentView() {
//        mContentView.setTranslationY(mScreenHeight);
//        mContentView.setVisibility(GONE);


        Log.d(TAG, "close content view");
        scrollDragView(mDragViewOrigin.x, mDragViewOrigin.y);
        mCurrentAnimator = animateTo(mContentView, mContentView.getTranslationY()
                , -mScreenHeight);
        mCurrentAnimator.start();
    }

    private Animator animateTo(View mContentView, float beginTy, float endTy) {
        return ObjectAnimator.ofFloat(mContentView, "translationY", beginTy, endTy);
    }


    private void scrollDragView(int left, int top) {
        mViewDragHelper.settleCapturedViewAt(left, top);
        invalidate();
    }

    private void setUpView() {
        mContentView = findViewById(mContentId);
        mDragView = findViewById(mDragChildId);
        mDragViewOrigin = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private void reset() {
        mContentView.setVisibility(GONE);
        mContentView.setTranslationY(-mScreenHeight);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return mIsDragging;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(false)) {
            invalidate();
        }
    }

    private void checkValid() {
        if (mContentId == -1 || mDragChildId == -1) {
            throw new RuntimeException("content id or dragChild id not set!");
        }
    }


}
