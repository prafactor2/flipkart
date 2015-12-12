package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class brands_search {
    private Integer success;
    private String message;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Brands> getResults() {
        return results;
    }

    public void setResults(List<Brands> results) {
        this.results = results;
    }

    public Integer getTotal_brands() {
        return total_brands;
    }

    public void setTotal_brands(Integer total_brands) {
        this.total_brands = total_brands;
    }

    private Integer size;
    private Integer total_brands;
    private List<Brands> results = new ArrayList<Brands>();;

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

    public class Brands {
        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        private String brand_id;
        private String brand_name;
    }

}
