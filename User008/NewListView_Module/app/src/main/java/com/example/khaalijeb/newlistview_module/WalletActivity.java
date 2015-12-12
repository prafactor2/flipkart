package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class WalletActivity extends ActionBarActivity {
    public void transfer(View view) {

    }

    Toolbar toolbar;
    ViewPager pager;
    MyFragmentPagerAdapter adapter;
    SlidingTabLayout1 tabs;
    String title[] = {"Add Money", "Transfer Money","Request Money", "brands", "shops"};
    int numoftab = 5;

    TextView mWalletBalanceTextView;
    String mUserId;
    String mUserIdFile;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_search, null,false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);
     //   toolbar = (Toolbar) findViewById(R.id.app_bar);
     //   setSupportActionBar(toolbar);

     //   getSupportActionBar().setDisplayShowHomeEnabled(true);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), title, numoftab);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout1) findViewById(R.id.sliding_tabs);
        tabs.setDistributeEvenly(true);
    //    tabs.setCustomTabView(R.layout.customtab_title, R.id.textView2);
        tabs.setBackgroundColor(getResources().getColor(R.color.colorbackground));
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.tabindicatorcolorwallet));


        tabs.setCustomTabColorizer(new SlidingTabLayout1.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorbackgroundsign);

            }

            public int getDividerColor(int position) {
                return getResources().getColor(R.color.colorSecondaryText);
            }
        });
        tabs.setViewPager(pager);

        }



 /*   @Override
    protected void onResume() {
        super.onResume();
        mWalletBalanceTextView = (TextView) findViewById(R.id.mWalletBalanceText);

        mUserIdFile = LoginFragment.mUserNameFile;
        mSharedPreferences = getSharedPreferences(mUserIdFile, Context.MODE_PRIVATE);
        mUserId = mSharedPreferences.getString("user_name","abc");
        Context cv = this;
        Operations op = new Operations(cv);
        Cursor cr = op.getinfo(op);
        cr.moveToNext();
        do{
            if (mUserId.equals(cr.getString(0))) {
                mWalletBalanceTextView.setText("Wallet Balance is Rs "+cr.getString(2));
            }
        }while (cr.moveToNext());
    }
      //  mWalletBalanceTextView.setText("Wallet Balance is Rs "+MyDetails.mBalance);
        /* NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawerFragment.setUp((android.support.v4.widget.DrawerLayout) findViewById(R.id.my_drawer),toolbar);

*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_money, menu);
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
        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


}
