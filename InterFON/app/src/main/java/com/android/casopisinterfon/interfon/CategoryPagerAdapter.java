package com.android.casopisinterfon.interfon;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Aleksa on 4.3.2017.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {
    /**
     * Total number of categories on interFON casopis
     */
    private static final int CATEGORY_COUNT=8;

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //possible addition of arguments
        Fragment fragment = new Fragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return CATEGORY_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
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
        return super.getPageTitle(position);
    }
}
