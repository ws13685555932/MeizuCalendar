package com.example.wangsheng.calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class MonthAdapter extends PagerAdapter {

    private SparseArray<MonthView> callist = new SparseArray<>();
    private Context mContext;
    private DateTime mDateTime;

    public MonthAdapter(Context context) {
        mContext = context;
        mDateTime = new DateTime(2017,1,1,12,0,0);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MonthView monthView = callist.get(position);
        if(monthView == null) {
            monthView = addMonthView(position);
        }
        container.addView(monthView);
        return monthView;
    }

    @NonNull
    public MonthView addMonthView(int position) {
        MonthView monthView;
        int month = mDateTime.getMonthOfYear();
        int diff = position +1 - month;
        monthView = new MonthView(mContext, mDateTime.plusMonths(diff));
        callist.put(position, monthView);
        return monthView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public SparseArray<MonthView> getCallist(){
        return callist;
    }


}
