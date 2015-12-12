package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.Merchant_All_Reviews;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Reviewactivity extends AppCompatActivity {

    public static String MERCHANT_ALL_REVIEWS_ENDPOINT = "http://192.168.0.3:1337/merchant_all_reviews";
    List<brandreviewdata> data = new ArrayList<>();
    Typeface font;
    private int preLast;
    Integer page;
    Integer total_curr;
    Integer list_size;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    int firstVis = 0;

    String merchant_id;
    Context c;
    RecyclerView mRecyclerview;
    ReviewRecyclerViewAdapter madapter;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewactivity);
        page = 0;
        adapterinitialised = false;
        Bundle b = getIntent().getExtras();
        merchant_id = b.getString("merchant_id");
        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        mRecyclerview = (RecyclerView)findViewById(R.id.my_recycler_view);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        Merchant_All_Reviews_requestData();

    }

    public List<brandreviewdata> getData(Context c) {

        Resources res = this.getResources();
        //  String[] titles = res.getStringArray(R.array.titles);
/*
        data.add(new brandreviewdata("Tahir","'Cause everything you do and words you say","3.5"));
        data.add(new brandreviewdata("shubham","So maybe it's true, that I can't live without you","3.5"));
        data.add(new brandreviewdata("willi","We love to start everyday on a fresh note. Lookout for new meal choices updated every morning.","3.5"));
        data.add(new brandreviewdata("kanha","Our exquisite range of dishes are prepared by experts","3.5"));
        data.add(new brandreviewdata("gauri", " All Inclusive pricing so no hassle of taxes, delivery and service charges!", "3.5"));
        data.add(new brandreviewdata("Binay", "Freshest of ingredients", "3.5"));
*/
        return data;
    }

    public void Merchant_All_Reviews_requestData() {
        Log.d("hii", "Get All Offers Request Data");
        final String PREFS_NAME = "MyPrefsFile";
        final SharedPreferences settings = this.getSharedPreferences(PREFS_NAME, 0);
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_ALL_REVIEWS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Merchant_All_Reviews(merchant_id, page.toString(), new Callback<Merchant_All_Reviews>() {
                @Override
                public void success(final Merchant_All_Reviews server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Log.d("size", "size " + server_data.getSize());
                        Log.d("hii", "" + server_data.getReviews().size());
                        if (server_data.getReviews().size() == 0) {
                            if (page != 0) {
                                mRecyclerview.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                madapter = new ReviewRecyclerViewAdapter(getBaseContext(), data, font);
                                mRecyclerview.setAdapter(madapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            if (server_data.getSize() > server_data.getReviews().size()) {
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
                            total_curr = server_data.getReviews().size();

                            for (int i = 0; i < total_curr; i++) {
                                brandreviewdata current = new brandreviewdata("", "", -1);
                                current.username = server_data.getReviews().get(i).getUser_name();
                                current.review = server_data.getReviews().get(i).getReview();
                                current.rating = server_data.getReviews().get(i).getRating();
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                madapter.notifyDataSetChanged();
                            } else {
                                madapter = new ReviewRecyclerViewAdapter(getBaseContext(), data, font);
                                mRecyclerview.setAdapter(madapter);

                                adapterinitialised = true;
                            }
                        }
                        mRecyclerview.scrollToPosition(firstVis);
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
