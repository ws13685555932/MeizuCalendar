package com.example.wangsheng.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class WeekViewPager extends ViewPager {
    public WeekViewPager(Context context) {
        super(context);
        init(context);
    }

    public WeekViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        setAdapter(new WeekAdapter(context));
    }
}
