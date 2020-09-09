package com.dejvo.Shop.rest;

import com.dejvo.Shop.model.Customer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void createCustomer(){
        Customer customer= new Customer(1L,"Jozko","picko@gmail.com","Alopova 54");
    }

    @Test
    public void getCustomerById(){
        try {
            mockMvc.perform(post("/customer"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
