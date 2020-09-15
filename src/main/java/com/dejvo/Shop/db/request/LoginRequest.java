package com.dejvo.Shop.db.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class LoginRequest {
    @NonNull
    private String name;
    @NonNull
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }
}
