package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 04/10/2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ViewHolder6 extends RecyclerView.ViewHolder {

    private TextView name, review, rating;
    Button button;

    public ViewHolder6(View v) {
        super(v);
        name = (TextView) v.findViewById(R.id.text1);
        review = (TextView) v.findViewById(R.id.text2);
        rating = (TextView) v.findViewById(R.id.rating);
        button = (Button)v.findViewById(R.id.reviewbutton);
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }




    public TextView getRating() {
        return rating;
    }

    public void setRating(TextView rating) {
        this.rating = rating;
    }


    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getReview() {
        return review;
    }

    public void setReview(TextView review) {
        this.review = review;
    }




}
