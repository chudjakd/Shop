package com.dejvo.Shop.restapi;


import com.dejvo.Shop.db.crudservice.LoginInterface;
import com.dejvo.Shop.db.request.LoginRequest;
import com.dejvo.Shop.db.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestLogin {
    @Autowired
    LoginInterface loginInterface;

    @Profile("default")
    @PostMapping("/login")
    public ResponseEntity trylogin(@RequestBody LoginRequest loginRequest){
        LoginResponse response=loginInterface.trylogin(loginRequest);
        if(response.isSuccess()){
            return new ResponseEntity(response.getMessage(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response.getMessage(),HttpStatus.PRECONDITION_FAILED);
        }

    }
}
