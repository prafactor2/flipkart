package models;

/**
 * Created by Davinci on 5/27/2015.
 */
public class Login {
    private Integer success;
    private String message;
    private String email_id;
    private String mobile_no;
    private Integer tot_not;
    private Integer tot_follow_not;

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

    public String  getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String  getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public Integer  getTot_not() {
        return tot_not;
    }

    public void setTot_not(Integer tot_not) {
        this.tot_not = tot_not;
    }

    public Integer  getTot_follow_not() {
        return tot_follow_not;
    }

    public void setTot_follow_not(Integer tot_follow_not) {
        this.tot_follow_not = tot_follow_not;
    }
}
