package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UpdateSellerRequest {

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String address;

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getAddress() {
        return address;
    }
}
