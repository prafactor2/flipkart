package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lord Voldemort on 14/11/2015.
 */
public class ShopSearchPagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;
    int PAGE_COUNT;
    String tabTitles[];
    private Context context;
    public ShopSearchPagerAdapter(FragmentManager fm,String title[], int num) {
        super(fm);
        this.tabTitles = title;
        this.PAGE_COUNT = num;

    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0)
            fragment = new ShopSearchFragment1();
        else if (i == 1)
            fragment = new ShopSearchFragment2();

        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
