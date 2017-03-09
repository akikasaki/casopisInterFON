package com.android.casopisinterfon.interfon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.casopisinterfon.interfon.internet.DownloadInterface;
import com.android.casopisinterfon.interfon.internet.NetworkManager;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DownloadInterface {

    /**
     * Total number of categories on interFON casopis
     */
    private static final int CATEGORY_COUNT = 9;
   // private static final String NOTIFY = "notify";
    public String tabTitles[] = {"Sve","Vesti", "Interesantno", "Nauka", "Kultura", "Intervjui", "Kolumne", "Prakse", "Sport"};
    public static boolean notification;
    CategoryPagerAdapter adapterViewPager;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        NetworkManager manager = NetworkManager.getInstance(this);
        manager.downloadArticles(0, this);
*/
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }

        ArticlesAdapter.mData = DummyData.createDummyData();

        adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(mViewPager);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_expandable, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.miSettings:
                Intent openSettings=new Intent(this,Settings.class);
                startActivity(openSettings);
                return true;
            case R.id.miAboutUs:
                return true;
            case R.id.miContacts:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(Settings.NOTIFICATION_STATE, true)){
            startService(notificationStarter);}
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent notificationStarter= new Intent(this,NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(Settings.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if(prefs.getBoolean(Settings.NOTIFICATION_STATE, true)){
            stopService(notificationStarter);
        }
    }
}


