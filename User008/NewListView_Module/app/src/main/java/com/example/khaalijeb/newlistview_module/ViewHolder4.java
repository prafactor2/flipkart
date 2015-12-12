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


public class ViewHolder4 extends RecyclerView.ViewHolder {


    public TextView getLabel3() {
        return label3;
    }

    public void setLabel3(TextView label3) {
        this.label3 = label3;
    }

    private TextView label3;
    private ImageView image;

    public ImageButton getImage3() {
        return image3;
    }

    public void setImage3(ImageButton image3) {
        this.image3 = image3;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage2() {
        return image2;
    }

    public void setImage2(ImageView image2) {
        this.image2 = image2;
    }

    private ImageView image2;
    private ImageButton image3;

    public ViewHolder4(View v) {
        super(v);
        label3 = (TextView) v.findViewById(R.id.text1);
        image = (ImageView) v.findViewById(R.id.image);
        image2 = (ImageView) v.findViewById(R.id.image2);
        image3 = (ImageButton) v.findViewById(R.id.image3);
    }


}