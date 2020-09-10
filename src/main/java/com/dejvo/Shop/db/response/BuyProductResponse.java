package com.dejvo.Shop.db.response;

public class BuyProductResponse {

    private boolean success;

    private String errormessage;

    public BuyProductResponse(boolean success) {
        this.success = success;
    }

    public BuyProductResponse(boolean success, String errormessage) {
        this.success = success;
        this.errormessage = errormessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrormessage() {
        return errormessage;
    }
}
