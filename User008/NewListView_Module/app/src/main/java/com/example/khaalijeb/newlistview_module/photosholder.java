package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 06/10/2015.
 */

import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class photosholder extends RecyclerView.ViewHolder {



    private ImageView imagex;


    public photosholder(View v) {
        super(v);

        imagex = (ImageView) v.findViewById(R.id.imagex);

    }
    public ImageView getImagex() {
        return imagex;
    }

    public void setImage1(ImageView imagex) {
        this.imagex = imagex;
    }



}