package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import models.Coupons;
import models.Nearby_Search_All;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by Lord Voldemort on 28/11/2015.
 */
public class PromoCodeItem1Fragment extends Fragment {

    public static String COUPON_FRAGMENT1_ENDPOINT = "http://192.168.0.9:1337/coupon_fragment1";

    RecyclerView mRecyclerView;
    PromoCodeRecyclerViewAdapter mAdapter;
    List<promocodedata> data = new ArrayList<>();
    Context c;
    Integer page;
    Integer list_size;
    Integer total_curr;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_pc_item1_fragment, container, false);
        page = 0;
        preLast = 0;
        adapterinitialised = false;
       font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
        Coupon_Fragment1_requestdata();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


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

        return view;
    }


    public List<promocodedata> getData(Context c) {
    /*    String[] brandname = getResources().getStringArray(R.array.brandname);
        String[] title = getResources().getStringArray(R.array.details2);
        String[] Amount = {"free","Rs.200","free","Rs.32","free","Rs.39","Rs.232","Rs.54","free"};


        int[] icon = {R.drawable.nike,R.drawable.levislogo1, R.drawable.nike,R.drawable.levislogo1, R.drawable.nike,R.drawable.levislogo1,R.drawable.nike,R.drawable.levislogo1,R.drawable.nike};
        for (int i = 0; i < icon.length; i++) {
           promocodedata current = new promocodedata();
            current.brandicon = icon[i];
            current.brandname = brandname[i];
            current.title = title[i];
            current.Amount = Amount[i];

            data.add(current);
        }
*/
        return data;
    }

    public void Coupon_Fragment1_requestdata() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(COUPON_FRAGMENT1_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Coupons_Fragment1(page.toString(), new Callback<Coupons>() {
                @Override
                public void success(final Coupons server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Log.d("size", "size " + server_data.getSize());
                        if (server_data.getAll_coupons().size() == 0) {
                            if (page != 0) {
                                mRecyclerView.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new PromoCodeRecyclerViewAdapter(getActivity(), data, font, (PromoCodeActivity) getActivity());
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            if (server_data.getSize() > server_data.getAll_coupons().size()) {
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
                            total_curr = server_data.getAll_coupons().size();

                            for (int i = 0; i < total_curr; i++) {
                                promocodedata current = new promocodedata();
                                current.brandicon = server_data.getAll_coupons().get(i).getMerchant_icon();
                                current.brandname = server_data.getAll_coupons().get(i).getMerchant_name();
                                current.title = server_data.getAll_coupons().get(i).getTitle();
                                current.Amount = server_data.getAll_coupons().get(i).getAmount();

                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new PromoCodeRecyclerViewAdapter(getActivity(), data, font, (PromoCodeActivity) getActivity());
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
