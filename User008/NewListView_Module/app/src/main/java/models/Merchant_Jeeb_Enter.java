package models;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Merchant_Jeeb_Enter {
    private Integer success;
    private String message;
    private String merchant_name;
    private Float merchant_rating;

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

    public String  getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public Float  getMerchant_rating() {
        return merchant_rating;
    }

    public void setMerchant_rating(Float merchant_rating) {
        this.merchant_rating = merchant_rating;
    }
}
