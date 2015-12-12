package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.brands_search;
import models.location_search;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class locationsearch extends AppCompatActivity  {

    public static String LOCATION_SEARCH_ENDPOINT = "http://192.168.0.21:1337/location_search";
    List<locationdata> data = new ArrayList<>();

    double latitude, longitude;
    Integer total_curr;
    Integer list_size;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;
    ImageView close;
    RecyclerView mRecyclerView;
    locationrecyclerviewadapter mAdapter;
    TextView currentlocation;
    ImageView currentlocationlogo;
    Context c;

    EditText LOCATION_SEARCH;
    String location;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 3) {
                page = 0;
                preLast = 0;
                firstVis = 0;
                Log.d("pppp", "page is : " + page);
                Location_Search_requestData();
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public void getlocation() throws IOException, JSONException {
        String finalAddress = "";
        GPSTracker gps = new GPSTracker(locationsearch.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("hii", "latitude: " + latitude);
            Log.d("hii", "longitude: " + longitude);
            Toast.makeText(getBaseContext(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    public void getAuto_Detect() throws IOException, JSONException {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Geocoder geoCoder = new Geocoder(getBaseContext());
        List<Address> matches = null;
        try {
            getlocation();
            matches = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String auto_detect = "";
        Address bestMatch = (matches.isEmpty() ? null : matches.get(0));
        if (bestMatch == null) {

        } else {
            Toast.makeText(getBaseContext(), auto_detect, Toast.LENGTH_LONG).show();
            Log.d("address", "" + matches.get(0));
            Log.d("address", "" + matches.get(0).getMaxAddressLineIndex());
            int len = matches.get(0).getMaxAddressLineIndex();
            String area1 = "", city = "";
            /*
            for (int i = 0; i <= len; i++) {
                Log.d("address", "i:" + i + ", " + matches.get(0).getAddressLine(i));
                if (i == 0) {
                    if (matches.get(0).getAddressLine(0).equals("Unnamed Rd")) {
                        location = "";
                    } else {
                        location = matches.get(0).getAddressLine(0);
                    }
                } else if (i < len - 1){
                    location = location +  matches.get(0).getAddressLine(i);
                }
            }
            */
            String str = matches.get(0).getAddressLine(len - 1);
            String[] splited = str.split(",");
            city = splited[0];
            String str1 = matches.get(0).getAddressLine(len - 2);
            String[] splitedd = str1.split(",");
            area1 = splitedd[splitedd.length - 1];
            area1 = area1.trim();
            Log.d("address", "" + "area1: " + area1);
            Log.d("address", "" + "city: " + city);
            settings.edit().putString("auto_detect_city", city).apply();
            settings.edit().putString("auto_detect_area1", area1).apply();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locationsearch);
        final String PREFS_NAME = "MyPrefsFile";
        close = (ImageView)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String last_start_time = settings.getString("app_last_start_time", "");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("hii", "last_start_time: " + last_start_time);
        try {
            Date oldDate = dateFormat.parse(last_start_time);
            System.out.println(oldDate);

            Date currentDate = new Date();

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            Log.d("hii", "hours: " + hours + " seconds: " + seconds);
            if (oldDate.before(currentDate)) {
                if (hours >= 4) {
                    try {
                        getAuto_Detect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                    settings.edit().putString("app_last_start_time", formattedDate).apply();
                    Log.d("date", "refresh");
                } else {

                }
            }
        } catch (ParseException e) {

            e.printStackTrace();
        }
        page = 0;
        adapterinitialised = false;
        LOCATION_SEARCH = (EditText) findViewById(R.id.location);
        LOCATION_SEARCH.addTextChangedListener(mTextEditorWatcher);
        currentlocation = (TextView)findViewById(R.id.currentloc);
        currentlocationlogo = (ImageView)findViewById(R.id.currentloclogo);

        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getAuto_Detect();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent returnIntent = new Intent();
                final String PREFS_NAME = "MyPrefsFile";

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String area1 = settings.getString("auto_detect_area1", "");
                String city = settings.getString("auto_detect_city", "");
                returnIntent.putExtra("auto_detect", "yes");
                returnIntent.putExtra("result", area1 + ", " + city);
                setResult(Activity.RESULT_OK, returnIntent);
            //    returnIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
            }
        });

        currentlocationlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        LinearLayout l = (LinearLayout)findViewById(R.id.l1);
        setupUI(l);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(locationsearch.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
    public List<locationdata> getData(Context c) {
        /*
        String[] location = getResources().getStringArray(R.array.CITIES);


        for (int i = 0; i < location.length; i++) {
            locationdata current = new locationdata();
            current.location = location[i];


            //current.rating = Rating[i];
            data.add(current);
        }
*/
        return data;
    }

    public void Location_Search_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        final String PREFS_NAME = "MyPrefsFile";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String city = settings.getString("auto_detect_city", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(LOCATION_SEARCH_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            location = LOCATION_SEARCH.getText().toString();
            String[] splited = location.split(" ");
            String loc_uppercase = "";
            for (int i = 0; i < splited.length; i++) {
                if (Character.isLowerCase(splited[i].charAt(0))) {
                    char capLetter = Character.toUpperCase(splited[i].charAt(0));
                    Log.d("caps", "hee" + loc_uppercase);
                    loc_uppercase = loc_uppercase + capLetter + splited[i].substring(1);
                    Log.d("caps", "hii" + loc_uppercase);
                    if (i < splited.length - 1) {
                        loc_uppercase = loc_uppercase + " ";
                    }
                } else {
                    loc_uppercase = loc_uppercase + splited[i];
                    if (i < splited.length - 1) {
                        loc_uppercase = loc_uppercase + " ";
                    }
                }
            }
            Log.d("caps", loc_uppercase);
            api.post_Location_Search(loc_uppercase, page.toString(), city, new Callback<location_search>() {
                @Override
                public void success(final location_search server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        if (server_data.getResults().size() == 0) {
                            if (page != 0) {
                                mRecyclerView.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new locationrecyclerviewadapter(getBaseContext(), data);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            if (server_data.getSize() > server_data.getResults().size()) {
                                stay_on_this_page = true;
                            } else {
                                stay_on_this_page = false;
                            }
                            if (page == 0) {
                                prev_total = 0;
                                data.clear();
                                Log.d("hii", "Page is zero " + prev_total);
                                list_size = server_data.getSize();
                            } else {
                                Log.d("hii", "prev_total is " + prev_total + " Size is " + server_data.getSize() + " page is " + page);
                                prev_total = server_data.getSize() * page;
                                Log.d("hii", "Now prev_total is " + prev_total);
                            }

                            total_curr = server_data.getResults().size();
                            for (int i = 0; i < total_curr; i++) {
                                locationdata current = new locationdata();
                                String loc = "";
                                if (server_data.getResults().get(i).getArea2() == null) {
                                    loc = "";
                                } else {
                                    loc = server_data.getResults().get(i).getArea2() + ", ";
                                }
                                if (server_data.getResults().get(i).getArea1() == null) {
                                    loc = loc + "";
                                } else {
                                    loc = loc + server_data.getResults().get(i).getArea1() + ", ";
                                }
                                if (server_data.getResults().get(i).getCity() == null) {
                                    loc = loc + "";
                                } else {
                                    loc = loc + server_data.getResults().get(i).getCity();
                                }
                                current.location = loc;
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new locationrecyclerviewadapter(getBaseContext(), data);
                                mRecyclerView.setAdapter(mAdapter);

                                adapterinitialised = true;
                            }
                        }
                        mRecyclerView.scrollToPosition(firstVis);
                    } else {
                        Log.d("hii", server_data.getMessage());
                        Toast.makeText(getBaseContext(), server_data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                    Toast.makeText(getBaseContext(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

}
