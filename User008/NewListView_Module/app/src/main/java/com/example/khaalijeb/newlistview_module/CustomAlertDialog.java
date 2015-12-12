package com.example.khaalijeb.newlistview_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import models.brand_profile_stores;
import models.merchant_profile;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 13/11/2015.
 */
public class CustomAlertDialog extends DialogFragment {

    public static String BRAND_PROFILE_STORES_ENDPOINT = "http://192.168.0.3:1337/brand_profile_stores";
    List<storedata> data = new ArrayList<>();

    double latitude = 0;
    double longitude = 0;
    String shop_type;
    String brand_id;
    private RecyclerView mRecyclerView;
    private RecyclerViewStores mAdapter;
    Context c;

    public CustomAlertDialog() {
    }

    @SuppressLint("ValidFragment")
    public CustomAlertDialog(String brand_id, String shop_type) {
        this.shop_type = shop_type;
        this.brand_id = brand_id;
    }

    // this method create view for your Dialog
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.layout_customdialog, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));

        Brand_Profile_STORES_requestData();
        return v;
    }

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getDialog().setTitle("STORES");


        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });


    }

    public List<storedata> getData(Context c) {
        //l  Resources res = c.getResources();
        /*
        String[] storename = getResources().getStringArray(R.array.titles2);
        String[] storeaddress = getResources().getStringArray(R.array.address);
        String[] distance = getResources().getStringArray(R.array.distance);

        // int[] icon = {R.drawable.nike, R.drawable.nike, R.drawable.nike, R.drawable.nike, R.drawable.nike};
        for (int i = 0; i < storename.length; i++) {
            storedata current = new storedata();
            // current.title = titles[i];
            current.storename = storename[i];
            current.storeaddress = storeaddress[i];
            current.distance = distance[i];
            data.add(current);
        }
        */
        return data;
    }

    public void Brand_Profile_STORES_requestData() {
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
        Log.d("hii", "Merchant Profile Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRAND_PROFILE_STORES_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Brand_Profile_Stores(brand_id, shop_type, city, latitude, longitude, new Callback<brand_profile_stores>() {
                @Override
                public void success(final brand_profile_stores server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Integer total_curr = server_data.getResults().size();
                        if (total_curr == 0) {
                            //Future            NO_REVIEWS.setVisibility(View.VISIBLE);
                        } else {
                            //Future            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            for (int i = 0; i < total_curr; i++) {
                                storedata current = new storedata();
                                // current.title = titles[i];
                                current.area1 = server_data.getResults().get(i).getAddress().getArea1();
                                current.storeaddress = server_data.getResults().get(i).getAddress().getStreet();
                                current.distance = server_data.getResults().get(i).getDistance();
                                current.jeb_no = server_data.getResults().get(i).getJeb_no();
                                data.add(current);
                            }
                        }
                        mAdapter = new RecyclerViewStores(getActivity(), getData(c));
                        mRecyclerView.setAdapter(mAdapter);
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
