package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class merchant_profile_stores {
    private Integer success;
    private String message;
    private Merchants merchant;
    private List<Reviews> reviews = new ArrayList<Reviews>();
    private Integer total_photos;
    private Merch_Photos photos;

    public Integer getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(Integer total_photos) {
        this.total_photos = total_photos;
    }


    public Merch_Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Merch_Photos photos) {
        this.photos = photos;
    }

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

    public class Merch_Photos {
        private String photo_1;
        private String photo_2;
        public String getPhoto_1() {
            return photo_1;
        }

        public void setPhoto_1(String photo_1) {
            this.photo_1 = photo_1;
        }

        public String getPhoto_2() {
            return photo_2;
        }

        public void setPhoto_2(String photo_2) {
            this.photo_2 = photo_2;
        }
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

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public Merch_Address getAddress() {
            return address;
        }

        public void setAddress(Merch_Address address) {
            this.address = address;
        }

        private double rating;
        private Merch_Address address;
        private Integer total_reviews;
        private Integer total_ratings;

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        private String shop_name;
        private Merchants_location location = new Merchants_location();

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

        public String getArea2() {
            return area2;
        }

        public void setArea2(String area2) {
            this.area2 = area2;
        }

        private String city;
        private String area2;
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
