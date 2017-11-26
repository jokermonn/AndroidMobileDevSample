package com.joker.ex0713;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by @author joker on 2017/11/26.
 */
public class MainActivity extends AppCompatActivity {
    private static final Item[] URLS = {
            new Item("http://news.163.com/", "网易新闻"),
            new Item("http://news.sina.com.cn/", "新浪新闻"),
            new Item("http://www.ifeng.com/", "凤凰网"),
    };
    private TabLayout mTitleTb;
    private ViewPager mContentVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTitleTb = findViewById(R.id.tb_title);
        mContentVp = findViewById(R.id.vp_content);
        mContentVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return URLS[position].title;
            }

            @Override
            public Fragment getItem(int position) {
                return ContentFragment.newInstance(URLS[position].url);
            }

            @Override
            public int getCount() {
                return URLS.length;
            }
        });
        mTitleTb.setupWithViewPager(mContentVp);
    }
}
