package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class merchant_all_pricesheets {
    private Integer success;
    private String message;
    private List<Pricesheets> pricesheets  = new ArrayList<Pricesheets>();

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

    public class Pricesheets {
        private String pricesheet;

        public String getPricesheet() {
            return pricesheet;
        }

        public void setPricesheet(String pricesheet) {
            this.pricesheet = pricesheet;
        }
    }

    public List<Pricesheets> getPricesheets() {
        return pricesheets;
    }

    public void setPricesheets(List<Pricesheets> pricesheets) {
        this.pricesheets = pricesheets;
    }

}
