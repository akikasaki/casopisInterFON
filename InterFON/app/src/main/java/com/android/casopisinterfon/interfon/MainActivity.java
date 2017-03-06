package com.android.casopisinterfon.interfon;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;
import com.android.casopisinterfon.interfon.internet.NetworkManager;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DownloadInterface {

    /**
     * Total number of categories on interFON casopis
     */
    private static final int CATEGORY_COUNT = 8;
    public String tabTitles[] = {"Vesti", "Interesantno", "Nauka", "Kultura", "Intervjui", "Kolumne", "Prakse", "Sport"};


    CategoryPagerAdapter adapterViewPager;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkManager manager = NetworkManager.getInstance(this);
        manager.downloadArticles(0, this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
//        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = mTabLayout.getTabAt(i);
//            tab.setCustomView(getTabView(i));
//        }


        ArticlesAdapter.mData = DummyData.createDummyData();
    }

//    protected View getTabView(int position) {
//        View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
//        TextView tv = (TextView) tab.findViewById(R.id.custom_text);
//        tv.setText(tabTitles[position]);
//        return tab;
//    }

    @Override
    public void onDownloadSuccess(JSONObject response) {
//        try {
////            JSONArray array = response.getJSONArray(Article.POSTS);
//            int size = array.length();
//            for (int i = 0; i < size; i++) {
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onDownloadFailed(String error) {

    }


    public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
        public CategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CategoryFragment.getInstance(position);
        }

        @Override
        public int getCount() {
            return CATEGORY_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}

