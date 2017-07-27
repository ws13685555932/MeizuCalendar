package com.example.wangsheng.calendar;

import android.content.Context;
import android.content.pm.PermissionInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsheng
 * on 2017/7/23.
 */

public class WeekView extends View {

    private Paint mPaint;
    private Paint mBgPaint;
    private Context mContext;
    private DateTime mInitTime;

    private int mItemHorMargin;
    private int mItemVerMargin;
    private int mItemVerPadding;
    private int mItemHorPadding;
    private int mItemHeight;
    private int mItemWidth;
    private int mIndex = 1;

    private DateTime mChosenTime;

    private List<Rect> dateList = new ArrayList<>();
    private List<Rect> lunerList = new ArrayList<>();
    private List<Rect> drawRectList = new ArrayList<>();

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

    public WeekView(Context context, DateTime dateTime) {
        super(context);
        init(context);
        this.mContext = context;
        int weekDay = dateTime.getDayOfWeek();
        this.mInitTime = dateTime.minus(weekDay -1);
        mChosenTime = mInitTime;
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setTextSize(45);
        mPaint.setStrokeWidth(5);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.parseColor("#eeedef"));
        mBgPaint.setAntiAlias(true);

        mItemHorMargin = 20;
        mItemVerMargin = 30;
        mItemVerPadding = 10;
        mItemHorPadding = 10;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);

        mItemHeight = 140;
        mItemWidth = (width - 8 * mItemHorMargin) / 7;

        int height = 2 * mItemVerMargin + mItemHeight;

        setRects();

        setMeasuredDimension(widthMeasureSpec, height);
    }

    private void setRects() {
        int x = mItemHorMargin;

        for (int j = 0; j < 7; j++) {
            Rect item = new Rect(x, mItemVerMargin, x + mItemWidth, mItemVerMargin + mItemHeight);
            drawRectList.add(item);
            Rect dateRect = new Rect(item.left + mItemHorPadding,
                    item.top + mItemVerPadding, item.right - mItemHorPadding, item.top + mItemVerPadding + 70);
            dateList.add(dateRect);
            Rect lunerRect = new Rect(dateRect.left, dateRect.bottom, dateRect.right, dateRect.bottom + 30);
            lunerList.add(lunerRect);
            x += mItemWidth + mItemHorMargin;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawChosenDay(canvas);
        drawDate(canvas);
    }

    private void drawChosenDay(Canvas canvas) {

        Log.d(TAG, "drawChosenDay: " + (mIndex - 1));
        canvas.drawRect(drawRectList.get(mIndex-1),mBgPaint);
    }

    private static final String TAG = "mytag";

    private void drawDate(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            DateTime dt = mInitTime.plusDays(i);
            Rect dateRect = dateList.get(i);
            mPaint.setTextSize(45);
            Paint.FontMetricsInt fm = mPaint.getFontMetricsInt();
            int baseline = (dateRect.top + dateRect.bottom - fm.bottom - fm.top) / 2;
            if(Utils.isToday(dt)){
                mPaint.setColor(Color.parseColor("#e45345"));
            }else{
                mPaint.setColor(Color.BLACK);
            }
            canvas.drawText(dt.getDayOfMonth() + "", (dateRect.left + dateRect.right) / 2, baseline, mPaint);

            Rect lunerRect = lunerList.get(i);
            mPaint.setTextSize(28);
            baseline = (lunerRect.top + lunerRect.bottom - fm.bottom - fm.top) / 2;
            canvas.drawText("十五", (lunerRect.left + lunerRect.right) / 2, baseline, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                mIndex = getIndexFromX(x);
                Log.d("mytag", "onTouchEvent: " + mIndex);
                mChosenTime = mInitTime.plus(mIndex - 1);
                break;
        }
        postInvalidate();
        return true;
    }

    private int getIndexFromX(int x) {
        int col = x / (mItemWidth + mItemHorMargin);
        return col + 1;
    }
}


















