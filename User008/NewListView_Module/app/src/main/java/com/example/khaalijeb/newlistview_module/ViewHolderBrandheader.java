package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 25/10/2015.
 */
public class ViewHolderBrandheader extends RecyclerView.ViewHolder {



    ImageView brandcover;
    ImageView brandicon;
    TextView title;
    TextView rating;
    TextView review;
    Button follow;
    Button unfollow;

    public Button getFollow() {
        return follow;
    }

    public void setFollow(Button follow) {
        this.follow = follow;
    }

    public Button getUnfollow() {
        return unfollow;
    }

    public void setUnfollow(Button unfollow) {
        this.unfollow = unfollow;
    }

    public ViewHolderBrandheader(View itemView) {
        super(itemView);

        brandcover = (ImageView)itemView.findViewById(R.id.cover);
        brandicon = (ImageView)itemView.findViewById(R.id.logo);
        title = (TextView)itemView.findViewById(R.id.title);
        rating = (TextView)itemView.findViewById(R.id.rating);
        review = (TextView)itemView.findViewById(R.id.reviewnumber);
        follow = (Button)itemView.findViewById(R.id.follow);
        unfollow = (Button)itemView.findViewById(R.id.unfollow);

    }

    public TextView getReview() {
        return review;
    }

    public void setReview(TextView review) {
        this.review = review;
    }

    public TextView getRating() {
        return rating;
    }

    public void setRating(TextView rating) {
        this.rating = rating;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getBrandicon() {
        return brandicon;
    }

    public void setBrandicon(ImageView brandicon) {
        this.brandicon = brandicon;
    }

    public ImageView getBrandcover() {
        return brandcover;
    }

    public void setBrandcover(ImageView brandcover) {
        this.brandcover = brandcover;
    }


}
