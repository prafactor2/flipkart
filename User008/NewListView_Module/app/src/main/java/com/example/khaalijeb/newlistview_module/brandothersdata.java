package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 23/10/2015.
 */
public class brandothersdata {

    String followed;
    String post_id;
    String Title;
    String brandname;
    String likes;
    String brandicon;
    String brandphoto;
    String upvoted;

    public brandothersdata(String post_id, String followed, String upvoted, String title, String brandname, String likes, String brandicon, String brandphoto) {

        this.upvoted = upvoted;
        this.followed = followed;
        this.post_id = post_id;
        this.Title = title;
        this.brandname = brandname;
        this.likes = likes;
        this.brandicon = brandicon;
        this.brandphoto = brandphoto;


    }
}
