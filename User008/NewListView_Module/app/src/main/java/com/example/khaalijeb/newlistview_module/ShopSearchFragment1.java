package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import models.Momoe_Search_Merchant;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 14/11/2015.
 */
public class ShopSearchFragment1 extends Fragment {

    public static String SEARCH_MERCHANT_ENDPOINT = "http://192.168.0.9:1337/momoe_search_merchant";

    double latitude = 0, longitude = 0;
    List<information> data = new ArrayList<>();
    TextView NO_MERCHANT;
    Typeface font;
    Integer total_curr;
    Integer list_size;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    EditText MOMOE_SEARCH;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    private static MyAdapter mAdapter;

    private RecyclerView mRecyclerView;
    Context c;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 3) {
                page = 0;
                preLast = 0;
                firstVis = 0;
                Log.d("pppp", "page is : " + page);
                //       shop_name.clear();
                //       address.clear();
                Momoe_Search_requestData();
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    public void getlocation() throws IOException, JSONException {
        String finalAddress = "";
        GPSTracker gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("hii", "latitude: " + latitude);
            Log.d("hii", "longitude: " + longitude);
            Toast.makeText(getActivity(), "Your Location is -\nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
        } else {
            gps.showSettingsAlert();
        }
    }

    public void getAuto_Detect() throws IOException, JSONException {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        Geocoder geoCoder = new Geocoder(getActivity());
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
            Toast.makeText(getActivity(), auto_detect, Toast.LENGTH_LONG).show();
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_shopsearchfragment1, container, false);
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
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
        MOMOE_SEARCH = (EditText) getActivity().findViewById(R.id.shop_search);
        MOMOE_SEARCH.addTextChangedListener(mTextEditorWatcher);
        page = 0;
        preLast = 0;
        adapterinitialised = false;

        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
        /*        Log.d("hiii", "scroll hua hai");
                RecyclerView.Recycler recycler = null;
                Log.d("scroll", "dx: " + dx + " dy: " + dy);
                Integer visibleItemCount = mRecyclerView.getChildCount();
                Integer totalItemCount = mLayoutManager.getItemCount();
                Integer firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //    Integer firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
                Log.d("scroll", "visibleitemcount " + visibleItemCount);
                Log.d("scroll", "totalitemcount " + totalItemCount);
                Log.d("scroll", "firstvisibleitem " + firstVisibleItem);
                final int lastItem = firstVisibleItem + visibleItemCount;
                Log.d("scroll", "lastitem " + lastItem);
                Log.d("hii", "lastitem " + lastItem);

                Log.d("smooth", "firstvis : " + firstVis + " lastItem : " + lastItem);

                if (page == 0 && visibleItemCount == totalItemCount) {
                    Log.d("hii", "did nothing ");
                } else {
                    if (stay_on_this_page == false) {
                        Log.d("hii", "ItemCountLast");
                        Log.d("hii", "" + preLast + "" + lastItem);
                        if (preLast != lastItem) { //to avoid multiple calls for last item
                            if (lastItem >= totalItemCount - 5) {
                                preLast = lastItem;
                                //                  preLast = firstVisibleItem;
                                firstVis = firstVisibleItem + 1;
                                Log.d("llll", "preLast :" + preLast);
                                page++;
                                stay_on_this_page = true;
                                Momoe_Search_requestData();
                            } else {
                                Log.d("hii", "Last");
                            }
                        } else {
                            Log.d("smooth_scroll", "khali");
                        }
                    }

                }*/
            }
        });
        return v;

    }



    public List<information> getData(Context c) {
/*
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] Address = getResources().getStringArray(R.array.address);
        String[] Type = getResources().getStringArray(R.array.type);
       double[] Rating = {4.3,2.3,5.0,1.9,4.0,3.4};
        double[] Distance = {4.3,2.3,5.0,1.9,4.0,3.4};
        String[] Votes = getResources().getStringArray(R.array.votes);

        //  int[] icon = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5,R.drawable.p6,R.drawable.p7};
        for (int i = 0; i < titles.length; i++) {
            information current = new information();
            current.title = titles[i];
            //   current.iconId = icon[i];
            current.Address = Address[i];
            current.type = Type[i];
            current.rating = Rating[i];
            current.distance = Distance[i];
            current.votes = Votes[i];
            data.add(current);
        }
*/
        return data;
    }

    public void Momoe_Search_requestData() {
        Log.d("hii", "Momoe Search Request Data");
        final String PREFS_NAME = "MyPrefsFile";
        try {
            getlocation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String city = settings.getString("auto_detect_city", "");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(SEARCH_MERCHANT_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            String location = ((ShopSearch)getActivity()).loc;
            api.post_Momoe_Search_Merchant(MOMOE_SEARCH.getText().toString(), location, city, page.toString(), latitude, longitude, new Callback<Momoe_Search_Merchant>() {
                @Override
                public void success(final Momoe_Search_Merchant server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Log.d("size", "size " + server_data.getSize());
                        Log.d("hii", "" + server_data.getResults().size());
                        if (server_data.getResults().size() == 0) {
                            if (page != 0) {
                                mRecyclerView.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //Futue                    NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new MyAdapter(getActivity(),data,font);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            //Future                NO_MERCHANT.setVisibility(View.INVISIBLE);
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
                                prev_total = server_data.getSize()*page;
                                Log.d("hii", "Now prev_total is " + prev_total);
                            }
                            total_curr = server_data.getResults().size();

                            Log.d("hii", "first place is " + server_data.getResults().get(0).getShop_name());

                            for (int i = 0; i < total_curr; i++) {
                                information current = new information();
                                current.title = server_data.getResults().get(i).getShop_name();
                                String street = server_data.getResults().get(i).getAddress().getStreet();
                                String area = server_data.getResults().get(i).getAddress().getArea();
                                current.Address = street + " " + area;
                                String[] splited = area.split(",");
                                current.area1 = splited[1];
                                current.type = server_data.getResults().get(i).getShop_type();
                                current.rating = server_data.getResults().get(i).getRating();
                                current.distance = server_data.getResults().get(i).getDistance();
                                current.jeb_no = server_data.getResults().get(i).getJeb_no();
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new MyAdapter(getActivity(), data, font);
                                mRecyclerView.setAdapter(mAdapter);
                                adapterinitialised = true;
                            }
                        }
                        mRecyclerView.scrollToPosition(firstVis);
                    } else {
                        Log.d("hii", server_data.getMessage());
                        Toast.makeText(getActivity(), server_data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("hii", "failure");
                    Log.e("hii", retrofitError.toString());
                    Toast.makeText(getActivity(), retrofitError.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("hii", "api null");
        }
    }

}
