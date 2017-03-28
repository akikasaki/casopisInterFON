package com.android.casopisinterfon.interfon.activity;

import android.content.Context;
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

import com.android.casopisinterfon.interfon.NotificationService;
import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.activity.fragment.ArticlesFragment;
import com.android.casopisinterfon.interfon.internet.NetworkManager;

public class MainActivity extends AppCompatActivity {

    /**
     * Total number of categories on interFON casopis
     */
    private static final int CATEGORY_COUNT = 9;
    // private static final String NOTIFY = "notify";
    public String tabTitles[] = {"Sve", "Vesti", "Interesantno", "Nauka", "Kultura", "Intervjui", "Kolumne", "Prakse", "Sport"};
    public static boolean notification;
    CategoryPagerAdapter adapterViewPager;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    Toolbar mToolbar;

   private static Context context;

//    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //For getting Context to pass into classes via getAppContext Method
        MainActivity.context = getApplicationContext();
        downloadFreshData();
        init();
    }

    private void downloadFreshData() {
        NetworkManager manager = NetworkManager.getInstance(this);
        manager.downloadArticles(0, true);
    }

    private void init() {
//        mDataManager = DataManager.getInstance();

//        //for fetching DummyArticles for debugging
//        DataSaver datasaver = new DataSaver(getApplicationContext());
//        datasaver.saveData();
//        DataLoader dataLoader = new DataLoader(getApplicationContext());
//        mDataManager.setData(dataLoader.readData());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        
        adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * Method for retrieving current active fragment position.
     * @return fragment position.
     */
    public int getActiveFragPosition(){
        return mViewPager.getCurrentItem();
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
                Intent openSettings = new Intent(this, SettingsActivity.class);
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

    public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

        public CategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticlesFragment.getInstance(position);
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
    protected void onStart() {
        super.onStart();
        Intent notificationStarter = new Intent(this, NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
            stopService(notificationStarter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent notificationStarter = new Intent(this, NotificationService.class);
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
            startService(notificationStarter);
        }
    }

    public static Context getAppContext(){
        return MainActivity.context;
    }

}


