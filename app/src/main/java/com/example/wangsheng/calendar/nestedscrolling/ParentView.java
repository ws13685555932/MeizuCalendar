package com.example.wangsheng.calendar.nestedscrolling;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by wangsheng
 * on 2017/7/24.
 */

public class ParentView extends FrameLayout implements NestedScrollingParent{
    public ParentView(@NonNull Context context) {
        super(context);
    }

    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        final float shouldMoveY = getY() + dy;

        final View parent = (View) getParent();

        int consumedY = 0;

        if(shouldMoveY <= 0){
            consumedY = - (int) getY();
        }else if(shouldMoveY >= parent.getHeight() - getHeight()){
            consumedY =  (int) (parent.getHeight() - getHeight() -getY());
        }else{
            consumedY = dy;
        }

        setY(getY() + consumedY);

        consumed[1] = consumedY;
    }
}

























