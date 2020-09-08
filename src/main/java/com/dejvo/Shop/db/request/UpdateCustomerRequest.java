package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;

public class UpdateCustomerRequest {
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String email;

    public UpdateCustomerRequest(@NonNull String name, @NonNull String address, @NonNull String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getEmail() {
        return email;
    }
}
