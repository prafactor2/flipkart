package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import models.BrandApp_Profile;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class brandprofile extends AppCompatActivity {

    public static String BRAND_PROFILE_ENDPOINT = "http://192.168.0.9:1337/brand_profile";
    ArrayList<Object> items = new ArrayList<>();
    Integer total_curr;
    Typeface font;

    String brand_id;
    String shop_type;
    Button stores;
    Button feeds;

    ComplexRecyclerViewAdapterbrandprofile mAdapter;
    RecyclerView mRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context c;
        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandprofile);
        Bundle b = getIntent().getExtras();
        brand_id = b.getString("brand_id");

        stores = (Button)findViewById(R.id.stores);
        feeds = (Button)findViewById(R.id.feeds);
        stores.setVisibility(View.INVISIBLE);
        feeds.setVisibility(View.INVISIBLE);
        stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();

            }
        });

        feeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(brandprofile.this, brandtimeline.class);
                Bundle b = new Bundle();
                b.putString("brand_id", brand_id);
                i.putExtras(b);
                startActivity(i);
            }
        });

        mRecyclerview = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        Brandapp_Profile_requestData();

    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CustomAlertDialog editNameDialog = new CustomAlertDialog(brand_id, shop_type);
        editNameDialog.show(fm, "f1");
    }
    public ArrayList<Object> getSampleArrayList(Context c) {
/*
        items.add(new brandheaderdata(R.drawable.levis,R.drawable.levislogo1,"LEVI'S","4.3","124"));
        items.add(new branddescdata("Our brands are among the most celebrated names in the history of apparel. They are recognized for their quality, originality and integrity."));

        items.add(new brandreviewdata("WILSON", " 1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore hey how are you", "4.7"));
        items.add(new brandreviewdata("PRAKASH", " 1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore", "3.9"));
        items.add(new brandreviewdata("GAURI", " 1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore", "2.6"));
        items.add(new brandreviewdata("KANHA", " 1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore", "3.1"));
        items.add(new brandreviewdata("KANHA", " 1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore" +
                "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore", "3.1"));
        items.add(new brandreviewdata("KANHA", "1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore\" +\n" +
                "                \"1, SJR Primus, Ground Floor, Opposite Forum Mall, 7th Block, Koramangala, Bangalore\"", "3.1"));
        items.add(new brandreviewdata2("KANHA", "dfassdsdf ", "3.1"));

*/
        return items;
    }

    public void Brandapp_Profile_requestData() {
        Log.d("hii", "Merchant Profile With Photos Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRAND_PROFILE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Brand_Profile(brand_id, new Callback<BrandApp_Profile>() {
                @Override
                public void success(final BrandApp_Profile server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        String cover, icon;
                        if (server_data.getBrand().getCover() == null) {
                            cover = "";
                        } else {
                            cover = server_data.getBrand().getCover();
                        }
                        if (server_data.getBrand().getIcon() == null) {
                            icon = "";
                        } else {
                            icon = server_data.getBrand().getIcon();
                        }
                        Integer total_votes = server_data.getBrand().getTotal_ratings();
                        Integer total_reviews = server_data.getBrand().getTotal_reviews();
                        Double rating = server_data.getBrand().getRating();
                        String brand_name = server_data.getBrand().getBrand_name();
                        shop_type = server_data.getBrand().getShop_type();
                        Log.d("shop_type", "shop_type: " + shop_type);
                        String description;
                        if (server_data.getBrand().getDescription() == null) {
                            description = "";
                        } else {
                            description = server_data.getBrand().getDescription();
                        }
                        Log.d("header", "cover: " + cover);
                        Log.d("header", "icon: " + icon);
                        Log.d("header", "brand_name: " + brand_name);
                        Log.d("header", "tot_rev: " + total_reviews);
                        Log.d("header", "tot_rt: " + total_votes);
                        Log.d("header", "rating: " + rating);
                        items.add(new brandheaderdata2(cover, icon, brand_name, rating.toString(), total_reviews.toString(), total_votes.toString()));
                        items.add(new branddescdata(description));
                        if (server_data.getReviews().size() == 0) {
                            //Future            NO_REVIEWS.setVisibility(View.VISIBLE);
                        } else {
                            //Future            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            total_curr = server_data.getReviews().size();
                            for (int i = 0; i < total_curr; i++) {
                                String review = server_data.getReviews().get(i).getReview();
                                Double ratingg = server_data.getReviews().get(i).getRating();
                                String username = server_data.getReviews().get(i).getUsername();
                                Log.d("cover", "cover: " + username);
                                if (i == 3) {
                                    items.add(new brandreviewdata2(username, review, rating));
                                } else {
                                    items.add(new brandreviewdata(username, review, rating));
                                }
                            }
                        }
                        mAdapter = new ComplexRecyclerViewAdapterbrandprofile(getApplicationContext(), items, font);
                        mRecyclerview.setAdapter(mAdapter);
                        stores.setVisibility(View.VISIBLE);
                        feeds.setVisibility(View.VISIBLE);
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
