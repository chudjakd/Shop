package com.dejvo.Shop.db.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


public class UpdateProductRequest {
    @NonNull
    private String name;
    @NonNull
    private String info;
    @NonNull
    private BigDecimal value;
    @NonNull
    private int count;
    @NonNull
    private int id;

    public UpdateProductRequest(@NonNull String name, @NonNull String info, BigDecimal value, int count) {
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
    }

    public UpdateProductRequest(@NonNull String name, @NonNull String info, @NonNull BigDecimal value, int count, int id) {
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getInfo() {
        return info;
    }
    @NonNull
    public BigDecimal getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    public int getId() {
        return id;
    }
}
