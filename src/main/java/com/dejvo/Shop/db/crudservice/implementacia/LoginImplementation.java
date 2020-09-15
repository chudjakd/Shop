package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.LoginInterface;
import com.dejvo.Shop.db.repository.LoginRepository;
import com.dejvo.Shop.db.request.LoginRequest;
import com.dejvo.Shop.db.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginImplementation implements LoginInterface {

    @Autowired
    LoginRepository loginRepository;

    @Override
    public LoginResponse trylogin(LoginRequest loginRequest) {
        return loginRepository.trylogin(loginRequest);
    }
}
