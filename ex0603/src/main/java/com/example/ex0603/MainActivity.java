package com.example.ex0603;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

        mNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    target = Float.parseFloat(charSequence.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mConfirmButton.setEnabled(false);
                }
                if (target >= 0 && target <= 1) {
                    mConfirmButton.setEnabled(true);
                } else {
                    mConfirmButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageViewImageView.setAlpha(target);
            }
        });
    }
}
