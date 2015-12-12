package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class location_search {
    private Integer success;
    private String message;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<Locations> getResults() {
        return results;
    }

    public void setResults(List<Locations> results) {
        this.results = results;
    }

    private Integer size;
    private List<Locations> results = new ArrayList<Locations>();;

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

    public class Locations {
        public String area1;
        public String area2;

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

        public String getArea1() {
            return area1;
        }

        public void setArea1(String area1) {
            this.area1 = area1;
        }

        public String city;
    }

}
