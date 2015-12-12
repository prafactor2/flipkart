package models;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Apply_Coupon {
    private Integer success;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    private Float off;

    public Float getCashback() {
        return cashback;
    }

    public void setCashback(Float cashback) {
        this.cashback = cashback;
    }

    private Float cashback;

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

    public Float  getOff() {
        return off;
    }

    public void setOff(Float off) {
        this.off = off;
    }
}
