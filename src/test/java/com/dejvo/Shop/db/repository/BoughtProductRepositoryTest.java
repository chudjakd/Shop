package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.model.BoughtProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class BoughtProductRepositoryTest {

    @Autowired
    BoughtProductRepository boughtProductRepository;

    @Test
    public void testovanieBoughtProductu(){
        BoughtProduct boughtProduct= new BoughtProduct(1,1,10, Timestamp.from(Instant.now()));

        assertEquals(1,boughtProductRepository.oldCreateBoughtProduct(boughtProduct));
        assertEquals(1,boughtProductRepository.getAllProductByCustomerId(1).size());

        assertNotNull(boughtProductRepository.selectBoughtProductByCustomerIdAndProductId(1,1));

        BoughtProduct boughtProduct1= new BoughtProduct(1,2,5, Timestamp.from(Instant.now()));
        boughtProductRepository.createBoughtProduct(boughtProduct1);

        BoughtProduct boughtProduct2= new BoughtProduct(2,2,5, Timestamp.from(Instant.now()));
        boughtProductRepository.createBoughtProduct(boughtProduct2);

        assertEquals(2,boughtProductRepository.getAllProductByProductId(2).size());

        assertEquals(2,boughtProductRepository.getAllProductByCustomerId(1).size());


    }
}