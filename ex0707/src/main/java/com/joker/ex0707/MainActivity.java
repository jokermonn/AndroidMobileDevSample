package com.joker.ex0707;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by @author joker on 2017/11/22.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String IMAGE_URL = "https://timgsa.baidu" +
            ".com/timg?image&quality=80&size=b9999_10000&sec=1511373035057&di" +
            "=e4eeb83d0bed6df21d5b62f31d25d25a&imgtype=0&src=http%3A%2F%2Fatt.x2.hiapk" +
            ".com%2Fforum%2Fmonth_1009%2F1009271254f2c388624a5d4970.jpg";
    private ImageView mContentIv;
    private Button mSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContentIv = findViewById(R.id.iv_content);
        mSaveBtn = findViewById(R.id.btn_save);
        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(IMAGE_URL);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = connection.getInputStream();

                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            byte[] buffer = new byte[1024];
                            int len;
                            while ((len = inputStream.read(buffer)) > -1) {
                                baos.write(buffer, 0, len);
                            }
                            baos.flush();

                            InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
                            File file = new File(getCacheDir() + "/test.jpg");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            int read = 0;
                            while ((read = stream1.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, read);
                            }

                            InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
                            final Bitmap bitmap = BitmapFactory.decodeStream(stream2);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mContentIv.setImageBitmap(bitmap);
                                }
                            });
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                break;
            default:
                break;
        }
    }
}
