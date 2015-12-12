package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davinci on 5/27/2015.
 */
public class All_Offers {
    private Integer success;
    private String message;
    private Integer size;
    private List<Brands> offers = new ArrayList<Brands>();

    public class Brands {
        private String image_url;
        private String type;
        private String title;
        private String merchant_name;
        private String icon_url;
        private String offer_code;

        public String getOffer_code() {
            return offer_code;
        }

        public void setOffer_code(String offer_code) {
            this.offer_code = offer_code;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
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

    public List<Brands> getOffers() {
        return offers;
    }

    public void setOffers(List<Brands> offers) {
        this.offers = offers;
    }


}


