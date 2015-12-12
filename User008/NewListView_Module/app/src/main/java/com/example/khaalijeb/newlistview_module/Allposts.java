package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import models.Brands_All_Posts;
import models.Followed_Posts;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class Allposts extends AppCompatActivity {

    public static String BRANDS_ALL_POST_ENDPOINT = "http://192.168.0.9:1337/show_all_brands_post";
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
    ComplexRecyclerViewAdapterbrandship mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allposts);
        page = 0;
        adapterinitialised = false;
        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");

        rvContacts = (RecyclerView)findViewById(R.id.my_recycler_view);

        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        Brand_All_Posts_requestData();

    }

    public ArrayList<Object> getSampleArrayList(Context c) {
/*
        items.add(new brandstockdata("A website content writer or web content writer is a person who specializes in providing relevant content for websites. Every website has a specific target audience and requires a different type and level of content. Content should contain words (key words) that attract and retain users on a website.","NIKE","1024",R.drawable.nike,R.drawable.nike));
        items.add(new brandothersdata("Thats when it became crystal clear. Yes, we want that. But we are scared of the road to get there. That means I have to look myself in the mirror.", "LEVIS", "1024", R.drawable.nike, R.drawable.nike));
        items.add(new brandothersdata("The first portion of this method deals with determining whether scrolling the", "MY BAR", "1024", R.drawable.nike, R.drawable.nike));
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

    public void Brand_All_Posts_requestData() {
        Log.d("function", "near");
        Log.d("hii", "Momoe Search Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BRANDS_ALL_POST_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_All_Brands_Post(page.toString(), "1234567890", new Callback<Brands_All_Posts>() {
                @Override
                public void success(final Brands_All_Posts server_data, Response response) {
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
                                mAdapter = new ComplexRecyclerViewAdapterbrandship(getBaseContext(), items, font);
                                rvContacts.setAdapter(mAdapter);
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
                                    String followed = server_data.getPosts().get(i).getFollowed();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();

                                    Log.d("all_post", "folowed: " + followed);
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
                                    items.add(new brandoffersdata(post_id, followed, upvoted, brand_name, image_url, icon, up_vote.toString(), title, offer_code, time_posted, terms_and_conditions));
                                    Log.d("unique", "current_offer: " + items.get(items.size() - 1));
                                } else if (server_data.getPosts().get(i).getCategory().equals("stock")) {
                                    String image_url = "";
                                    String post_id = server_data.getPosts().get(i).get_id();
                                    Integer up_vote = server_data.getPosts().get(i).getUp_vote();
                                    String description = server_data.getPosts().get(i).getDescription();
                                    String time_posted = server_data.getPosts().get(i).getTime_posted();
                                    String followed = server_data.getPosts().get(i).getFollowed();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();

                                    Log.d("all_post", "folowed: " + followed);
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

                                    items.add(new brandstockdata(post_id, followed, upvoted, description, brand_name, up_vote.toString(), icon, image_url));
                                } else if (server_data.getPosts().get(i).getCategory().equals("others")) {
                                    String image_url = "";
                                    String post_id = server_data.getPosts().get(i).get_id();
                                    Integer up_vote = server_data.getPosts().get(i).getUp_vote();
                                    String description = server_data.getPosts().get(i).getDescription();
                                    String time_posted = server_data.getPosts().get(i).getTime_posted();
                                    String followed = server_data.getPosts().get(i).getFollowed();
                                    String upvoted = server_data.getPosts().get(i).getUpvoted();

                                    Log.d("all_post", "folowed: " + followed);
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
                                    items.add(new brandothersdata(post_id, followed, upvoted, description, brand_name, up_vote.toString(), icon, image_url));
                                }
                            }
                            if (adapterinitialised) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter = new ComplexRecyclerViewAdapterbrandship(getBaseContext(), items, font);
                                rvContacts.setAdapter(mAdapter);

                                adapterinitialised = true;
                            }
                        }
                        rvContacts.scrollToPosition(firstVis);
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
