package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.LoginRequest;
import com.dejvo.Shop.db.response.LoginResponse;

public interface LoginInterface {

    public LoginResponse trylogin(LoginRequest loginRequest);


}
