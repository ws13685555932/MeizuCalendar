package com.example.wangsheng.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class MonthViewPager extends ViewPager {

    public MonthViewPager(Context context) {
        super(context);
        init(context);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context) {
//        final List<CalendarView> callist =new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            DateTime date = new DateTime(2017,i+1,1,12,0,0);
//            CalendarView calendarView = new CalendarView(context,date);
//            callist.add(calendarView);
//        }
//        setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 12;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//
//            @Override
//            public Object instantiateItem(ViewGroup container, int position) {
//                container.addView(callist.get(position));
//                return callist.get(position);
//            }
//
//            @Override
//            public void destroyItem(ViewGroup container, int position, Object object) {
//                container.removeView(callist.get(position));
//            }
//        });
        setAdapter(new MonthAdapter(context));
        setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position == getAdapter().getCount() -1){
                    return ;
                }

                MonthView view = ((MonthAdapter)getAdapter()).getCallist().get(position);
//                Log.d("mytag", "onPageScrolled: "+ view.getHeight());
                MonthView next = ((MonthAdapter)getAdapter()).getCallist().get(position + 1);
                if(next == null){
                    next = ((MonthAdapter)getAdapter()).addMonthView(position + 1);
                }

                ViewGroup.LayoutParams lp = getLayoutParams();
                lp.height = (int) (view.getHeight() + (next.getHeight() - view.getHeight() ) * positionOffset);
                setLayoutParams(lp);


                Log.d("mytag", "position: " + position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.d("mytag", "onMeasure: ");
//        int curIndex = getCurrentItem();
//        View view = getChildAt(curIndex);
//        if(view!= null){
//            view.measure(widthMeasureSpec,heightMeasureSpec);
//            setMeasuredDimension(widthMeasureSpec,view.getMeasuredHeight());
//        }else{
//            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
//        }
//    }
}
