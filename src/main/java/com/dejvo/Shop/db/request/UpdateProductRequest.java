package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;

public class UpdateProductRequest {
    @NonNull
    private String name;
    @NonNull
    private String info;
    @NonNull
    private int value;
    @NonNull
    private int count;

    public UpdateProductRequest(@NonNull String name, @NonNull String info, int value, int count) {
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
