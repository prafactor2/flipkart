package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.Momoe_Search_Merchant;
import models.Nearby_Search_All;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.mime.MultipartTypedOutput;

public class All extends Fragment implements searchrecyclerviewadapter.ClickListener {

    private static int firstVisibleInListviews;


    public static String SEARCH_MERCHANT_ENDPOINT = "http://192.168.0.3:1337/nearby_search";
    List<information> data = new ArrayList<>();
    TextView NO_MERCHANT;
    private static searchrecyclerviewadapter mAdapter;
    Integer total_curr;
    Integer list_size;
    private RecyclerView mRecyclerView;
    Context c;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("hii", "oncreate");
        View view;
        view = inflater.inflate(R.layout.activity_all, container, false);
        NO_MERCHANT = (TextView) getActivity().findViewById(R.id.no_merchant);

        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager l = new LinearLayoutManager(c);
        mRecyclerView.setLayoutManager(l);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
     //   mAdapter.setClickListener(this);
        page = 0;
        preLast = 0;
        adapterinitialised = false;

       final FloatingActionButton fab2 = (FloatingActionButton)view.findViewById(R.id.fab2);
       // fab2.setVisibility(View.INVISIBLE);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                scrollToTop();

            }
        });
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

    public Void scrollToTop()
    {
        mRecyclerView.smoothScrollToPosition(0);

        return null;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("hii", "onActivity");
        Nearby_Search_requestData();
    }

    public void itemClicked(View v, int position) {

        if (position == 0) {
            startActivity(new Intent(getActivity(), merchantprofile.class));
        }

    }

    public void Nearby_Search_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(SEARCH_MERCHANT_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Nearby_Search(page.toString(), new Callback<Nearby_Search_All>() {
                @Override
                public void success(final Nearby_Search_All server_data, Response response) {
                    Log.d("function", "near success");
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
                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new searchrecyclerviewadapter(getActivity(), data, font);
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
                                current.total_photos = server_data.getResults().get(i).getTotal_photos();
                                if (current.total_photos  == 0) {

                                } else if (current.total_photos  == 1) {
                                    current.photo_1 = server_data.getResults().get(i).getPhotos().getPhoto_1();
                                } else if (current.total_photos  == 2) {
                                    current.photo_1 = server_data.getResults().get(i).getPhotos().getPhoto_1();
                                    current.photo_2 = server_data.getResults().get(i).getPhotos().getPhoto_2();
                                } else {
                                    current.photo_1 = server_data.getResults().get(i).getPhotos().getPhoto_1();
                                    current.photo_2 = server_data.getResults().get(i).getPhotos().getPhoto_2();
                                }
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new searchrecyclerviewadapter(getActivity(), data, font);
                              //  mAdapter.setClickListener(Search.class);
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
