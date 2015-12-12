package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Merchant_cover_lat_lon_tot_rat_rev {
    private Integer success;
    private String message;
    private String cover;
    private Integer total_reviews;
    private Integer total_ratings;
    private Merchants_location location = new Merchants_location();

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
