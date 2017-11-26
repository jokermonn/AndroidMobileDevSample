package com.example.ex0704;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by @author joker on 2017/11/12.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] URLS = {
            "http://static.ws.126.net/cnews/img/photocenter/logo_newsphoto.png",
            "https://www.baidu.com/img/bd_logo1.png",
            "http://n.sinaimg.cn/news/1_img/cfp/d2808720/20171125/GrtE-fypathz5771495.jpg",
            "https://img.alicdn.com/tfs/TB1hwPxefDH8KJjy1XcXXcpdXXa-520-280.jpg_q90_.webp",
            "https://img30.360buyimg.com/da/jfs/t4519/270/3593018818/142359/66551a05/58ffffe3N27fe1222.jpg"
    };
    private ImageView mNeteaseIv;
    private ImageView mBaiduIv;
    private ImageView mSinaIv;
    private ImageView mTaobaoIv;
    private ImageView mGoudongIv;
    private ImageView[] mImageViews;
    public ExecutorService mThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mImageViews = new ImageView[5];
        mImageViews[0] = mNeteaseIv;
        mImageViews[1] = mBaiduIv;
        mImageViews[2] = mSinaIv;
        mImageViews[3] = mTaobaoIv;
        mImageViews[4] = mGoudongIv;
        mThreadPool = Executors.newFixedThreadPool(5);
    }

    private void initView() {
        mNeteaseIv = findViewById(R.id.iv_netease);
        mBaiduIv = findViewById(R.id.iv_baidu);
        mSinaIv = findViewById(R.id.iv_sina);
        mTaobaoIv = findViewById(R.id.iv_taobao);
        mGoudongIv = findViewById(R.id.iv_goudong);
        Button netBtn = findViewById(R.id.btn_net);
        netBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_net:
                for (int i = 0; i < mImageViews.length; i++) {
                    final int finalI = i;
                    mThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            InputStream stream = imageInputStream(URLS[finalI]);
                            final Bitmap bitmap = BitmapFactory.decodeStream(stream);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mImageViews[finalI].setImageBitmap(bitmap);
                                }
                            });
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private InputStream imageInputStream(String url) {
        InputStream stream = null;
        try {
            URL imageUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
            stream = connection.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }
}
