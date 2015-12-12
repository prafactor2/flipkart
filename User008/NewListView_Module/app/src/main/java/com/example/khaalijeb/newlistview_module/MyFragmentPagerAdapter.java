package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Rajdeep Singh on 22-05-2015.
 *
 * for wallet activity
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;
    int PAGE_COUNT;
    String tabTitles[];
    private Context context;
    public MyFragmentPagerAdapter(FragmentManager fm,String title[], int num) {
        super(fm);
        this.tabTitles = title;
        this.PAGE_COUNT = num;

    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0)
            fragment = new All();
        else if (i == 1)
            fragment = new All();
        else if (i == 2)
            fragment = new All();
        else    if(i == 3)
        fragment = new Brandship();
        else    if(i == 4)
            fragment = new Brandship();
        else

        fragment = new ShopsTab();
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
