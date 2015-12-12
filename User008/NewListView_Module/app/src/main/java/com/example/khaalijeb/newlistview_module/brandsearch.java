package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.Brands_All_Posts;
import models.brands_search;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class brandsearch extends AppCompatActivity {

    public static String BRANDS_SEARCH_ENDPOINT = "http://192.168.0.3:1337/brand_search";
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

    RecyclerView mRecyclerView;
    brandsearchrecyclerviewadapter mAdapter;
    Context c;

    EditText BRAND_SEARCH;
    String brand_search;

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >= 3) {
                page = 0;
                preLast = 0;
                firstVis = 0;
                Log.d("pppp", "page is : " + page);
                Brand_Search_requestData();
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandsearch);
        page = 0;
        adapterinitialised = false;
        BRAND_SEARCH = (EditText) findViewById(R.id.brand_search);
        BRAND_SEARCH.addTextChangedListener(mTextEditorWatcher);

        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        mRecyclerView = (RecyclerView)findViewById(R.id.rvContacts);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
    }

    public List<allbrandsdata> getData(Context c) {
/*
        String[] brandname = getResources().getStringArray(R.array.brandname);
        String[] category = getResources().getStringArray(R.array.categories2);


       // int[] brandcover = {R.drawable.nike, R.drawable.levis, R.drawable.nike, R.drawable.levis, R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.levis,R.drawable.nike};
        int[] brandlogo = {R.drawable.nike, R.drawable.levislogo1, R.drawable.nike, R.drawable.levislogo1, R.drawable.nike,R.drawable.nike,R.drawable.nike,R.drawable.levislogo1,R.drawable.nike};
        for (int i = 0; i < brandname.length; i++) {
            allbrandsdata current = new allbrandsdata();

            current.brandname = brandname[i];
            current.brandlogo = brandlogo[i];
            current.category = category[i];
            data.add(current);
        }
*/
        return data;
    }

    public void Brand_Search_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRANDS_SEARCH_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            brand_search = BRAND_SEARCH.getText().toString();
            api.post_Brands_Search( brand_search, page.toString(), new Callback<brands_search>() {
                @Override
                public void success(final brands_search server_data, Response response) {
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
                                mAdapter = new brandsearchrecyclerviewadapter(getApplicationContext(), data, font);
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
                                allbrandsdata current = new allbrandsdata();

                                current.brandname = server_data.getResults().get(i).getBrand_name();
                                current.brand_id = server_data.getResults().get(i).getBrand_id();
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new brandsearchrecyclerviewadapter(getApplicationContext(), data, font);
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
