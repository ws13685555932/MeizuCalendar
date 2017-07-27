package com.example.wangsheng.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class WeekView extends View {

    private Paint mPaint;
    private Context mContext;
    private DateTime mInitTime;

    public WeekView(Context context) {
        super(context);
        init(context);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeekView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public WeekView(Context context, DateTime dateTime){
        super(context);
        init(context);
        this.mContext = context;
        this.mInitTime = dateTime;
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int x = 0;
        int itemHeight = 150,itemWidth = getWidth()/7;

        int weekDay = mInitTime.getDayOfWeek();
        DateTime dateTime = mInitTime.minus(weekDay);

        for (int i = 0; i < 7; i++) {
            int day = dateTime.getDayOfMonth();
            Rect rect = new Rect(x,0,x+itemWidth,itemHeight);
            Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
            int baseline = (rect.top + rect.bottom - fm.bottom - fm.top) / 2;
            canvas.drawText(day+"", (rect.left + rect.right) / 2, baseline, mPaint);
            dateTime = dateTime.plusDays(1);
            x+= itemWidth;
        }

    }
}


















