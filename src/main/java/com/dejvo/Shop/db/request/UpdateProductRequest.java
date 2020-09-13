package com.dejvo.Shop.db.request;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.math.BigDecimal;


public class UpdateProductRequest {
    @NonNull
    private String name;
    @NonNull
    private String info;
    @NonNull
    @Column(name = "value",precision = 8,scale = 2)
    private BigDecimal value;
    @NonNull
    private int count;
    @NonNull
    private int id;
    @NonNull
    private String category;

    public UpdateProductRequest() {
    }

    public UpdateProductRequest(@NonNull String name, @NonNull String info, BigDecimal value, int count) {
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
    }

    public UpdateProductRequest(@NonNull String name, @NonNull String info, @NonNull BigDecimal value, int count, int id, @NonNull String category) {
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
        this.id = id;
        this.category = category;
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

    @NonNull
    public String getCategory() {
        return category;
    }
}
