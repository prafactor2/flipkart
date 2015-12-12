package com.example.khaalijeb.newlistview_module;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import models.All_Offers;
import models.Nearby_Search_All;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Offers extends Fragment implements Offersrecyclerview.ClickListener {

    CoordinatorLayout snackbar;
    public static String ALL_OFFERS_ENDPOINT = "http://192.168.0.3:1337/get_offers";
    Typeface font;
    List<offersdata> data = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    Integer total_curr;
    Integer list_size;
    RecyclerView mRecyclerView;
    Offersrecyclerview mAdapter;
    Context c;
    Integer page;
    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_offers, container, false);
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        snackbar = (CoordinatorLayout)view.findViewById(R.id.snackbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        page = 0;
        preLast = 0;
        adapterinitialised = false;

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("offers", "offers 1");
        Get_All_Offers_requestData();
        Log.d("offers", "offers 2");
        final FloatingActionMenu menu1 = (FloatingActionMenu) getActivity().findViewById(R.id.menu1);
     /*   menu1.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.scale_up));
        menu1.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down));
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu1.showMenuButton(true);
            }
        }, 100);
*/
        menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu1.isOpened()) {

                    LinearLayout l = (LinearLayout) getActivity().findViewById(R.id.c1);
                    l.setBackgroundColor(getResources().getColor(R.color.white));
                }
                menu1.toggle(true);
            }
        });

        menu1.setClosedOnTouchOutside(true);


        createCustomAnimation();
    }

    private void createCustomAnimation() {
        final FloatingActionMenu menu1 = (FloatingActionMenu) getView().findViewById(R.id.menu1);

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu1.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu1.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu1.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu1.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu1.getMenuIconView().setImageResource(menu1.isOpened()
                        ? R.drawable.filter2 : R.drawable.close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu1.setIconToggleAnimatorSet(set);
    }

    @Override
    public void itemClicked(View v, int position) {

    }

    public void Get_All_Offers_requestData() {
        Log.d("hii", "Get All Offers Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ALL_OFFERS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_All_Offers(page.toString(), new Callback<All_Offers>() {
                @Override
                public void success(final All_Offers server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        Log.d("size", "size " + server_data.getSize());
                        Log.d("hii", "" + server_data.getOffers().size());
                        if (server_data.getOffers().size() == 0) {
                            if (page != 0) {
                                mRecyclerView.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                mAdapter = new Offersrecyclerview(getActivity(), data, font, snackbar);
                                mRecyclerView.setAdapter(mAdapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            Log.d("offers", "size: " + server_data.getSize() + ", offers.size: " + server_data.getOffers().size());
                            if (server_data.getSize() > server_data.getOffers().size()) {
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
                            total_curr = server_data.getOffers().size();

                            for (int i = 0; i < total_curr; i++) {
                                offersdata current = new offersdata();
                                if (server_data.getOffers().get(i).getTitle() == null) {
                                    Log.d("offers", "null h ");
                                }
                                current.details = server_data.getOffers().get(i).getTitle();
                                current.categories = server_data.getOffers().get(i).getType();
                                current.brandlogo = server_data.getOffers().get(i).getIcon_url();
                                current.offercover = server_data.getOffers().get(i).getImage_url();
                                current.snackbar = server_data.getOffers().get(i).getOffer_code();
                                data.add(current);
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new Offersrecyclerview(getActivity(), data, font, snackbar);
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
