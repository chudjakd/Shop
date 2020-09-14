package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;

public class BuyProductByCardRequest {

    @NonNull
    private int productid;
    @NonNull
    private int customerid;
    @NonNull
    private String cardnumber;
    @NonNull
    private String datecard;
    @NonNull
    private String safecode;
    @NonNull
    private int countofbuyingproduct;

    public BuyProductByCardRequest() {
    }

    public BuyProductByCardRequest(int productid, int customerid, @NonNull String cardnumber, @NonNull String datecard, @NonNull String safecode, int countofbuyingproduct) {
        this.productid = productid;
        this.customerid = customerid;
        this.cardnumber = cardnumber;
        this.datecard = datecard;
        this.safecode = safecode;
        this.countofbuyingproduct = countofbuyingproduct;
    }

    @NonNull
    public String getSafecode() {
        return safecode;
    }

    public int getProductid() {
        return productid;
    }

    public int getCustomerid() {
        return customerid;
    }

    @NonNull
    public String getCardnumber() {
        return cardnumber;
    }

    @NonNull
    public String getDatecard() {
        return datecard;
    }

    public int getCountofbuyingproduct() {
        return countofbuyingproduct;
    }
}
