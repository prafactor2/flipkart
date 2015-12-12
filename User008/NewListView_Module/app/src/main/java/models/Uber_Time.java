package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Uber_Time {
    public List<Cars> getTimes() {
        return times;
    }

    public void setTimes(List<Cars> times) {
        this.times = times;
    }

    private List<Cars> times  = new ArrayList<Cars>();


    public class Cars {
        private String product_id;

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public int getEstimate() {
            return estimate;
        }

        public void setEstimate(int estimate) {
            this.estimate = estimate;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        private String display_name;
        private int estimate;
    }

}
