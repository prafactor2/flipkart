package com.example.khaalijeb.newlistview_module;

/**
 * Created by Lord Voldemort on 24/10/2015.
 */
public class brandoffersdata {

    String followed;
    String post_id;
    String Title;
    String date;
    String code;
    String desc;
    String likes;
    String terms;
    String Brandicon;
    String offerphoto;
    String upvoted;

    public brandoffersdata(String post_id, String followed, String upvoted, String title, String offerphoto, String brandicon, String likes, String desc, String code, String date, String terms) {

        this.upvoted = upvoted;
        this.followed = followed;
        this.post_id = post_id;
        this.Title = title;
        this.offerphoto = offerphoto;
        this.Brandicon = brandicon;
        this.likes = likes;
        this.desc = desc;
        this.code = code;
        this.date = date;
        this.terms = terms;
    }
}
