package com.joker.ex0706;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
    private static final String NET_URL = "http://www.163.com/";
    private TextView mContentTv;
    private Button mGetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mContentTv = findViewById(R.id.tv_content);
        mGetBtn = findViewById(R.id.btn_get);
        mGetBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(NET_URL);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = urlConnection.getInputStream();
                            int read = 0;
                            byte[] buffer = new byte[1024];
                            StringBuilder stringBuilder = new StringBuilder();
                            while ((read = inputStream.read(buffer)) != -1) {
                                stringBuilder.append(new String(buffer, 0, read));
                            }
                            Document doc = Jsoup.parse(stringBuilder.toString());
                            stringBuilder.delete(0, stringBuilder.length());
                            Elements links = doc.select("link[href]");
                            for (Element link : links) {
                                stringBuilder.append(link.attr("href")).append("\n");
                            }
                            final File file = new File(getCacheDir() + "/link.txt");
                            final String s = stringBuilder.toString();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mContentTv.setText(mContentTv.getText() + file.getPath() + "\n内容为：\n"
                                            + s);
                                }
                            });
                            byte[] bytes = s.getBytes();
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(bytes);
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
