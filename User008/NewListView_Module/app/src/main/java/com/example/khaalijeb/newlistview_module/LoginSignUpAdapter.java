package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Rajdeep Singh on 22-06-2015.
 */
public class LoginSignUpAdapter extends FragmentPagerAdapter {
    Fragment fragment;

    int PAGE_COUNT;
    String tabTitles[];
    private Context context;
    public LoginSignUpAdapter(FragmentManager fm,String title[], int num) {
        super(fm);
        this.tabTitles = title;
        this.PAGE_COUNT = num;

    }

    @Override
    public Fragment getItem(int i) {

        if (i == 0)
            fragment = new LoginFragment();
        else if (i == 1)
            fragment = new SignUpFragment();

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
