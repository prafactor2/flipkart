package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Nearby_Search_All {
    private Integer success;
    private String message;
    private Integer size;
    private List<Merchants> results = new ArrayList<Merchants>();

    public class Merchants {
        private String jeb_no;
        private Double rating;
        private String shop_name;
        private String shop_type;
        private Double distance;
        private Integer total_photos;
        private Merch_Photos photos;

        private Merch_Address address;

        public String getJeb_no() {
            return jeb_no;
        }

        public void setJeb_no(String jeb_no) {
            this.jeb_no = jeb_no;
        }

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public Merch_Address getAddress() {
            return address;
        }

        public void setAddress(Merch_Address address) {
            this.address = address;
        }

        public Merch_Photos getPhotos() {
            return photos;
        }

        public void setPhotos(Merch_Photos photos) {
            this.photos = photos;
        }


        public Integer getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(Integer total_photos) {
            this.total_photos = total_photos;
        }

    }

    public class Merch_Address {
        private String street;
        private String area;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }
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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String  getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Merchants> getResults() {
        return results;
    }

    public void setResults(List<Merchants> results) {
        this.results = results;
    }
}