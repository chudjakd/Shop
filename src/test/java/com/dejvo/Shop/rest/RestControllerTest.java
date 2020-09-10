package com.dejvo.Shop.rest;

import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
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

import java.sql.Timestamp;
import java.time.Instant;
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

    private Seller seller;
    private int countofseller=0;

    @Before
    public void createSeller() throws Exception {

        if(seller==null){
            seller= new Seller("Selerko","seleris@seler.sk","Selerova 96");
            //Create seller
            String sellerid=mockMvc.perform(post("/api/seller")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(seller)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            seller.setId(objectMapper.readValue(sellerid,Long.class));

        }
    }

    @Test
    public void testProductu() throws Exception {
        // TODO: 9. 9. 2020 skurveny json zaokruluje double hodnoty skus zmenit value a dojebe sa to
        Product product= new Product(seller.getId()
                ,"Taska"
                ,"Cervena taska vysivana"
                ,1
                ,50
                , Timestamp.from(Instant.now()));

                //Create product
                String idofproduct=mockMvc.perform(post("/api/product")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(objectMapper.writeValueAsString(product)))
                                          .andExpect(status().isOk())
                                          .andReturn().getResponse().getContentAsString();

                product.setId(objectMapper.readValue(idofproduct,Long.class));

                //Get product by id
                String productjson=mockMvc.perform(get("/api/product/"+product.getId().toString())
                                          .contentType(MediaType.APPLICATION_JSON))
                                          .andExpect(status().isOk())
                                          .andReturn().getResponse().getContentAsString();
                Product productfromdb=objectMapper.readValue(productjson,Product.class);

                Assert.assertEquals(product,productfromdb);

                //Get all products
                String listJsonProducts=mockMvc.perform(get("/api/products")
                                               .contentType(MediaType.APPLICATION_JSON))
                                               .andExpect(status().isOk())
                                               .andReturn().getResponse().getContentAsString();
                List<Product> listOfProducts= objectMapper.readValue(listJsonProducts, new TypeReference<List<Product>>() {
                });

                Assert.assertEquals(product,listOfProducts.get(0));

                 //Update product
                double updateValue=product.getValue()+1;
                int updateCount=product.getCount()+10;
                UpdateProductRequest request= new UpdateProductRequest(product.getName(),product.getInfo(),updateValue,updateCount);
                mockMvc.perform(patch("/api/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

                String updatedproductjson=mockMvc.perform(get("/api/product/"+product.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                Product updatedproductfromdb=objectMapper.readValue(updatedproductjson,Product.class);

                Assert.assertNotEquals(product.getCount(),updatedproductfromdb.getCount());
                Assert.assertNotEquals(product.getValue(),updatedproductfromdb.getValue());


                //Delete product by id
                mockMvc.perform(delete("/api/product/"+product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status()
                        .isOk());

                String getDeletedProduct=mockMvc.perform(get("/api/product/"+product.getId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed())
                .andReturn().getResponse().getContentAsString();


    }

    @Test
    public void testCustomera() throws Exception{
        countofseller++;
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

        CustomerAccount customerAccount= new CustomerAccount(5,536.25);

                String keyfromcreatejson=mockMvc.perform(post("/api/customer/account")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(customerAccount)))
                                            .andExpect(status().isOk())
                                            .andReturn().getResponse().getContentAsString();
                Integer keyfromcreatecustomeraccout=objectMapper.readValue(keyfromcreatejson,Integer.class);

                Assert.assertEquals((Integer) 1 ,keyfromcreatecustomeraccout);


    }

    @Test
    public void testSellera() throws Exception {
        countofseller++;

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
                Long idicko=seller.getId()-1;
                Assert.assertEquals(seller,listSellers.get(idicko.intValue()));
                Assert.assertEquals(seller.getId().intValue(),listSellers.size());

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
