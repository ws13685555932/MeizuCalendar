package com.example.wangsheng.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.joda.time.DateTime;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class WeekAdapter extends PagerAdapter {

    private SparseArray<WeekView> weekList = new SparseArray<>();
    private Context mContext;
    private DateTime mInitTime;

    public WeekAdapter(Context context) {
        mContext= context;
        mInitTime = new DateTime(2017,1,2,12,0,0);
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
        WeekView weekView = weekList.get(position);
        if(weekView == null){
            int week = mInitTime.getWeekOfWeekyear();
            weekView = new WeekView(mContext, mInitTime.plusDays((position - week + 1) * 7));
            weekList.put(position,weekView);
        }
        container.addView(weekView);
        return weekView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(weekList.get(position));
    }
}
