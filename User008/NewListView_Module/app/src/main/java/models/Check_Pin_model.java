package models;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Check_Pin_model {
    private Integer success;
    private String message;
    private Integer pin;

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

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}
