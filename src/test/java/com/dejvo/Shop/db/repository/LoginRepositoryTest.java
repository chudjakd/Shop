package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.request.LoginRequest;
import com.dejvo.Shop.db.response.LoginResponse;
import com.dejvo.Shop.model.Login;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginRepositoryTest {

    @Autowired
    LoginRepository loginRepository;

    @Test
    public void TestLogin(){
    LoginRequest login= new LoginRequest("admin","admin");
    LoginRequest login2= new LoginRequest("customer","customer");
    LoginRequest login3= new LoginRequest("seller","seller");

    assertEquals(1,loginRepository.createlogin(login));
    assertEquals(1,loginRepository.createlogin(login2));
    assertEquals(1,loginRepository.createlogin(login3));

    LoginRequest loginRequest= new LoginRequest("Admin","admin");
    LoginResponse loginResponse=loginRepository.trylogin(loginRequest);
    assertFalse(loginResponse.isSuccess());
    System.out.println("Ocakavame zle meno: "+loginResponse.getMessage());

    LoginRequest loginRequest2= new LoginRequest("admin","Admin");
    LoginResponse loginResponse2=loginRepository.trylogin(loginRequest2);
    assertFalse(loginResponse2.isSuccess());
    System.out.println("Ocakavame zle heslo: "+loginResponse2.getMessage());

    LoginRequest loginRequest3= new LoginRequest("admine","dmin");
    LoginResponse loginResponse3=loginRepository.trylogin(loginRequest3);
    assertFalse(loginResponse3.isSuccess());
    System.out.println("Ocakavame neexistujuce meno: "+loginResponse3.getMessage());

        LoginRequest loginRequest4= new LoginRequest("admin","admin");
        LoginResponse loginResponse4=loginRepository.trylogin(loginRequest4);
        assertTrue(loginResponse4.isSuccess());
        System.out.println("Ocakavame uspesny log: "+loginResponse4.getMessage());

    }


}