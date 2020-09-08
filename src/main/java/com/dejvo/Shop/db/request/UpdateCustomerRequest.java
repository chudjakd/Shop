package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UpdateCustomerRequest {
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String email;


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
