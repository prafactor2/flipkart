package com.example.khaalijeb.newlistview_module;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class FirstActivity extends ActionBarActivity {
    TextView textView;
    ViewPager pager;
    LoginSignUpAdapter adapter;
    SlidingTabLayout1 tabs;
    String title[] = {"Login", "SignUp"};
    int numoftab = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            Log.d("back", "first hii");
            finish();
        } else {
            Log.d("back", "first hee");
            setContentView(R.layout.activity_first);
            adapter = new LoginSignUpAdapter(getSupportFragmentManager(), title, numoftab);
            pager = (ViewPager) findViewById(R.id.viewpager);
            pager.setAdapter(adapter);
            tabs = (SlidingTabLayout1) findViewById(R.id.sliding_tabs);
            // tabs.setDistributeEvenly(true);
            tabs.setBackgroundColor(getResources().getColor(R.color.colorbackground));
            tabs.setSelectedIndicatorColors(getResources().getColor(R.color.tabindicatorcolorwallet));
            tabs.setViewPager(pager);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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
