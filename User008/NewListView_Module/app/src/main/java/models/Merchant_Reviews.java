package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Merchant_Reviews {
    private Integer success;
    private String message;
    private List<Merchants> reviews = new ArrayList<Merchants>();

    public class Merchants {
        private String review;
        private double rating;
        private String username;

        public String getReview() {
            return review;
        }

        public void setReview(String review) {
            this.review = review;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
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

    public List<Merchants> getReviews() {
        return reviews;
    }

    public void setReviews(List<Merchants> reviews) {
        this.reviews = reviews;
    }


}


