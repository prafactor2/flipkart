package com.example.khaalijeb.newlistview_module;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.uber.sdk.android.rides.RequestButton;
import com.uber.sdk.android.rides.RideParameters;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.Uber_Price;
import models.Uber_Time;
import models.success_message;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;


public class MainActivity extends AppCompatActivity implements Notifications.MyNotificationsListener, Brandship.MyBrandshipListener {
    SharedPreferences mSharedPreferences;
    String mFile;
    ViewPager pager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    MainPagerAdapter adapter;
    private NavigationView navigationView;
    protected DrawerLayout drawerLayout;
    SlidingTabLayout1 tabs;
    SlidingTabLayout tabs1;
    String title[] = {"PAY", "SCHOOLFEES","rent"};
    int numoftab = 4;
    NavigationDrawerFragment drawerFragment;
    private int[] tabIcons = {
            R.drawable.image2,
            R.drawable.image1,
            R.drawable.image3
    };

    public static final String REMOVE_FOLLOW_NOTIFICATIONS_ENDPOINT = "http://192.168.0.3:1337/remove_follow_notifications";

    public static final String REMOVE_NOTIFICATIONS_ENDPOINT = "http://192.168.0.3:1337/remove_notifications";

    public static final String LOGOUT_ENDPOINT = "http://192.168.0.3:1337/user_logout";

    public static final String UBER_TIME_ENDPOINT = "https://api.uber.com/v1/estimates";

    public static final String UBER_PRICE_ENDPOINT = "https://api.uber.com/v1/estimates";

  //  public static final String UBER_TIME_ENDPOINT = "http://192.168.0.9:1337/add_money";

    public void RemoveNotifications_requestData() {
        Log.d("hii", "request data");
        final String PREFS_NAME = "MyPrefsFile";

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mobile_no = settings.getString("mobile_no", "");
        String reg_id = settings.getString("reg_id", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(REMOVE_NOTIFICATIONS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Remove_Notifications(mobile_no, reg_id, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed notifications");
                    } else {
                        Log.d("hii", "remove notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

    public void Removefollow_requestData() {
        Log.d("hii", "request data");
        final String PREFS_NAME = "MyPrefsFile";

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mobile_no = settings.getString("mobile_no", "");
        String reg_id = settings.getString("reg_id", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(REMOVE_FOLLOW_NOTIFICATIONS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Remove_Follow_Notifications(mobile_no, reg_id, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        Log.d("hii", "removed follow_notifications");
                    } else {
                        Log.d("hii", "remove follow notifications error" + data.getMessage());
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

    public void Logout_requestData() {
        Log.d("hii", "request data");
        final String PREFS_NAME = "MyPrefsFile";

        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String mobile_no = settings.getString("mobile_no", "");
        String reg_id = settings.getString("reg_id", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(LOGOUT_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Logout(mobile_no, reg_id, new Callback<success_message>() {
                @Override
                public void success(success_message data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + data.getSuccess());
                    Log.d("hii", "Message : " + data.getMessage());
                    if (data.getSuccess() == 1) {
                        final String PREFS_NAME = "MyPrefsFile";
                        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
                        settings.edit().putBoolean("login", false).apply();
                        preferences.edit().remove("email_id").apply();
                        preferences.edit().remove("mobile_no").apply();
                        preferences.edit().remove("follow_notifications").apply();
                        preferences.edit().remove("notifications_notifications").apply();
                        Toast.makeText(getApplicationContext(), "Log out", Toast.LENGTH_SHORT).show();
                        Intent log = new Intent(MainActivity.this, StartingActivity.class);
                        log.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(log);
                    } else {
                        Log.d("hii", "Please try Again" + data.getMessage());
                        Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

    float latitude, longitude;

    public void getlocation() throws IOException, JSONException {
        String finalAddress = "";
        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            latitude = (float) gps.getLatitude();
            longitude = (float) gps.getLongitude();
            Log.d("hii", "latitude: " + latitude);
            Log.d("hii", "longitude: " + longitude);

            Toast.makeText(getBaseContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    String prod;
    RequestButton uberButtonBlack;

    private boolean check_uber() {
        PackageManager pm = getPackageManager();

        try {
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void Uber_Time_requestData() throws IOException, JSONException {
        Log.d("uber", "Uber Price request data");

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
                                          @Override
                                          public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                                              Request original = chain.request();

                                              Request request = original.newBuilder()
                                                      .header("Authorization", "Token YQtelRNZ66Qfd6hFCz80wXguFDEEXd6wp7K4pbJG")
                                                      .method(original.method(), original.body())
                                                      .build();

                                              return chain.proceed(request);
                                          }
                                      });

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(UBER_PRICE_ENDPOINT).setClient(new OkClient(httpClient)).build();
        Log.d("uber", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("uber", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            getlocation();
            Log.d("uber", "latitude: " + latitude);
            Log.d("uber", "longitude: " + longitude);
            api.get_Uber_Price(latitude, longitude, (float) 12.89, (float) 77.65, new Callback<Uber_Price>() {
                @Override
                public void success(Uber_Price data, Response response) {

                    Log.d("uber", "statusCode hai: " + response.getStatus());
                    Log.d("uber", "3");
                    if (response.getStatus() == 200) {
                        for (int i = 0; i < data.getPrices().size(); i++) {
                            prod = data.getPrices().get(i).getProduct_id();
                            Log.d("uber", "product_id: " + data.getPrices().get(i).getProduct_id());
                            Log.d("uber", ": display_name" + data.getPrices().get(i).getDisplay_name());
                            Log.d("uber", "estimate: " + data.getPrices().get(i).getEstimate());
                            Log.d("uber", "duration: " + data.getPrices().get(i).getDuration());
                            if (check_uber()) {
                                uberButtonBlack.setClientId("UglarOHD-bVaFl7qlNSCMkMknkk7vpiu");
                                Log.d("uber", "prod: " + prod);
                                RideParameters rideParameters = new RideParameters.Builder()
                                        .setProductId(prod)
                                        .setPickupLocation(latitude, longitude, "", "Manipal County, Singasandra")
                                        .setDropoffLocation((float) 12.89, (float) 77.65, "", "Maithri Layout Whitefield")
                                        .build();

                                uberButtonBlack.setRideParameters(rideParameters);
                            } else {
                                String url = "https://m.uber.com/sign-up?client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        }
                        /*
                        try {
                            PackageManager pm = getPackageManager();
                            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                            String uri =
                                    "uber://?action=setPickup&pickup=my_location&dropofflocation=12.97,77.75,Maithri Layout, Maithri Layout Whitefield&product_id=" + data.getTimes().get(0).getProduct_id() +"&client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        } catch (PackageManager.NameNotFoundException e) {
                            // No Uber app! Open mobile website.
                            String url = "https://m.uber.com/sign-up?client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                         */
                    } else {

                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("uber", "failure");
                    Log.e("uber", retrofitError.toString());
                    String json = new String(((TypedByteArray) retrofitError.getResponse().getBody()).getBytes());
                    Log.d("uber", "failure:" + json.toString());
                }
            });
        } else {
            Log.d("uber", "api null");
        }
    }

    public void Uber_Price_requestData() throws IOException, JSONException {
        Log.d("uber", "Uber Time request data");

        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", "Token YQtelRNZ66Qfd6hFCz80wXguFDEEXd6wp7K4pbJG")
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(UBER_TIME_ENDPOINT).setClient(new OkClient(httpClient)).build();
        Log.d("uber", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("uber", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            getlocation();
            Log.d("uber", "latitude: " + latitude);
            Log.d("uber", "longitude: " + longitude);
            api.get_Uber_Time(latitude, longitude, new Callback<Uber_Time>() {
                @Override
                public void success(Uber_Time data, Response response) {

                    Log.d("uber", "statusCode hai: " + response.getStatus());
                    Log.d("uber", "3");
                    if (response.getStatus() == 200) {
                        for (int i = 0; i < data.getTimes().size(); i++) {
                            prod = data.getTimes().get(i).getProduct_id();
                            Log.d("uber", "product_id: " + data.getTimes().get(i).getProduct_id());
                            Log.d("uber", ": display_name" + data.getTimes().get(i).getDisplay_name());
                            Log.d("uber", "estimate: " + data.getTimes().get(i).getEstimate());
                            if (check_uber()) {
                                uberButtonBlack.setClientId("UglarOHD-bVaFl7qlNSCMkMknkk7vpiu");
                                Log.d("uber", "prod: " + prod);
                                RideParameters rideParameters = new RideParameters.Builder()
                                        .setProductId(prod)
                                        .setPickupLocation(12.87f, 77.64f, "", "Manipal County, Singasandra")
                                        .setDropoffLocation(12.97f, 77.75f, "", "Maithri Layout Whitefield")
                                        .build();

                                uberButtonBlack.setRideParameters(rideParameters);
                            } else {
                                String url = "https://m.uber.com/sign-up?client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        }
                        /*
                        try {
                            PackageManager pm = getPackageManager();
                            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                            String uri =
                                    "uber://?action=setPickup&pickup=my_location&dropofflocation=12.97,77.75,Maithri Layout, Maithri Layout Whitefield&product_id=" + data.getTimes().get(0).getProduct_id() +"&client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(uri));
                            startActivity(intent);
                        } catch (PackageManager.NameNotFoundException e) {
                            // No Uber app! Open mobile website.
                            String url = "https://m.uber.com/sign-up?client_id=UglarOHD-bVaFl7qlNSCMkMknkk7vpiu";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                         */
                    } else {

                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("uber", "failure");
                    Log.e("uber", retrofitError.toString());
                    String json =  new String(((TypedByteArray)retrofitError.getResponse().getBody()).getBytes());
                    Log.d("uber", "failure:" + json.toString());
                }
            });
        } else {
            Log.d("uber", "api null");
        }
    }

    @Override
    public Integer nonotifications(){
        /** Do something with the string and return your Integer instead of 0 **/
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        settings.edit().putInt("notifications_notifications", 0).apply();
        removeNotificationTextView();
        RemoveNotifications_requestData();
        return 0;
    }

    @Override
    public Integer nofollownotifications(){
        /** Do something with the string and return your Integer instead of 0 **/
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        settings.edit().putInt("follow_notifications", 0).apply();
        removeFollowNotsTextView();
        Removefollow_requestData();
        return 0;
    }

    public void removeFollowNotsTextView() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textV1 = (TextView) findViewById(R.id.follow_badge);
                textV1.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void removeNotificationTextView() {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textV1 = (TextView) findViewById(R.id.notification_badge);
                textV1.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void updateNotificationTextView(final String t) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textV1 = (TextView) findViewById(R.id.notification_badge);
                Log.d("notifications", t);
                if (t == "0") {
                    textV1.setVisibility(View.INVISIBLE);
                } else {
                    textV1.setVisibility(View.VISIBLE);
                    textV1.setText(t);
                }
            }
        });
    }

    public void updateFollowTextView(final String t) {
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                TextView textV1 = (TextView) findViewById(R.id.follow_badge);
                if (t == "0") {
                    textV1.setVisibility(View.INVISIBLE);
                } else {
                    textV1.setVisibility(View.VISIBLE);
                    textV1.setText(t);
                }
            }
        });
    }

    private BroadcastReceiver mNotifications = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            final String PREFS_NAME = "MyPrefsFile";

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            Integer x = settings.getInt("notifications_notifications", 0);
            //        settings.edit().putInt("notifications_notifications", x+1).commit();
            String message = intent.getStringExtra("message");
            //        Integer z = x + 1;
            updateNotificationTextView("" + x);
            Log.d("notifications", "message : " + message);
            //do other stuff here
        }
    };

    private BroadcastReceiver mFollow = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            final String PREFS_NAME = "MyPrefsFile";

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            Integer x = settings.getInt("follow_notifications", 0);
            //        settings.edit().putInt("follow_notifications", x + 1).commit();
            String message = intent.getStringExtra("message");
            //        Integer z = x + 1;
            updateFollowTextView("" + x);
            Log.d("follow notifications", "message : " + message);
            //do other stuff here
        }
    };

    @Override
    public void onBackPressed () {
        Intent intent = new Intent(MainActivity.this, StartingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
        finish();
        Log.d("back", "main back pressed");
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        this.unregisterReceiver(mNotifications);
        this.unregisterReceiver(mFollow);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(mNotifications, new IntentFilter("notifications_notifications"));
        this.registerReceiver(mFollow, new IntentFilter("follow_notifications"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        try {
            Uber_Time_requestData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */

/*
        uberButtonBlack = (RequestButton) findViewById(R.id.uber_button_black);
        uberButtonBlack.setClientId("UglarOHD-bVaFl7qlNSCMkMknkk7vpiu");
*/
        TextView notificationbadge = (TextView)findViewById(R.id.notification_badge);
        TextView followbadge = (TextView)findViewById(R.id.follow_badge);
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        Integer x = settings.getInt("notifications_notifications", 0);
        if (x == 0) {
            Log.d("notifications", "x: " + x);
            notificationbadge.setVisibility(View.INVISIBLE);
        } else {
            notificationbadge.setVisibility(View.VISIBLE);
            Log.d("notifications", "x: " + x);
            notificationbadge.setText("" + x);
        }
        Integer y = settings.getInt("follow_notifications", 0);
        if (y == 0) {
            Log.d("folow_notifications", "y: " + y);
            followbadge.setVisibility(View.INVISIBLE);
        } else {
            Log.d("follow_notifications", "y: " + y);
            followbadge.setVisibility(View.VISIBLE);
            followbadge.setText("" + y);
        }
        //for making translucent statusbar and navigationbar
   /*     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), title, numoftab);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        tabs1 = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        tabs1.setDistributeEvenly(true);
        tabs1.setCustomTabView(R.layout.custom_tab_title_2, R.id.tab_name_img);
        tabs1.setBackgroundColor(getResources().getColor(R.color.colorbackground));
        tabs1.setSelectedIndicatorColors(getResources().getColor(R.color.pressed1));
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                //   if(menuItem.isChecked()) menuItem.setChecked(false);
                //  else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.Home:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(i);
                      /*  ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        */

                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.Account:
                        Toast.makeText(getApplicationContext(),"Your Profile",Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getApplicationContext(),Profile.class);
                        startActivity(i2);

                        return true;

                    case R.id.Credits:
                        Toast.makeText(getApplicationContext(),"Earn Credits",Toast.LENGTH_SHORT).show();

                        Intent i3 = new Intent(getApplicationContext(),ChangePass.class);
                        startActivity(i3);

                        return true;

                    case R.id.Cards:
                        Toast.makeText(getApplicationContext(),"Manage Ur Cards",Toast.LENGTH_SHORT).show();
                        managecardsfragment fragment2 = new managecardsfragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.frame, fragment2);
                        fragmentTransaction2.commit();
                        return true;

                    case R.id.Support:
                        Toast.makeText(getApplicationContext(),"Support Center",Toast.LENGTH_SHORT).show();

                        supportfragment fragment3 = new supportfragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.frame, fragment3);
                        fragmentTransaction3.commit();
                        return true;

                    case R.id.Feedback:
                        Toast.makeText(getApplicationContext(),"Give Us Feedback",Toast.LENGTH_SHORT).show();
                        feedbackfragment fragment4 = new feedbackfragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction4.replace(R.id.frame, fragment4);
                        fragmentTransaction4.commit();
                        return true;

                    case R.id.Logout:
                        Logout_requestData();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });



        navigationView = (NavigationView) findViewById(R.id.navigation_view);
  /*      Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }*/
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.my_drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        //  setSupportActionBar(toolbar);

        //  getSupportActionBar().setIcon(R.drawable.app_icon);
  /*      drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawerFragment.setUp((android.support.v4.widget.DrawerLayout) findViewById(R.id.my_drawer), toolbar);



        final LinearLayout mDrawer = (LinearLayout) findViewById(R.id.drawer_fragment);
        final DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.my_drawer);
*/
        ImageView btn = (ImageView)findViewById(R.id.navbut);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });



        //   viewPager = (ViewPager) findViewById(R.id.viewpager);
        //   setupViewPager(viewPager);

        //  tabLayout = (TabLayout) findViewById(R.id.tabs);

        //  tabLayout.setupWithViewPager(viewPager);

        //  setupTabIcons();

     /*  adapter = new MainPagerAdapter(getSupportFragmentManager(), title, numoftab);
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
       // tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.customtab_title, R.id.textView2);
        tabs.setBackgroundColor(getResources().getColor(R.color.colortabbackground));
        tabs.setSelectedIndicatorColors(getResources().getColor(R.color.colortabbackground2));

*/


    /*  TextView textView = (TextView) findViewById(R.id.DATE);
textView.setText(DateUtils.formatDateTime(context, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_12HOUR));
*/


        /*tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colortext);

            }

            public int getDividerColor(int position) {
                return getResources().getColor(R.color.colorIcons);
            }
        });*/
        tabs1.setViewPager(pager);


    }

    /*   private void applyFontToMenuItem(MenuItem mi) {
           Typeface font = Typeface.createFromAsset(getAssets(), "helmed.otf");
           SpannableString mNewTitle = new SpannableString(mi.getTitle());
           mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
           mi.setTitle(mNewTitle);
       }
      */
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("PAY");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.image2, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("OFFERS");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.image1, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("BRANDS");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.image3, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PayFragment(), "PAY");
        adapter.addFrag(new Offers(), "OFFERS");
        adapter.addFrag(new Brandship(), "BRANDS");

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about) {
            Intent i = new Intent(this,AboutUs.class);
            startActivity(i);
        }

        if (id == R.id.main_page_logout) {


        }

        if (id == R.id.search) {

            Intent i = new Intent(MainActivity.this,Search.class);
            startActivity(i);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);

        }

        return super.onOptionsItemSelected(item);


    }


}
