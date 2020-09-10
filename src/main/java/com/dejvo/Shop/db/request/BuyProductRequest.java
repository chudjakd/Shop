package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class BuyProductRequest {

    @NonNull
    private int productid;
    @NonNull
    private int customerid;
    @NonNull
    private int count;

    public BuyProductRequest(int productid, int customerid, int count) {
        this.productid = productid;
        this.customerid = customerid;
        this.count = count;
    }

    public int getProductid() {
        return productid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyProductRequest that = (BuyProductRequest) o;
        return productid == that.productid &&
                customerid == that.customerid &&
                count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productid, customerid, count);
    }
}
