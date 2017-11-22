package com.example.ex0605;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by @author joker on 2017/11/10.
 */
public class MainActivity extends AppCompatActivity {
    private float scale = 0.1F;
    private int maxWidth = 0;
    private int maxHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ConstraintLayout contentCL = findViewById(R.id.cl_content);
        final ImageView targetIV = findViewById(R.id.iv_target);

        contentCL.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                maxWidth = contentCL.getWidth();
                maxHeight = contentCL.getHeight();
                contentCL.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        contentCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float scaleX = targetIV.getScaleX() + scale;
                float scaleY = targetIV.getScaleY() + scale;
                if (targetIV.getWidth() * scaleX <= maxWidth && targetIV.getHeight() * scaleY <=
                        maxHeight) {
                    targetIV.setScaleX(scaleX);
                    targetIV.setScaleY(scaleY);
                }
            }
        });
    }
}
