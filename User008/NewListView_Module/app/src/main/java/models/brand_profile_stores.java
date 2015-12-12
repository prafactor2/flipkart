package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class brand_profile_stores {
    private Integer success;
    private String message;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Stores> getResults() {
        return results;
    }

    public void setResults(List<Stores> results) {
        this.results = results;
    }

    private Integer size;
    private List<Stores> results = new ArrayList<Stores>();;

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

    public class Stores {
        private String jeb_no;

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getShop_type() {
            return shop_type;
        }

        public void setShop_type(String shop_type) {
            this.shop_type = shop_type;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public Merch_Address getAddress() {
            return address;
        }

        public void setAddress(Merch_Address address) {
            this.address = address;
        }

        public String getJeb_no() {
            return jeb_no;
        }

        public void setJeb_no(String jeb_no) {
            this.jeb_no = jeb_no;
        }

        private String brand_name;
        private Merch_Address address;
        private double distance;
        private String shop_type;
    }

    public class Merch_Address {
        private String street;
        private String area1;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getArea1() {
            return area1;
        }

        public void setArea1(String area1) {
            this.area1 = area1;
        }
    }

}
