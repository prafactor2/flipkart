package com.example.khaalijeb.newlistview_module;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.squareup.okhttp.OkHttpClient;

import java.awt.font.TextAttribute;
import java.util.ArrayList;

import models.Followed_Posts;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Brandship extends Fragment {

    private static final long ANIMATION_DURATION =  1200;

    public interface MyBrandshipListener{
        public Integer nofollownotifications();
    }

    private MyBrandshipListener callerActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callerActivity = (MyBrandshipListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            Log.d("attach", "visible onmenu of HomeFragment");
            final String PREFS_NAME = "MyPrefsFile";
            SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
            Integer x = settings.getInt("follow_notifications", 0);
            if (x != 0) {
                settings.edit().putInt("follow_notifications", 0).apply();
                Log.d("onattach", "hiii");
                callerActivity.nofollownotifications();
            }
        }
    }

    public static String BRAND_TIMELINE_ENDPOINT = "http://192.168.0.9:1337/show_following_brands_post";
    ArrayList<Object> items = new ArrayList<>();
    Integer total_curr;
    Integer list_size;
    Integer page;

    int firstVis = 0;
    int preLast = 0;
    Integer prev_total;
    boolean stay_on_this_page;
    boolean adapterinitialised;
    Typeface font;
    Context c;
    RecyclerView rvContacts;
    ComplexRecyclerViewAdapterbrandship madapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_brandship,container,false);

        rvContacts = (RecyclerView)view.findViewById(R.id.rvContacts);
        rvContacts.setHasFixedSize(true);
        rvContacts.setLayoutManager(new LinearLayoutManager(c));
        font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cae.ttf");

        page = 0;
        adapterinitialised = false;


        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Brand_Posts_requestData();

        TextView allbrands = (TextView)getView().findViewById(R.id.allbrands);
        TextView allposts = (TextView)getView().findViewById(R.id.allposts);
        ImageView searchbrands = (ImageView)getView().findViewById(R.id.brandsearch);

        allbrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),Allbrands.class);

                startActivity(i);


            }
        });

        allposts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(),Allposts.class);

                startActivity(i);


            }
        });

        searchbrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), brandsearch.class);

                startActivity(i);


            }
        });


        final FloatingActionMenu menu2 = (FloatingActionMenu)getActivity().findViewById(R.id.menu2);
     /*   menu1.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(),R.anim.scale_up));
        menu1.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_down));
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menu1.showMenuButton(true);
            }
        }, 100);
*/

        menu2.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menu2.isOpened()) {

                    LinearLayout l = (LinearLayout) getActivity().findViewById(R.id.c2);
                    l.setBackgroundColor(getResources().getColor(R.color.white));
                }
                menu2.toggle(true);
            }
        });

        menu2.setClosedOnTouchOutside(true);


        createCustomAnimation();
        
    }



    public ArrayList<Object> getSampleArrayList(Context c) {

/*
        items.add(new brandstockdata("A website content writer or web content writer is a person who specializes in providing relevant content for websites. Every website has a specific target audience and requires a different type and level of content. Content should contain words (key words) that attract and retain users on a website.","NIKE","1024",R.drawable.nike,R.drawable.nike));
        items.add(new brandothersdata("Thats when it became crystal clear. Yes, we want that. But we are scared of the road to get there. That means I have to look myself in the mirror.","LEVIS","1024",R.drawable.nike,R.drawable.nike));
        items.add(new brandothersdata("The first portion of this method deals with determining whether scrolling the","MY BAR","1024",R.drawable.nike,R.drawable.nike));
        items.add(new brandstockdata("A file photo of eminent cartoonist RK Laxman who used his iconic 'comman man' character to create satire targeting politicians for more than five decades. Here's a compilation of some of his best cartoons. ", "LEVIS", "10", R.drawable.nike, R.drawable.nike));
        items.add(new brandoffersdata("WRANGLER",R.drawable.nike,R.drawable.nike,"100","Wrangler Clothing Minimum 50% off to 60 % off from Rs. 358","NOW500","06/01/2016","code123123131"));
        items.add(new brandstockdata("501 ORIGINAL & 501 CTWHETHER YOU LIKE AN ICONIC STRAIGHT FIT OR A PERFECT CUSTOM TAPER, WE'VE GOT YOU. NOW IN NEW WASHES.", "LEVIS", "10", R.drawable.nike, R.drawable.nike));
        items.add(new brandoffersdata("CINEPOLIS", R.drawable.nike, R.drawable.nike, "100", "Get 50% discount on the 2nd ticket!! This offer is applicable only for VIP tickets booked with Cinepolis Kochi.", "GET500", "29/10/2015", "\"1. This voucher entitles you to a Rs. 500/- discount on a minimum bill value of Rs. 2000/- at Peter England exclusive branded outlets only.\n\n" +
                "2. This discount is valid on apparel products only \\n\\n\" +\n" +
                "3. One voucher is valid per visit \\n\\n\" +\n" +
                "4. One customer can avail the offer only once (valid on a single bill and a registered mobile number)\n\n" +
                "5. To avail the discount you are required to present the print out/ e-copy/ unique voucher code at the Peter England store and register your details\n\n" +
                "6. This offer is valid only on Full MRP Merchandise\n\n" +
                "7. Offer under this voucher is not valid in conjunction with any other offer / scheme / discount coupons/ discounted products\n\n" +
                "8. Voucher cannot be exchanged for cash\n\n" +
                "9. Offer valid between Oct 9th, 2015 � Oct 31st , 2015 \n\n" +
                "10. The merchant store�s rules and regulations apply \n\n" +
                "11. Please visit http://www.peterengland.com/store.php for store locations\n\n"));

*/
        return items;
    }
        private void createCustomAnimation() {
            final FloatingActionMenu menu2 = (FloatingActionMenu) getActivity().findViewById(R.id.menu2);

            AnimatorSet set = new AnimatorSet();

            ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu2.getMenuIconView(), "scaleX", 1.0f, 0.2f);
            ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu2.getMenuIconView(), "scaleY", 1.0f, 0.2f);

            ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu2.getMenuIconView(), "scaleX", 0.2f, 1.0f);
            ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu2.getMenuIconView(), "scaleY", 0.2f, 1.0f);

            scaleOutX.setDuration(50);
            scaleOutY.setDuration(50);

            scaleInX.setDuration(150);
            scaleInY.setDuration(150);

            scaleInX.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    menu2.getMenuIconView().setImageResource(menu2.isOpened()
                            ? R.drawable.filter2: R.drawable.close);
                }
            });

            set.play(scaleOutX).with(scaleOutY);
            set.play(scaleInX).with(scaleInY).after(scaleOutX);
            set.setInterpolator(new OvershootInterpolator(2));

            menu2.setIconToggleAnimatorSet(set);
        }

    public void Brand_Posts_requestData() {
        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
        String mobile_no = settings.getString("mobile_no", "");

        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(getActivity());
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRAND_TIMELINE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Following_Brands_Post(page.toString(), mobile_no, new Callback<Followed_Posts>() {
                @Override
                public void success(final Followed_Posts server_data, Response response) {
                    Log.d("function", "near success");
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        if (server_data.getTotal_posts() == 0) {
                            if (page != 0) {
                                rvContacts.scrollToPosition(firstVis);
                            } else {
                                Log.d("hii", "zero");
                                //                NO_MERCHANT.setVisibility(View.VISIBLE);
                                madapter = new ComplexRecyclerViewAdapterbrandship(getActivity(),getSampleArrayList(c),font);
                                rvContacts.setAdapter(madapter);
                            }
                        } else {
                            //            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            if (server_data.getSize() > server_data.getTotal_posts()) {
                                stay_on_this_page = true;
                            } else {
                                stay_on_this_page = false;
                            }
                            if (page == 0) {
                                prev_total = 0;
                                items.clear();
                                Log.d("hii", "Page is zero " + prev_total);
                                list_size = server_data.getSize();
                            } else {
                                Log.d("hii", "prev_total is " + prev_total + " Size is " + server_data.getSize() + " page is " + page);
                                prev_total = server_data.getSize() * page;
                                Log.d("hii", "Now prev_total is " + prev_total);
                            }

                            total_curr = server_data.getTotal_posts();
                            for (int i = 0; i < total_curr; i++) {
                                Log.d("unique", server_data.getPosts().get(i).getCategory());
                                if (server_data.getPosts().get(i).getCategory().equals("offer")) {
                                    String image_url = "";
                                    String post_id = server_data.getPosts().get(i).get_id();
                                    Integer up_vote = server_data.getPosts().get(i).getUp_vote();
                                    String title = server_data.getPosts().get(i).getTitle();
                                    String terms_and_conditions = server_data.getPosts().get(i).getTerms_and_conditions();
                                    String offer_code = server_data.getPosts().get(i).getOffer_code();
                                    String expir_date = server_data.getPosts().get(i).getExpir_date();
                                    String time_posted = server_data.getPosts().get(i).getTime_posted();
                                    String brand_name = server_data.getPosts().get(i).getBrand_name();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();
                                    String icon;
                                    if (server_data.getPosts().get(i).getIcon() == "" || server_data.getPosts().get(i).getIcon() == null) {
                                        icon = "";
                                    } else {
                                        icon = server_data.getPosts().get(i).getIcon();
                                    }

                                    if (server_data.getPosts().get(i).getImage_url() == null) {
                                        image_url = "";
                                    } else {
                                        image_url = server_data.getPosts().get(i).getImage_url();
                                    }
                                    Log.d("unique", "offer: " + "aya");
                                    items.add(new brandoffersdata(post_id, "yes", upvoted, brand_name, image_url, icon, up_vote.toString(), title, offer_code, time_posted, terms_and_conditions));
                                    Log.d("unique", "current_offer: " + items.get(items.size() - 1));
                                } else if (server_data.getPosts().get(i).getCategory().equals("stock")) {
                                    String image_url = "";
                                    Integer up_vote = server_data.getPosts().get(i).getUp_vote();
                                    String description = server_data.getPosts().get(i).getDescription();
                                    String post_id = server_data.getPosts().get(i).get_id();
                                    String time_posted = server_data.getPosts().get(i).getTime_posted();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();

                                    if (server_data.getPosts().get(i).getImage_url() == null) {
                                        image_url = "";
                                    } else {
                                        image_url = server_data.getPosts().get(i).getImage_url();
                                    }
                                    String brand_name = server_data.getPosts().get(i).getBrand_name();
                                    String icon;
                                    if (server_data.getPosts().get(i).getIcon() == "" || server_data.getPosts().get(i).getIcon() == null) {
                                        icon = "";
                                    } else {
                                        icon = server_data.getPosts().get(i).getIcon();
                                    }

                                    items.add(new brandstockdata(post_id, "yes", upvoted, description, brand_name, up_vote.toString(), icon, image_url));
                                }else if (server_data.getPosts().get(i).getCategory().equals("others")) {
                                    String image_url = "";
                                    String post_id = server_data.getPosts().get(i).get_id();
                                    Integer up_vote = server_data.getPosts().get(i).getUp_vote();
                                    String description = server_data.getPosts().get(i).getDescription();
                                    String time_posted = server_data.getPosts().get(i).getTime_posted();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();

                                    if (server_data.getPosts().get(i).getImage_url() == null) {
                                        image_url = "";
                                    } else {
                                        image_url = server_data.getPosts().get(i).getImage_url();
                                    }
                                    String brand_name = server_data.getPosts().get(i).getBrand_name();
                                    String icon;
                                    if (server_data.getPosts().get(i).getIcon() == "" || server_data.getPosts().get(i).getIcon() == null) {
                                        icon = "";
                                    } else {
                                        icon = server_data.getPosts().get(i).getIcon();
                                    }
                                    items.add(new brandothersdata(post_id, "yes", upvoted, description, brand_name, up_vote.toString() , icon , image_url));
                                }
                            }
                            if (adapterinitialised) {
                                madapter.notifyDataSetChanged();
                            } else {
                                madapter = new ComplexRecyclerViewAdapterbrandship(getActivity(),getSampleArrayList(c),font);
                                rvContacts.setAdapter(madapter);

                                adapterinitialised = true;
                            }
                        }
                        rvContacts.scrollToPosition(firstVis);
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
