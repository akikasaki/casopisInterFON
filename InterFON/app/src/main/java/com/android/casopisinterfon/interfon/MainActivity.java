package com.android.casopisinterfon.interfon;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;
import com.android.casopisinterfon.interfon.internet.NetworkManager;
import com.android.casopisinterfon.interfon.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DownloadInterface{
    List<String> list = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
   // String myDataset[]= {"1", "2", "3", "4 ", "5", "6", "7", "8"};
    CategoryPagerAdapter adapterViewPager;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(arrayAdapter);
        
        NetworkManager manager = NetworkManager.getInstance(this);
        manager.downloadArticles(0, this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        mViewPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        /*for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setCustomView(CategoryPagerAdapter.getTabView(i));
        }*/
    }


    public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
        /**
         * Total number of categories on interFON casopis
         */
        private static final int CATEGORY_COUNT=8;
        String tabTitles[] = new String[] { "Vesti", "Interesantno", "Nauka" ,"Kultura", "Intervjui", "Kolumne", "Prakse", "Sport"};
        Context context;
        public CategoryPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            //possible addition of arguments
            switch (position) {
                case 0:
                    return new CategoryFragment();
                case 1:
                    return new CategoryFragment2();
                case 2:
                    return new CategoryFragment3();
                case 3:
                    return new CategoryFragment4();
                case 4:
                    return new CategoryFragment5();
                case 5:
                    return new CategoryFragment6();
                case 6:
                    return new CategoryFragment7();
                case 7:
                    return new CategoryFragment8();
            }

            return null;
        }

        @Override
        public int getCount() {
            return CATEGORY_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
           /* switch(position)
            {
                case 0:
                    return "Vesti";
                case 1:
                    return "Interesantno";
                case 2:
                    return "Nauka";
                case 3:
                    return "Kultura";
                case 4:
                    return "Intervjui";
                case 5:
                    return "Kolumne";
                case 6:
                    return "Prakse";
                case 7:
                    return "Sport";
            }
            return super.getPageTitle(position);*/
            return tabTitles[position];
        }
        public View getTabView(int position) {
            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }

    }


    @Override
    public void onDownloadSuccess(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray(Article.POSTS);
            int size = array.length();
            for (int i = 0; i < size; i++) {
                list.add(array.getJSONObject(i).getString(Article.POST_TITLE));
            }
            arrayAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDownloadFailed(String error) {

    }
}

