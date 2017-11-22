package com.example.ex0603;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by @author joker on 2017/11/10.
 */
public class MainActivity extends AppCompatActivity {
    private ImageView mImageViewImageView;
    private EditText mNumberEditText;
    private Button mConfirmButton;
    private float target = -1F;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageViewImageView = findViewById(R.id.imageView);
        mNumberEditText = findViewById(R.id.et_number);
        mConfirmButton = findViewById(R.id.btn_confirm);

        mNumberEditText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    target = Float.parseFloat(mNumberEditText.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "请输入有效值", Toast.LENGTH_SHORT).show();
                }
                if (target >= 0 && target <= 1) {
                    mImageViewImageView.setAlpha(target);
                } else {
                    Toast.makeText(MainActivity.this, "请输入0~1任意数", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
