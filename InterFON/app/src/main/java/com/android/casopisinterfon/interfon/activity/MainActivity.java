package com.android.casopisinterfon.interfon.activity;

import android.content.Intent;
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

import com.android.casopisinterfon.interfon.R;
import com.android.casopisinterfon.interfon.activity.fragment.ArticlesFragment;
import com.android.casopisinterfon.interfon.internet.NetworkManager;
import com.android.casopisinterfon.interfon.model.Category;

import static com.android.casopisinterfon.interfon.model.Category.CATEGORY_COUNT;

public class MainActivity extends AppCompatActivity {


    private CategoryPagerAdapter adapterViewPager;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    private void init() {
//        mDataManager = DataManager.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.vpCategory);

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        getSupportActionBar().setIcon(R.drawable.ic_logo1);

        adapterViewPager = new CategoryPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setIcon(R.drawable.home_tab_icon).setText(null);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    /**
     * Method for retrieving current active fragment position.
     *
     * @return fragment position.
     */
    public int getActiveFragPosition() {
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
        switch (item.getItemId()) {
            case R.id.miSettings:
                Intent openSettings = new Intent(this, SettingsActivity.class);
                startActivity(openSettings);
                return true;
            case R.id.miAboutUs:
                Intent openAboutUs = new Intent(this, AboutActivity.class);
                startActivity(openAboutUs);
                return true;
            case R.id.miContacts:
                Intent openContacts = new Intent(this, ContactUsActivity.class);
                startActivity(openContacts);
                return true;
            case R.id.miBookmarks:
                Intent openBookmarks = new Intent(this, BookmarksActivity.class);
                startActivity(openBookmarks);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
//        Intent notificationStarter = new Intent(this, NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
//            stopService(notificationStarter);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent notificationStarter = new Intent(this, NotificationService.class);
//        SharedPreferences prefs = getSharedPreferences(SettingsActivity.NOTIFICATION_TOGGLE, MODE_PRIVATE);
//        if (prefs.getBoolean(SettingsActivity.NOTIFICATION_STATE, true)) {
//            startService(notificationStarter);
//        }
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
            return Category.CATEGORY_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Category.tabTitles[position];
        }
    }
}



