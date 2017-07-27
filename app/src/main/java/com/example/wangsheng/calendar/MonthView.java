package com.example.wangsheng.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.lang.UCharacter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangsheng
 * on 2017/7/22.
 */

public class MonthView extends View {
    private static final String TAG = "mytag";

    private Paint mTextPaint;
    private Paint mBgPaint;
    private DateTime mInitDate;
    private int mItemHorMargin;
    private int mItemVerMargin;
    private int mItemVerPadding;
    private int mItemHorPadding;
    private int mItemHeight;
    private int mItemWidth;
    private DateTime nowDate;
    private int period;

    private DateTime mChosenTime;

    private int mRows;

    private List<Rect> dateList = new ArrayList<>();
    private List<Rect> lunerList = new ArrayList<>();
    private List<Rect> drawRectList = new ArrayList<>();


    public MonthView(Context context) {
        super(context);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MonthView(Context context, DateTime initDate) {
        super(context);
        init();
        this.mInitDate = initDate;
        this.nowDate = new DateTime();
        mChosenTime = mInitDate;
    }


    private void init() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(5);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(45);

        mBgPaint = new Paint();
        mBgPaint.setColor(Color.parseColor("#eeedef"));
        mBgPaint.setAntiAlias(true);

        mItemHorMargin = 20;
        mItemVerMargin = 30;
        mItemVerPadding = 10;
        mItemHorPadding = 10;

        mChosenTime = mInitDate;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawChosenDate(canvas);
        drawDate(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "monthview onMeasure");
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mRows = calRows();

        mItemHeight = 140;
        mItemWidth = (width - 8 * mItemHorMargin) / 7;

        height = (mRows + 1) * mItemVerMargin + mRows * mItemHeight;

        setRects(mRows);

        setMeasuredDimension(width, height);
    }

    private void setRects(int rows) {
        int x = mItemHorMargin, y = mItemVerMargin;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 7; j++) {
                Rect item = new Rect(x, y, x + mItemWidth, y + mItemHeight);
                drawRectList.add(item);
                Rect dateRect = new Rect(item.left + mItemHorPadding,
                        item.top + mItemVerPadding, item.right - mItemHorPadding, item.top + mItemVerPadding + 70);
                dateList.add(dateRect);
                Rect lunerRect = new Rect(dateRect.left, dateRect.bottom, dateRect.right, dateRect.bottom + 30);
                lunerList.add(lunerRect);
                x += mItemWidth + mItemHorMargin;
            }
            y += mItemHeight + mItemVerMargin;
            x = mItemHorMargin;
        }
    }

    private int calRows() {
        DateTime firstDayOfMonth = this.mInitDate;
        DateTime nextMonth = firstDayOfMonth.plusMonths(1);
        Period per = new Period(firstDayOfMonth, nextMonth, PeriodType.days());
        period = per.getDays();

        int startWeek = firstDayOfMonth.getWeekOfWeekyear();
        int endWeek = nextMonth.minusDays(1).getWeekOfWeekyear();
        if (startWeek > endWeek) {
            startWeek = 0;
        }
        return endWeek - startWeek + 1;
    }

    private void drawDate(Canvas canvas) {

        int startIndex = mInitDate.getDayOfWeek();

        for (int i = 0; i < period; i++) {
            mTextPaint.setTextSize(45);
            Paint.FontMetricsInt fm = mTextPaint.getFontMetricsInt();
            Rect dateRect = dateList.get(i + startIndex - 1);
            int baseline = (dateRect.top + dateRect.bottom - fm.bottom - fm.top) / 2;
            if ((i + 1) == nowDate.getDayOfMonth()) {
                mTextPaint.setColor(Color.parseColor("#e45345"));
            } else {
                mTextPaint.setColor(Color.BLACK);
            }
            canvas.drawText(i + 1 + "", (dateRect.left + dateRect.right) / 2, baseline, mTextPaint);
            Rect lunerRect = lunerList.get(i + startIndex - 1);
            baseline = (lunerRect.top + lunerRect.bottom - fm.bottom - fm.top) / 2;
            mTextPaint.setTextSize(28);
            canvas.drawText("初一", (dateRect.left + dateRect.right) / 2, baseline, mTextPaint);
        }


    }

    private void drawChosenDate(Canvas canvas) {
        int startWeekDay = mInitDate.getDayOfWeek();
        int chosenDay = mChosenTime.getDayOfMonth();
        Log.d(TAG, "drawChosenDate: " + chosenDay);

        Rect chosenArea = drawRectList.get(chosenDay + startWeekDay - 2);
        canvas.drawRect(chosenArea, mBgPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                int index = getIndexFromXY(x, y);
                Log.d(TAG, "onTouchEvent: " + index);
                mChosenTime = getDateTimeByIndex(index);
                break;
        }
        postInvalidate();
        return true;
    }

    private DateTime getDateTimeByIndex(int index) {
        int startIndex = mInitDate.getDayOfWeek();
        int diff = index - startIndex;
        if (diff < 0 || diff >= period) {
            return mChosenTime;
        } else {
            return mInitDate.plusDays(diff);
        }
    }

    private int getIndexFromXY(int x, int y) {
        int col = x / (mItemHorMargin + mItemWidth) + 1;
        int row = y / (mItemVerMargin + mItemHeight);
        int index = row * 7 + col;
        return index;
    }


}
