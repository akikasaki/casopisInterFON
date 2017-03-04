package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    CategoryPagerAdapter adapterViewPager;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            ViewPager vpPager = (ViewPager) findViewById(R.id.vpCategory);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
            mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
            adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager());
            vpPager.setAdapter(adapterViewPager);
            mTabLayout.setupWithViewPager(mViewPager);
        }

    }

