package com.example.khaalijeb.newlistview_module;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Testing extends MainActivity {

    Toolbar toolbar;
    ViewPager pager;
    MyFragmentPagerAdapter adapter;
    SlidingTabLayout1 tabs;
    String title[] = {"ALL","SHOPS","STORES","RESTAURANT"};
    int numoftab = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        /*toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        */
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), title, numoftab);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout1) findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true);
        tabs.setBackgroundColor(getResources().getColor(R.color.colortabbackground2));
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.tabindicatorcolorwallet));
        tabs.setViewPager(pager);
        /* tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);

            }
            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.colorSecondaryText);
            }
        });*/



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
