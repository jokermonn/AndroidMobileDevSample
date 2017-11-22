package com.example.ex0607;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by @author joker on 2017/11/10.
 */
public class MainActivity extends AppCompatActivity {
    private Circle mViewCircle;
    private ImageView mHour;
    private ImageView mMin;
    private ImageView mSec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewCircle = findViewById(R.id.view);
        mHour = findViewById(R.id.iv_hour);
        mMin = findViewById(R.id.iv_min);
        mSec = findViewById(R.id.iv_sec);
        mViewCircle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                SimpleDateFormat sDateFormat = new SimpleDateFormat("hh:mm:ss");
                String date = sDateFormat.format(new Date());
                String h = date.substring(0, 2);
                String m = date.substring(3, 5);
                String s = date.substring(6, 8);

                float hPer = Integer.parseInt(h) / 12F * 360F;
                mHour.setRotation(hPer);
                float mPer = Integer.parseInt(m) / 60F * 360F;
                mMin.setRotation(mPer);
                float sPer = Integer.parseInt(s) / 60F * 360F;
                mSec.setRotation(sPer);

                RotateAnimation animation1 = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5F);
                animation1.setDuration(Long.MAX_VALUE);
                animation1.setRepeatCount(-1);
                animation1.setInterpolator(new LinearInterpolator());
                mHour.startAnimation(animation1);

                RotateAnimation animation2 = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5F);
                animation2.setDuration(60 * 1000 * 60);
                animation2.setRepeatCount(-1);
                animation2.setInterpolator(new LinearInterpolator());
                mMin.startAnimation(animation2);

                RotateAnimation animation3 = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5F,
                        Animation.RELATIVE_TO_SELF, 0.5F);
                animation3.setDuration(60 * 1000);
                animation3.setInterpolator(new LinearInterpolator());
                animation3.setRepeatCount(-1);
                mSec.startAnimation(animation3);

                mViewCircle.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}
