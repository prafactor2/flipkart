package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Uber_Price {

    public List<Cars> getPrices() {
        return prices;
    }

    public void setPrices(List<Cars> prices) {
        this.prices = prices;
    }

    private List<Cars> prices  = new ArrayList<Cars>();


    public class Cars {
        private String product_id;

        public String getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getEstimate() {
            return estimate;
        }

        public void setEstimate(String estimate) {
            this.estimate = estimate;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        private String display_name;
        private String estimate;
        private int duration;
    }

}
