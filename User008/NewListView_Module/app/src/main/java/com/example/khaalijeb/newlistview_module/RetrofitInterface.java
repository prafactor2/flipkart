package com.example.khaalijeb.newlistview_module;

import models.All_Offers;
import models.Apply_Coupon;
import models.Bill_Generator;
import models.BrandApp_Profile;
import models.Brand_Timeline;
import models.Brands_All_Posts;
import models.Check_Pin_model;
import models.Coupon_Info;
import models.Coupons;
import models.Followed_Posts;
import models.Login;
import models.Merchant_All_Reviews;
import models.Merchant_Jeeb_Enter;
import models.Merchant_Reviews;
import models.Merchant_cover_lat_lon_tot_rat_rev;
import models.Momoe_Search_Merchant;
import models.Nearby_Search_All;
import models.Uber_Price;
import models.Uber_Time;
import models.Wallet_Username;
import models.all_brands;
import models.brand_profile_stores;
import models.brands_search;
import models.brands_search_profile;
import models.getLocation;
import models.location_search;
import models.merchant_all_photos;
import models.merchant_all_pricesheets;
import models.merchant_profile;
import models.merchant_profile_stores;
import models.merchant_profile_with_photos;
import models.success_message;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Davinci on 5/27/2015.
 */
public interface RetrofitInterface {


    /* To get Retrofit working asynchronously you need two things
    1. The method must have a return type of void
    2. The last argument to the method must be a Callback generically typed to what you acutally want returned
     */

    @GET("/")
    public void getFeed(@Query("jeeb_no") String jeeb_no, @Query("password") String password, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Login(@Field("jeeb_no") String jeeb_no, @Field("password") String password, @Field("reg_id") String reg_id, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Register(@Field("jeeb_no") String jeeb_no, @Field("password") String password, @Field("email_id") String email_id, @Field("mobile_no") String mobile_no, @Field("jeeb_name") String jeeb_name, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Wallet_Username(@Field("jeeb_no") String jeeb_no, Callback<Wallet_Username> response);

    @FormUrlEncoded
    @POST("/")
    public void post_AddMoney(@Field("jeeb_no") String jeeb_no, @Field("money") String money, @Field("promo_code") String promo_code, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_SendMoney(@Field("jeeb_no") String jeeb_no, @Field("sender_no") String sender_no, @Field("amount") String amount, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_PayMoney(@Field("jeeb_no") String jeeb_no, @Field("sender_no") String sender_no, @Field("amount") String amount, @Field("promo_code") String promo_code, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_PayPinMoney(@Field("jeeb_no") String jeeb_no, @Field("sender_no") String sender_no, @Field("amount") String amount, @Field("promo_code") String promo_code, @Field("pin") String pin, Callback<Login> response);


    @FormUrlEncoded
    @POST("/")
    public void post_ApplyCoupon(@Field("money") double money, @Field("promo_code") String promo_code, Callback<Apply_Coupon> response);

    @FormUrlEncoded
    @POST("/")
    public void post_MerchJeebEnter(@Field("jeeb_no") String jeeb_no, Callback<Merchant_Jeeb_Enter> response);

    @FormUrlEncoded
    @POST("/")
    public void post_CheckPin(@Field("jeeb_no") String jeeb_no, Callback<Check_Pin_model> response);

    @FormUrlEncoded
    @POST("/")
    public void post_UpdatePassword(@Field("jeeb_no") String jeeb_no, @Field("curr_pass") String curr_pass, @Field("new_pass") String new_pass, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_UpdateUsername(@Field("jeeb_no") String jeeb_no, @Field("password") String password, @Field("username") String username, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_SubmitPin(@Field("jeeb_no") String jeeb_no, @Field("pin") String pin, @Field("password") String password, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_ChangePin(@Field("jeeb_no") String jeeb_no, @Field("new_pin") String new_pin, @Field("password") String password, @Field("old_pin") String old_pin, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_RemovePin(@Field("jeeb_no") String jeeb_no, @Field("old_pin") String old_pin, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_GetLocation(@Field("lats") double lats, @Field("longs") double longs, Callback<getLocation> response);

    @FormUrlEncoded
    @POST("/")
    public void post_SubmitReview(@Field("user_id") String user_id, @Field("merchant_id") String merchant_id, @Field("review") String review, @Field("anonymous") Integer anonymous, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_SubmitRating(@Field("user_id") String user_id, @Field("merchant_id") String merchant_id, @Field("rating") String rating, @Field("anonymous") Integer anonymous, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_SubmitImprovement(@Field("user_id") String user_id, @Field("merchant_id") String merchant_id, @Field("improvement") String improvement, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Momoe_Search_Merchant(@Field("shop_search") String shop_search, @Field("location_search") String location_search, @Field("curr_city") String curr_city, @Field("page") String page, @Field("curr_lat") double curr_lat, @Field("curr_lon") double curr_lon, Callback<Momoe_Search_Merchant> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_Reviews(@Field("merchant_id") String merchant_id, Callback<Merchant_Reviews> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_All_Reviews(@Field("merchant_id") String merchant_id, @Field("page") String page, Callback<Merchant_All_Reviews> response);

    @FormUrlEncoded
    @POST("/")
    public void post_All_Offers(@Field("page") String page, Callback<All_Offers> response);

    @Multipart
    @POST("/")
    public void post_merchant_posts(@Part("brand_post") TypedFile brand_post1, @Part("brand_post") TypedFile brand_post2, @Part("brand_post") TypedFile brand_post3, @Query("merchant_id") String merchant_id, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_App_Register_Ids(@Field("reg_id") String reg_id, Callback<Login> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Remove_Follow_Notifications(@Field("mobile_no") String mobile_no, @Field("reg_id") String reg_id, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Remove_Notifications(@Field("mobile_no") String mobile_no, @Field("reg_id") String reg_id, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Nearby_Search(@Field("page") String page, Callback<Nearby_Search_All> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_cover_lat_lon_tot_rat_rev(@Field("jeb_no") String jeb_no, Callback<Merchant_cover_lat_lon_tot_rat_rev> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_profile(@Field("jeb_no") String jeb_no, Callback<merchant_profile> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_profile_with_photos(@Field("jeb_no") String jeb_no, Callback<merchant_profile_with_photos> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_profile_stores(@Field("jeb_no") String jeb_no, Callback<merchant_profile_stores> response);


    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_all_photos(@Field("jeb_no") String jeb_no, @Field("page") Integer page, Callback<merchant_all_photos> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Brand_Timeline(@Field("page") String page, @Field("brand_id") String brand_id, @Field("mobile_no") String mobile_no, Callback<Brand_Timeline> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Merchant_all_pricesheets(@Field("jeb_no") String jeb_no, @Field("page") Integer page, Callback<merchant_all_pricesheets> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Following_Brands_Post(@Field("page") String page, @Field("mobile_no") String mobile_no, Callback<Followed_Posts> response);

    @FormUrlEncoded
    @POST("/")
    public void post_All_Brands(@Field("page") String page, @Field("mobile_no") String mobile_no, Callback<all_brands> response);

    @FormUrlEncoded
    @POST("/")
    public void post_All_Brands_Post(@Field("page") String page, @Field("mobile_no") String mobile_no, Callback<Brands_All_Posts> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Brands_Search(@Field("brand_search") String brand_search, @Field("page") String page, Callback<brands_search> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Brands_Search_Profile(@Field("brand_search") String brand_search, @Field("page") String page, Callback<brands_search_profile> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Brand_Profile_Stores(@Field("brand_id") String brand_id, @Field("shop_type") String shop_type, @Field("city") String city, @Field("latitude") double latitude, @Field("longitude") double longitude, Callback<brand_profile_stores> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Location_Search(@Field("location_search") String location_search, @Field("page") String page, @Field("curr_city") String curr_city, Callback<location_search> response);


    @FormUrlEncoded
    @POST("/")
    public void post_Logout(@Field("jeeb_no") String jeeb_no, @Field("reg_id") String reg_id, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_User_Upvote(@Field("mobile_no") String mobile_no, @Field("post_id") String post_id, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_User_Downvote(@Field("mobile_no") String mobile_no, @Field("post_id") String post_id, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_User_Follow(@Field("mobile_no") String mobile_no, @Field("brand_id") String brand_id,  @Field("follow") Integer follow, Callback<success_message> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Brand_Profile(@Field("brand_id") String brand_id, Callback<BrandApp_Profile> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Coupon_Info(@Field("_id") String _id, Callback<Coupon_Info> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Bill_generator(@Field("amount") double amount, Callback<Bill_Generator> response);

    @FormUrlEncoded
    @POST("/")
    public void post_Coupons_Fragment1(@Field("page") String page, Callback<Coupons> response);

    @GET("/time")
    public void get_Uber_Time(@Query("start_latitude") float start_latitude, @Query("start_longitude") float start_longitude, Callback<Uber_Time> response);

    @GET("/price")
    public void get_Uber_Price(@Query("start_latitude") float start_latitude, @Query("start_longitude") float start_longitude, @Query("start_latitude") float end_latitude, @Query("start_longitude") float end_longitude, Callback<Uber_Price> response);



}

