package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class merchant_profile {
    private Integer success;
    private String message;
    private Merchants merchant;
    private List<Reviews> reviews = new ArrayList<Reviews>();

    public List<Reviews> getReviews() {
        return reviews;
    }

    public void setReviews(List<Reviews> reviews) {
        this.reviews = reviews;
    }

    public Merchants getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchants merchant) {
        this.merchant = merchant;
    }

    public class Reviews {
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

    public class Merchants_location {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLon() {
            return lon;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

    }

    public class Merchants {
        private String cover;
        private Integer total_reviews;
        private Integer total_ratings;
        private Merchants_location location = new Merchants_location();

        public Merch_Address getAddress() {
            return address;
        }

        public void setAddress(Merch_Address address) {
            this.address = address;
        }

        private Merch_Address address;

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public Integer  getTotal_reviews() {
            return total_reviews;
        }

        public void setTotal_reviews(Integer total_reviews) {
            this.total_reviews = total_reviews;
        }

        public Integer getTotal_ratings() {
            return total_ratings;
        }

        public void setTotal_ratings(Integer total_ratings) {
            this.total_ratings = total_ratings;
        }

        public Merchants_location getLocation() {
            return location;
        }

        public void setLocation(Merchants_location location) {
            this.location = location;
        }

    }

    public class Merch_Address {
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        private String city;
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
