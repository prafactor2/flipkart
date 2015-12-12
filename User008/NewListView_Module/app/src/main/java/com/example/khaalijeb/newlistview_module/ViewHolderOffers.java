package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 24/10/2015.
 */
public class ViewHolderOffers extends RecyclerView.ViewHolder {

    ImageView brandicon;
    ImageView offerphoto;
    TextView offercode;
    TextView date;
    TextView likes;
    TextView desc;
    TextView Title;
    TextView tnc;
    ImageView tick;
    TextView followbutton;
    ImageView unlikebutton;
    ImageView likebutton;

    public ImageView getTick() {
        return tick;
    }

    public void setTick(ImageView tick) {
        this.tick = tick;
    }

    public TextView getFollowbutton() {
        return followbutton;
    }

    public void setFollowbutton(TextView followbutton) {
        this.followbutton = followbutton;
    }

    public ImageView getUnlikebutton() {
        return unlikebutton;
    }

    public void setUnlikebutton(ImageView unlikebutton) {
        this.unlikebutton = unlikebutton;
    }

    public ImageView getLikebutton() {
        return likebutton;
    }

    public void setLikebutton(ImageView likebutton) {
        this.likebutton = likebutton;
    }

    public ViewHolderOffers(View itemView) {
        super(itemView);

        brandicon = (ImageView)itemView.findViewById(R.id.brandicon);
        offerphoto = (ImageView)itemView.findViewById(R.id.brandphoto);
        offercode = (TextView)itemView.findViewById(R.id.code);
        date = (TextView)itemView.findViewById(R.id.date);
        likes = (TextView)itemView.findViewById(R.id.likenum);
        desc = (TextView)itemView.findViewById(R.id.branddesc);
        Title = (TextView)itemView.findViewById(R.id.brandname);
        tnc = (TextView)itemView.findViewById(R.id.tnc);
        tick = (ImageView)itemView.findViewById(R.id.tick);
        followbutton = (TextView)itemView.findViewById(R.id.followbutton);
        unlikebutton = (ImageView)itemView.findViewById(R.id.unlike);
        likebutton = (ImageView)itemView.findViewById(R.id.like);
    }

    public TextView getTnc() {
        return tnc;
    }

    public void setTnc(TextView tnc) {
        this.tnc = tnc;
    }
    public ImageView getBrandicon() {
        return brandicon;
    }

    public void setBrandicon(ImageView brandicon) {
        this.brandicon = brandicon;
    }

    public TextView getTitle() {
        return Title;
    }

    public void setTitle(TextView title) {
        Title = title;
    }

    public TextView getDesc() {
        return desc;
    }

    public void setDesc(TextView desc) {
        this.desc = desc;
    }

    public TextView getLikes() {
        return likes;
    }

    public void setLikes(TextView likes) {
        this.likes = likes;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getOffercode() {
        return offercode;
    }

    public void setOffercode(TextView offercode) {
        this.offercode = offercode;
    }

    public ImageView getOfferphoto() {
        return offerphoto;
    }

    public void setOfferphoto(ImageView offerphoto) {
        this.offerphoto = offerphoto;
    }


}
