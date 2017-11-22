package com.example.ex0607;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by @author joker on 2017/11/10.
 */

public class Circle extends View {
    private Paint mPaint = new Paint();

    public Circle(Context context) {
        super(context);
        init();
    }

    public Circle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Circle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5F);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int cx = canvas.getWidth() / 2;
        int cy = canvas.getHeight() / 2;
        int radius = cx - 10;
        canvas.drawCircle(cx, cy, radius, mPaint);

        canvas.translate(cx, cy);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(50F);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(0, 0, mPaint);

        mPaint.setStrokeWidth(5F);
        int offset = 360 / 60;
        for (int i = 1; i <= 60; i++) {
            canvas.rotate(offset);
            if (i % 5 == 0) {
                canvas.drawLine(0, cy - 30, 0, radius, mPaint);
            } else {
                canvas.drawLine(0, cy - 20, 0, radius, mPaint);
            }
        }


    }
}
