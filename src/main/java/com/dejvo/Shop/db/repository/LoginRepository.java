package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.LoginMapper;
import com.dejvo.Shop.db.request.LoginRequest;
import com.dejvo.Shop.db.response.LoginResponse;
import com.dejvo.Shop.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoginRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    LoginMapper loginMapper;

    public LoginResponse trylogin(LoginRequest loginRequest){
        String url="SELECT * FROM LOGIN WHERE name="+"'"+loginRequest.getName()+"'";
        try {
            Login login=jdbcTemplate.queryForObject(url,loginMapper);
            if(login!=null){
                if(login.getPassword().equals(loginRequest.getPassword())){
                    return new LoginResponse(true,"Login uspesny");
                }else{
                    return new LoginResponse(false,"Nespravne heslo");
                }
            }else {
                return new LoginResponse(false,"Meno nenajdene skuste si ho skontrolovat");
            }
        }catch (Exception e){
            return new LoginResponse(false,"Skuste skontrolovat udaje");
        }
    }

    public Integer createlogin(LoginRequest loginRequest){
        String url="INSERT INTO LOGIN (name,password) VALUES(?,?)";
        try {
            jdbcTemplate.update(url,loginRequest.getName(),loginRequest.getPassword());
            return 1;
        }catch (Exception e){
            return null;
        }
    }
}

