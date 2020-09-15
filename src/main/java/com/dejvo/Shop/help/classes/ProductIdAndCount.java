package com.dejvo.Shop.help.classes;

import org.springframework.lang.NonNull;

public class ProductIdAndCount {

    @NonNull
    private int productid;
    @NonNull
    private int count;

    public ProductIdAndCount(int productid, int count) {
        this.productid = productid;
        this.count = count;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
