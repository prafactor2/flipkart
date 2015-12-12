package com.example.khaalijeb.newlistview_module;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lord Voldemort on 23/10/2015.
 */
public class ViewHolderOthers extends RecyclerView.ViewHolder {

    ImageView brandicon;
    ImageView brandphoto;
    TextView brandname;
    TextView Title;
    TextView likes;
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

    public ViewHolderOthers(View itemView) {
        super(itemView);

        brandicon = (ImageView)itemView.findViewById(R.id.brandicon);
        brandphoto = (ImageView)itemView.findViewById(R.id.brandphoto);
        brandname = (TextView)itemView.findViewById(R.id.brandname);
        Title = (TextView)itemView.findViewById(R.id.branddesc);
        likes = (TextView)itemView.findViewById(R.id.likenum);
        tick = (ImageView)itemView.findViewById(R.id.tick);
        followbutton = (TextView)itemView.findViewById(R.id.followbutton);
        unlikebutton = (ImageView)itemView.findViewById(R.id.unlike);
        likebutton = (ImageView)itemView.findViewById(R.id.like);

    }
    public ImageView getBrandicon() {
        return brandicon;
    }

    public void setBrandicon(ImageView brandicon) {
        this.brandicon = brandicon;
    }

    public ImageView getBrandphoto() {
        return brandphoto;
    }

    public void setBrandphoto(ImageView brandphoto) {
        this.brandphoto = brandphoto;
    }

    public TextView getBrandname() {
        return brandname;
    }

    public void setBrandname(TextView brandname) {
        this.brandname = brandname;
    }

    public TextView getTitle() {
        return Title;
    }

    public void setTitle(TextView title) {
        Title = title;
    }

    public TextView getLikes() {
        return likes;
    }

    public void setLikes(TextView likes) {
        this.likes = likes;
    }


}
