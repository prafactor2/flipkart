package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Coupons {
    private Integer success;
    private String message;
    private Integer size;

    public List<All_Coupons> getAll_coupons() {
        return all_coupons;
    }

    public void setAll_coupons(List<All_Coupons> all_coupons) {
        this.all_coupons = all_coupons;
    }

    private List<All_Coupons> all_coupons = new ArrayList<All_Coupons>();

    public class All_Coupons {
        private String title;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getMerchant_icon() {
            return merchant_icon;
        }

        public void setMerchant_icon(String merchant_icon) {
            this.merchant_icon = merchant_icon;
        }

        private double amount;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        private String _id;
        private String merchant_name;
        private String merchant_icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}


