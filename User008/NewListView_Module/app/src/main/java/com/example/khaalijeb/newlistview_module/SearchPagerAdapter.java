package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 15/09/2015.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by Lord Voldemort on 15/09/2015.
 */

/**
 * Created by Rajdeep Singh on 22-05-2015.
 *
 * for wallet activity
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {

    Fragment fragment;
    int PAGE_COUNT;
    String tabTitles[];
    private Context context;
    public SearchPagerAdapter(FragmentManager fm,String title[], int num) {
        super(fm);
        this.tabTitles = title;
        this.PAGE_COUNT = num;

    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0)
            fragment = new All();
        else if (i == 1)
            fragment = new RestaurantTab();
        else if(i == 2)
            fragment = new wellness();
        else if(i == 3)
            fragment = new wellness();
        else
        fragment = new others();

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

