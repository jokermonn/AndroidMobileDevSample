package com.example.ex0606;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by @author joker on 2017/11/10.
 */
public class MainActivity extends AppCompatActivity {
    private ImageView mTargetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTargetImageView = findViewById(R.id.iv_target);

        final RotateAnimation animation1 = new RotateAnimation(-45F, 45F, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0F);
        animation1.setDuration(3000L);
        animation1.setFillAfter(true);
        final RotateAnimation animation2 = new RotateAnimation(45F, -45F, Animation.RELATIVE_TO_SELF, 0.5F,
                Animation.RELATIVE_TO_SELF, 0F);
        animation2.setDuration(3000L);
        animation2.setFillAfter(true);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTargetImageView.startAnimation(animation2);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        animation2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mTargetImageView.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mTargetImageView.startAnimation(animation1);
    }
}
