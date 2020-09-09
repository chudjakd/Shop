package com.dejvo.Shop.rest;

import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Seller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class RestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper= new ObjectMapper();

    @Test
    public void testCustomera() throws Exception{

        Customer customer= new Customer("Jozko","picko@gmail.com","Alopova 54");

            //Add customer
            String id= mockMvc.perform(post("/api/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isCreated())
            .andReturn().getResponse().getContentAsString();

            customer.setId(objectMapper.readValue(id,Long.class));

            //Get customer with id
            String customerJson=mockMvc.perform(get("/api/customer/"+customer.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

            Customer customerzdb= objectMapper.readValue(customerJson,Customer.class);

        Assert.assertEquals(customer,customerzdb);

        //Get all customers
        String customersJson=mockMvc.perform(get("/api/customers")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();

        List<Customer> listcustomers=objectMapper.readValue(customersJson, new TypeReference<List<Customer>>(){});
        Assert.assertEquals(1,listcustomers.size());
        Assert.assertEquals(customer,listcustomers.get(0));

        //Delete Customer
        String deleteidcustomer="1";
        mockMvc.perform(delete("/api/customer/"+deleteidcustomer)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
        //Delete non existing customer
        String deleteidcustomerwhichdoesntexist="5";
        mockMvc.perform(delete("/api/customer/"+deleteidcustomerwhichdoesntexist)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

    }

    @Test
    public void testSellera() throws Exception {

        Seller seller= new Seller("Selerko","seleris@seler.sk","Selerova 96");
                //Create seller
                String sellerid=mockMvc.perform(post("/api/seller")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seller)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

                seller.setId(objectMapper.readValue(sellerid,Long.class));

                //Get seller by id
                String sellerjson=mockMvc.perform(get("/api/seller/"+seller.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

                Seller sellerfromdb= objectMapper.readValue(sellerjson,Seller.class);
                Assert.assertEquals(sellerfromdb,seller);

                //Get Seller with id that doesnt exist
                String sellerjsonwhichdoesntexist=mockMvc.perform(get("/api/seller/15")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();


                //Get all sellers
                String jsonlistofsellers=
                        mockMvc.perform(get("/api/sellers")
                               .contentType(MediaType.APPLICATION_JSON))
                               .andExpect(status().isOk())
                               .andReturn().getResponse().getContentAsString();
                List<Seller> listSellers=objectMapper.readValue(jsonlistofsellers, new TypeReference<List<Seller>>() {
                });

                Assert.assertEquals(seller,listSellers.get(0));
                Assert.assertEquals(1,listSellers.size());

                //Delete seller
                mockMvc.perform(delete("/api/seller/"+seller.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

                //Delete seller with id that doesnt exist
                mockMvc.perform(delete("/api/seller/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }


}
