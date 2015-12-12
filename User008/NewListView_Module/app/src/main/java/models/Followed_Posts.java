package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Followed_Posts {
    private Integer success;
    private String message;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    private Integer size;


    private Integer total_posts;
    private List<Posts> posts = new ArrayList<Posts>();


    public Integer getTotal_posts() {
        return total_posts;
    }

    public void setTotal_posts(Integer total_posts) {
        this.total_posts = total_posts;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public class Posts {
        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        private String _id;
        private String image_url;
        private String category;
        private String brand_name;
        private String icon;
        private String description;
        private String upvoted;
        private String title;
        private String terms_and_conditions;
        private String time_posted;
        private Integer up_vote;
        private String expir_date;
        private String offer_code;
        private String arrival_time;

        public String getUpvoted() {
            return upvoted;
        }

        public void setUpvoted(String upvoted) {
            this.upvoted = upvoted;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTerms_and_conditions() {
            return terms_and_conditions;
        }

        public void setTerms_and_conditions(String terms_and_conditions) {
            this.terms_and_conditions = terms_and_conditions;
        }

        public String getTime_posted() {
            return time_posted;
        }

        public void setTime_posted(String time_posted) {
            this.time_posted = time_posted;
        }

        public Integer getUp_vote() {
            return up_vote;
        }

        public void setUp_vote(Integer up_vote) {
            this.up_vote = up_vote;
        }

        public String getExpir_date() {
            return expir_date;
        }

        public void setExpir_date(String expir_date) {
            this.expir_date = expir_date;
        }

        public String getOffer_code() {
            return offer_code;
        }

        public void setOffer_code(String offer_code) {
            this.offer_code = offer_code;
        }

        public String getArrival_time() {
            return arrival_time;
        }

        public void setArrival_time(String arrival_time) {
            this.arrival_time = arrival_time;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String  getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


