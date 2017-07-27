package com.example.wangsheng.calendar.nestedscrolling;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.lang.UCharacter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import java.util.concurrent.Delayed;

/**
 * Created by wangsheng
 * on 2017/7/24.
 */

public class ChildView extends View implements NestedScrollingChild {

    private float downY;
    private int[] consumed = new int[2];
    private int[] offsetInWindow = new int[2];

    public ChildView(Context context) {
        super(context);
        init();
    }

    public ChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.BLUE);
    }

    Paint paint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        int i = 0;
        for (int j = 0; j < 10; j++) {
            canvas.drawLine(0, i, getWidth(), i, paint);
            i += 100;
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();

                float deltaY = y - downY;
//                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) getLayoutParams();
//                lp.height = (int) (lp.height + 0.6 * deltaY);
//                setLayoutParams(lp);

                setY( getY() + (int) (deltaY));
                downY = y;
                break;
        }
        return true;
    }



    private static final String TAG = "mytag";
}
