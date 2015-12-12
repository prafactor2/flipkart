package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class MainPagerAdapter extends FragmentPagerAdapter {
    Fragment fragment;
    int PAGE_COUNT;
    String tabTitles[];
    private Context context;
    public MainPagerAdapter(FragmentManager fm,String title[], int num) {
        super(fm);
        this.tabTitles = title;
        this.PAGE_COUNT = num;
    }
    private static final int iconRes[] = {
            R.drawable.image2,
            R.drawable.image1,
            R.drawable.image3,
            R.drawable.image4,


    };

    @Override
    public Fragment getItem(int i) {

        if (i == 0)
            fragment = new PayFragment();
        else if (i == 1)
            fragment = new Offers();
        else if (i == 2)
            fragment = new Brandship();
        else
            fragment = new Notifications();

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
    public int getDrawable(int position) {
        return iconRes[position];
    }
}
