package com.dejvo.Shop.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;

@Entity
public class Login {
    @NonNull
    private String name;
    @NonNull
    private String password;

    public Login() {
    }

    public Login(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }
}
