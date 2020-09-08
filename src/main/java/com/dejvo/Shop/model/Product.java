package com.dejvo.Shop.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Product {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @NonNull
    @Column(name = "seller_id")
    public long sellerId;
    @NonNull
    public String name;
    @NonNull
    public String info;
    @NonNull
    public double value;
    @NonNull
    public int count;
    @NonNull
    public Timestamp datetime;

    public Product() {
    }

    public Product( long sellerId, @NonNull String name, @NonNull String info, double value, int count, @NonNull Timestamp datetime) {
        this.sellerId = sellerId;
        this.name = name;
        this.info = info;
        this.value = value;
        this.count = count;
        this.datetime = datetime;
    }

    @NonNull
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(@NonNull Timestamp datetime) {
        this.datetime = datetime;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
