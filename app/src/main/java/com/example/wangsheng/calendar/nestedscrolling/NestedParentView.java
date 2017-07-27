package com.example.wangsheng.calendar.nestedscrolling;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.Arrays;

public class NestedParentView extends LinearLayout implements NestedScrollingParent {

    private Context mContext;
    public static final String TAG = "mytag";

    private NestedScrollingParentHelper parentHelper;

    public NestedParentView(Context context) {
        super(context);
        mContext = context;
    }

    public NestedParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    {
        parentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        Log.d(TAG, String.format("onStartNestedScroll, child = %s, target = %s, nestedScrollAxes = %d", child, target, nestedScrollAxes));
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
//        Log.d(TAG, String.format("onNestedScrollAccepted, child = %s, target = %s, nestedScrollAxes = %d", child, target, nestedScrollAxes));
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
//        Log.d(TAG, "onStopNestedScroll");
        parentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        Log.d(TAG, String.format("onNestedScroll, dxConsumed = %d, dyConsumed = %d, dxUnconsumed = %d, dyUnconsumed = %d", dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed));
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        View child = getChildAt(0);
        int height = child.getHeight();

        Log.d(TAG, "child height: " + height);

        if(Math.abs(dy) >= 100){
            consumed[1] = 0;
        }else{
            consumed[1] = dy;
            LinearLayout.LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int addHeight = (int) (0.4 * dy);
            if(lp.height >= 500) {
                lp.height = lp.height + addHeight;
                int scrollY = (int) (- 0.6 * dy);
                scrollBy(0,scrollY);
                getChildAt(0).setLayoutParams(lp);
            }
            Log.d(TAG, "lp.height" + lp.height);

        }

//        Log.d(TAG, String.format("onNestedPreScroll, dx = %d, dy = %d, consumed = %s", dx, dy, Arrays.toString(consumed)));
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        Log.d(TAG, String.format("onNestedFling, velocityX = %f, velocityY = %f, consumed = %b", velocityX, velocityY, consumed));
        return true;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
//        Log.d(TAG, String.format("onNestedPreFling, velocityX = %f, velocityY = %f", velocityX, velocityY));
        return true;
    }

    @Override
    public int getNestedScrollAxes() {
//        Log.d(TAG, "getNestedScrollAxes");
        return parentHelper.getNestedScrollAxes();
    }
}