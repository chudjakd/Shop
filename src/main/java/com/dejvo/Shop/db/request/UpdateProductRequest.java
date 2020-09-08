package com.dejvo.Shop.db.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;


public class UpdateProductRequest {
    @NonNull
    private String name;
    @NonNull
    private String info;
    @NonNull
    private int value;
    @NonNull
    private int count;

    public UpdateProductRequest(String name, String info, int value, int count) {
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getInfo() {
        return info;
    }

    public int getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }
}
