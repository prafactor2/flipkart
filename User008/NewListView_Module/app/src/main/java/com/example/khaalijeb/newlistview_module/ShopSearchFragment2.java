package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.brands_search;
import models.brands_search_profile;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 14/11/2015.
 */
public class ShopSearchFragment2 extends Fragment {

    public static String BRANDS_SEARCH_PROFILE_ENDPOINT = "http://192.168.0.9:1337/brand_search_profile";
    List<brandshopsearchdata> data = new ArrayList<>();

    Integer total_curr;
    Integer list_size;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;
    private static MyAdapter2 mAdapter;

    private RecyclerView mRecyclerView;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate layout with recycler view
        View v = inflater.inflate(R.layout.layout_shopsearchfragment2, container, false);
        page = 0;
        adapterinitialised = false;
        BRAND_SEARCH = (EditText) getActivity().findViewById(R.id.shop_search);
        BRAND_SEARCH.addTextChangedListener(mTextEditorWatcher);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.my_recycler_view);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        //get your recycler view and populate it.
        return v;
    }

    public List<brandshopsearchdata> getData(Context c) {
/*
        String[] titles = getResources().getStringArray(R.array.brandname);
        String[] rating = getResources().getStringArray(R.array.rating);


          int[] icon = {R.drawable.p1, R.drawable.nike, R.drawable.nike, R.drawable.nike, R.drawable.nike,R.drawable.nike,R.drawable.nike};
        for (int i = 0; i < rating.length; i++) {
            brandshopsearchdata current = new brandshopsearchdata();

            current.rating = rating[i];
            current.brandname = titles[i];
            current.brandlogo = icon[i];
            data.add(current);
        }
*/
        return data;
    }

    public void Brand_Search_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRANDS_SEARCH_PROFILE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            brand_search = BRAND_SEARCH.getText().toString();
            api.post_Brands_Search_Profile(brand_search, page.toString(), new Callback<brands_search_profile>() {
                @Override
                public void success(final brands_search_profile server_data, Response response) {
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
                                mAdapter = new MyAdapter2(getActivity(), data, font);
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
                                brandshopsearchdata current = new brandshopsearchdata();

                                current.brandname = server_data.getResults().get(i).getBrand_name();
                                current.brand_id = server_data.getResults().get(i).getBrand_id();
                                Log.d("brand_id", "brand_id: " + current.brand_id);

                                current.rating = server_data.getResults().get(i).getRating();
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new MyAdapter2(getActivity(), data, font);
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
