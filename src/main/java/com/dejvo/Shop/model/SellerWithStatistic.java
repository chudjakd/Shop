package com.dejvo.Shop.model;

import org.springframework.lang.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class SellerWithStatistic {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String address;
    @NonNull
    private int countoftipsofsellproduct;
    @NonNull
    private int countofsellproducts;

    public SellerWithStatistic(int id, @NonNull String name, @NonNull String email, @NonNull String address, int countoftipsofsellproduct, int countofsellproducts) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.countoftipsofsellproduct = countoftipsofsellproduct;
        this.countofsellproducts = countofsellproducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public int getCountoftipsofsellproduct() {
        return countoftipsofsellproduct;
    }

    public void setCountoftipsofsellproduct(int countoftipsofsellproduct) {
        this.countoftipsofsellproduct = countoftipsofsellproduct;
    }

    public int getCountofsellproducts() {
        return countofsellproducts;
    }

    public void setCountofsellproducts(int countofsellproducts) {
        this.countofsellproducts = countofsellproducts;
    }
}
