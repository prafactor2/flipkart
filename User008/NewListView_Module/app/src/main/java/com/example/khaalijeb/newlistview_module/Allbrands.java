package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.Followed_Posts;
import models.all_brands;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Allbrands extends AppCompatActivity implements BrandRecyclerViewAdapter.IObserver {


    public static String BRAND_TIMELINE_ENDPOINT = "http://192.168.0.3:1337/all_brands";
    List<allbrandsdata> data = new ArrayList<>();
    Integer total_curr;
    Integer list_size;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;
    Integer click_pos;

    RecyclerView mRecyclerView;
    BrandRecyclerViewAdapter mAdapter;

    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allbrands);
        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        page = 0;
        adapterinitialised = false;
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(c, 1));
        All_Brands_requestData();

    }



    public List<allbrandsdata> getData(Context c) {
/*
        String[] titles = getResources().getStringArray(R.array.brandname);
        String[] category = getResources().getStringArray(R.array.categories2);
        String[] desc= getResources().getStringArray(R.array.desc);

        int[] brandcover = {R.drawable.nike, R.drawable.levis, R.drawable.nike, R.drawable.levis, R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.levis,R.drawable.nike};
        int[] brandlogo = {R.drawable.nike, R.drawable.levislogo1, R.drawable.nike, R.drawable.levislogo1, R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.levislogo1,R.drawable.nike};
        for (int i = 0; i < brandcover.length; i++) {
            allbrandsdata current = new allbrandsdata();
            current.brandcover = brandcover[i];
            current.brandname = titles[i];
            current.desc = desc[i];
            current.category = category[i];
            current.brandlogo = brandlogo[i];
            data.add(current);
        }
*/
        return data;
    }

    public void All_Brands_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRAND_TIMELINE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_All_Brands(page.toString(), "1234567890", new Callback<all_brands>() {
                @Override
                public void success(final all_brands server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        if (server_data.getTotal_brands() == 0) {
                            if (page != 0) {
                                mRecyclerView.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new BrandRecyclerViewAdapter(getBaseContext(), data, font, Allbrands.this);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            if (server_data.getSize() > server_data.getTotal_brands()) {
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

                            total_curr = server_data.getTotal_brands();
                            for (int i = 0; i < total_curr; i++) {
                                String brand_id = server_data.getBrands().get(i).getBrand_id();
                                String followed = server_data.getBrands().get(i).getFollowed();
                                Log.d("brand", "followed:" + followed);
                                String brand_name = server_data.getBrands().get(i).getBrand_name();
                                String description = server_data.getBrands().get(i).getDescription();
                                String category = server_data.getBrands().get(i).getCategory();
                                String icon;
                                if (server_data.getBrands().get(i).getIcon() == "" || server_data.getBrands().get(i).getIcon() == null) {
                                    icon = "";
                                } else {
                                    icon = server_data.getBrands().get(i).getIcon();
                                }
                                allbrandsdata current = new allbrandsdata();
                                current.brand_id = brand_id;
                                current.followed = followed;
                                current.brandname = brand_name;
                                current.desc = description;
                                current.category = category;
                                current.brandlogo = icon;
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new BrandRecyclerViewAdapter(getBaseContext(), data, font, Allbrands.this);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataa) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=dataa.getStringExtra("follow_change");
                if (result.equals("yes")) {
                    String[] splited = result.split(",");
                    if (data.get(click_pos).followed.equals("yes")) {
                        data.get(click_pos).followed = "no";
                    } else {
                        data.get(click_pos).followed = "yes";
                    }
                    mAdapter.notifyItemChanged(click_pos);
                } else {

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onItemClicked(int position) {
        this.click_pos = position;
        Intent intent = new Intent(this, brandtimeline.class);
        Bundle b = new Bundle();
        Log.d("timeline", "brand_id:" + data.get(position).brand_id);
        b.putString("brand_id", data.get(position).brand_id);
        intent.putExtras(b);
        startActivityForResult(intent, 1);
    }
}
