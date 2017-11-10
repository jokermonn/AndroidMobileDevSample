package com.example.ex0604;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by @author joker on 2017/11/10.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout contentCL = findViewById(R.id.cl_content);
        final ImageView targetIV = findViewById(R.id.iv_target);
        final int maxWidth = contentCL.getWidth();
        final int maxHeight = contentCL.getHeight();

        contentCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float targetX = targetIV.getX();
                float targetY = targetIV.getY();
                if (targetX < maxWidth && targetY < maxHeight) {
                    targetIV.setX(targetX + 10F);
                } else if (targetX >= maxWidth && targetY < maxHeight) {
                    targetIV.setY(targetY + 10F);
                } else {
                    targetIV.setX(0F);
                    targetIV.setY(0F);
                }
            }
        });
    }
}
