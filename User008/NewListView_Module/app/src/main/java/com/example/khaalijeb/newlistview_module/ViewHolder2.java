package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 04/10/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewHolder2 extends RecyclerView.ViewHolder {

    private ImageView ivExample;
    ImageView like;
    TextView Merchant_Area;
    TextView Merchant_rating;

    public TextView getMerchant_votes() {
        return Merchant_votes;
    }

    public void setMerchant_votes(TextView merchant_votes) {
        Merchant_votes = merchant_votes;
    }

    TextView Merchant_votes;

    public ViewHolder2(View v) {
        super(v);
        ivExample = (ImageView) v.findViewById(R.id.ivExample);
        Merchant_title = (TextView)v.findViewById(R.id.merchant_title);
        Merchant_Area = (TextView)v.findViewById(R.id.merchant_area);
        Merchant_votes = (TextView)v.findViewById(R.id.merchant_votes);
        Merchant_rating = (TextView)v.findViewById(R.id.merchant_rating);
        Merchant_review = (TextView)v.findViewById(R.id.merchant_review);
        like = (ImageView) v.findViewById(R.id.like);

    }

    public TextView getMerchant_review() {
        return Merchant_review;
    }

    public void setMerchant_review(TextView merchant_review) {
        Merchant_review = merchant_review;
    }

    public TextView getMerchant_title() {
        return Merchant_title;
    }

    public void setMerchant_title(TextView merchant_title) {
        Merchant_title = merchant_title;
    }

    public TextView getMerchant_rating() {
        return Merchant_rating;
    }

    public void setMerchant_rating(TextView merchant_rating) {
        Merchant_rating = merchant_rating;
    }

    public TextView getMerchant_Area() {
        return Merchant_Area;
    }

    public void setMerchant_Area(TextView merchant_Area) {
        Merchant_Area = merchant_Area;
    }

    TextView Merchant_review;
    TextView Merchant_title;

    public ImageView getLike() {
        return like;
    }

    public void setLike(ImageView like) {
        this.like = like;
    }




    public ImageView getImageView() {
        return ivExample;
    }

    public void setImageView(ImageView ivExample) {
        this.ivExample = ivExample;
    }
}