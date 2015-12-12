package com.example.khaalijeb.newlistview_module;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;

import models.Merchant_Reviews;
import models.Merchant_cover_lat_lon_tot_rat_rev;
import models.Momoe_Search_Merchant;
import models.merchant_profile;
import models.merchant_profile_stores;
import models.merchant_profile_with_photos;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class merchantprofile extends AppCompatActivity {

    public Integer number_function_call = 0;
    public static String MERCHANT_REVIEWS_ENDPOINT = "http://192.168.0.3:1337/merchant_reviews";
    public static String MERCHANT_COVER_LAT_LON_TOT_REV_RAT_ENDPOINT = "http://192.168.0.3:1337/merchant_cover_lat_lon_tot_rat_rev";
    public static String MERCHANT_PROFILE_ENDPOINT = "http://192.168.0.3:1337/merchant_profile";
    public static String MERCHANT_PROFILE_WITH_PHOTOS_ENDPOINT = "http://192.168.0.3:1337/merchant_profile_with_photos";
    public static String MERCHANT_PROFILE_STORES_ENDPOINT = "http://192.168.0.3:1337/merchant_profile_stores";

    String merchant_jeb_no, shop_name, merchant_address, merchant_photo_1, merchant_photo_2, area1;
    Double merchant_rating;
    Integer total_curr;
    Typeface font;
    ArrayList<Object> items = new ArrayList<>();
    ComplexRecyclerViewAdapter madapter;
    RecyclerView rvContacts;
  //  Object items[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        if (b.getString("from_where").equals("with_photos")) {
            merchant_jeb_no = b.getString("merchant_jeb_no");
            merchant_rating = b.getDouble("merchant_rating");
            merchant_address = b.getString("merchant_address");
            area1 = b.getString("area1");
            shop_name = b.getString("shop_name");
            Log.d("hii", "with photos: " + b.getBoolean("with_photos"));
            items.add(new Header("","", "", "", "", "", ""));
            items.add(new User("ADDRESS", merchant_address));
            items.add(new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", 0, "", "")); // just for initialise
            items.add(new photos(R.drawable.menu));

            items.add(new CONTENT("REVIEW"));
            Merchant_Profile_With_Photos_requestData();
        } else if (b.getString("from_where").equals("stores")) {
            merchant_jeb_no = b.getString("merchant_jeb_no");
            merchant_address = b.getString("merchant_address");
            area1 = b.getString("area1");
            items.add(new Header("","", "", "", "", "", ""));
            items.add(new User("ADDRESS", ""));
            items.add(new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", 0, "", "")); // just for initialise
            items.add(new photos(R.drawable.menu));
            Merchant_Profile_Stores_requestData();

        } else if (b.getString("from_where").equals("search")) {
            merchant_jeb_no = b.getString("merchant_jeb_no");
            merchant_rating = b.getDouble("merchant_rating");
            merchant_address = b.getString("merchant_address");
            area1 = b.getString("area1");
            shop_name = b.getString("shop_name");
            Log.d("hii", "with photos: " + b.getBoolean("with_photos"));
            Integer total_photos = b.getInt("total_photos");
            if (total_photos == 0) {
                merchant_photo_1 = "";
                merchant_photo_2 = "";
            } else if (total_photos == 1) {
                merchant_photo_1 = b.getString("merchant_photo_1");
                merchant_photo_2 = "";
            } else if (total_photos == 2) {
                merchant_photo_1 = b.getString("merchant_photo_1");
                merchant_photo_2 = b.getString("merchant_photo_2");
            } else {
                merchant_photo_1 = b.getString("merchant_photo_1");
                merchant_photo_2 = b.getString("merchant_photo_2");
            }
            items.add(new Header("", "", "", "", "", "", ""));
            items.add(new User("ADDRESS", merchant_address));
            items.add(new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, merchant_photo_1, merchant_photo_2));
            items.add(new photos(R.drawable.menu));

            items.add(new CONTENT("REVIEW"));
            Merchant_Profile_requestData();
        }

        Context c;
        setContentView(R.layout.activity_merchantprofile);

        font = Typeface.createFromAsset(getAssets(), "fonts/cae.ttf");
        rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_merchantprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Merchant_Profile_requestData() {
        Log.d("hii", "Merchant Profile Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_PROFILE_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Merchant_profile(merchant_jeb_no, new Callback<merchant_profile>() {
                @Override
                public void success(final merchant_profile server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        String cover;
                        if (server_data.getMerchant().getCover() == null) {
                            cover = "";
                        } else {
                            cover = server_data.getMerchant().getCover();
                        }
                        Integer total_votes = server_data.getMerchant().getTotal_ratings();
                        Integer total_reviews = server_data.getMerchant().getTotal_reviews();
                        String city = server_data.getMerchant().getAddress().getCity();
                        items.set(0, new Header(cover, merchant_jeb_no, shop_name, area1 + ", " + city, total_votes.toString(), merchant_rating.toString(), total_reviews.toString()));
                        if (server_data.getReviews().size() == 0) {
                            //Future            NO_REVIEWS.setVisibility(View.VISIBLE);
                        } else {
                            //Future            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            total_curr = server_data.getReviews().size();
                            for (int i = 0; i < total_curr; i++) {
                                String review = server_data.getReviews().get(i).getReview();
                                Double rating = server_data.getReviews().get(i).getRating();
                                String username = server_data.getReviews().get(i).getUsername();
                                Log.d("cover", "cover: " + username);
                                if (i == 2) {
                                    items.add(new REVIEW2(username, review, "" + rating));
                                } else {
                                    items.add(new REVIEW(username, review, "" + rating));
                                }
                            }
                        }
                        madapter = new ComplexRecyclerViewAdapter(merchantprofile.this, items, font);
                        rvContacts.setAdapter(madapter);
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

    public void Merchant_Profile_With_Photos_requestData() {
        Log.d("hii", "Merchant Profile With Photos Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_PROFILE_WITH_PHOTOS_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Merchant_profile_with_photos(merchant_jeb_no, new Callback<merchant_profile_with_photos>() {
                @Override
                public void success(final merchant_profile_with_photos server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        String cover;
                        if (server_data.getMerchant().getCover() == null) {
                            cover = "";
                        } else {
                            cover = server_data.getMerchant().getCover();
                        }
                        Integer total_votes = server_data.getMerchant().getTotal_ratings();
                        Integer total_reviews = server_data.getMerchant().getTotal_reviews();
                        String city = server_data.getMerchant().getAddress().getCity();
                        items.set(0, new Header(cover, merchant_jeb_no, shop_name, area1 + ", " + city, total_votes.toString(), merchant_rating.toString(), total_reviews.toString()));
                        Integer total_photos = server_data.getTotal_photos();

                        if (total_photos == 0) {

                        } else if (total_photos == 1) {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, ""));
                        } else if (total_photos == 2) {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            String photo_2 = server_data.getPhotos().getPhoto_2();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, photo_2));
                        } else {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            String photo_2 = server_data.getPhotos().getPhoto_2();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, photo_2));
                        }
                        if (server_data.getReviews().size() == 0) {
                            //Future            NO_REVIEWS.setVisibility(View.VISIBLE);
                        } else {
                            //Future            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            total_curr = server_data.getReviews().size();
                            for (int i = 0; i < total_curr; i++) {
                                String review = server_data.getReviews().get(i).getReview();
                                Double rating = server_data.getReviews().get(i).getRating();
                                String username = server_data.getReviews().get(i).getUsername();
                                Log.d("cover", "cover: " + username);
                                if (i == 2) {
                                    items.add(new REVIEW2(username, review, "" + rating));
                                } else {
                                    items.add(new REVIEW(username, review, "" + rating));
                                }
                            }
                        }
                        madapter = new ComplexRecyclerViewAdapter(merchantprofile.this, items, font);
                        rvContacts.setAdapter(madapter);
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

    public void Merchant_Profile_Stores_requestData() {
        Log.d("hii", "Merchant Profile With Photos Request Data");
        OkHttpClient client = new OkHttpClient();
        client = MySSLTrust.trustcert(this);
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MERCHANT_PROFILE_STORES_ENDPOINT).setClient(new OkClient(client)).build();
        Log.d("hii", "1");
        final RetrofitInterface api = adapter.create(RetrofitInterface.class);
        Log.d("hii", "2");
        if (api != null) {
            Log.d("hii", "api null nhi hai");
            api.post_Merchant_profile_stores(merchant_jeb_no, new Callback<merchant_profile_stores>() {
                @Override
                public void success(final merchant_profile_stores server_data, Response response) {
                    Log.d("hii", "3");
                    Log.d("hii", "Success : " + server_data.getSuccess());
                    Log.d("hii", "Message : " + server_data.getMessage());
                    if (server_data.getSuccess() == 1) {
                        //Future        NO_MERCHANT.setVisibility(View.INVISIBLE);
                        String cover;
                        if (server_data.getMerchant().getCover() == null) {
                            cover = "";
                        } else {
                            cover = server_data.getMerchant().getCover();
                        }
                        Integer total_votes = server_data.getMerchant().getTotal_ratings();
                        Integer total_reviews = server_data.getMerchant().getTotal_reviews();
                        String city = server_data.getMerchant().getAddress().getCity();
                        String shop_name = server_data.getMerchant().getShop_name();
                        String area2;
                        if (server_data.getMerchant().getAddress().getArea2() == null) {
                            area2 = "";
                            items.set(1, new User("ADDRESS", merchant_address + ", " + area1));
                        } else {
                            area2 = server_data.getMerchant().getAddress().getArea2();
                            items.set(1, new User("ADDRESS", merchant_address + ", " + area2 + ", " + area1));
                        }
                        double rat = server_data.getMerchant().getRating();
                        items.set(0, new Header(cover, merchant_jeb_no, shop_name, area1 + ", " + city, total_votes.toString(), ""+ rat, total_reviews.toString()));
                        Integer total_photos = server_data.getTotal_photos();

                        if (total_photos == 0) {

                        } else if (total_photos == 1) {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, ""));
                        } else if (total_photos == 2) {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            String photo_2 = server_data.getPhotos().getPhoto_2();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, photo_2));
                        } else {
                            String photo_1 = server_data.getPhotos().getPhoto_1();
                            String photo_2 = server_data.getPhotos().getPhoto_2();
                            items.set(2, new com.example.khaalijeb.newlistview_module.Menu("PHOTOS", total_photos, photo_1, photo_2));
                        }
                        if (server_data.getReviews().size() == 0) {
                            //Future            NO_REVIEWS.setVisibility(View.VISIBLE);
                        } else {
                            //Future            NO_MERCHANT.setVisibility(View.INVISIBLE);
                            total_curr = server_data.getReviews().size();
                            for (int i = 0; i < total_curr; i++) {
                                String review = server_data.getReviews().get(i).getReview();
                                double rating = server_data.getReviews().get(i).getRating();
                                String username = server_data.getReviews().get(i).getUsername();
                                Log.d("cover", "cover: " + username);
                                if (i == 2) {
                                    items.add(new REVIEW2(username, review, "" + rating));
                                } else {
                                    items.add(new REVIEW(username, review, "" + rating));
                                }
                            }
                        }
                        madapter = new ComplexRecyclerViewAdapter(merchantprofile.this, items, font);
                        rvContacts.setAdapter(madapter);
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
