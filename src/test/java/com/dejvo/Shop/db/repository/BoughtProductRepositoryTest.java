package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.model.BoughtProduct;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import com.dejvo.Shop.model.SellerWithStatistic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class BoughtProductRepositoryTest {

    @Autowired
    BoughtProductRepository boughtProductRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;

    @Test
    public void testovanieBoughtProductu(){
        Seller seller= new Seller("Selerko","seleris@seler.sk","Selerova 96");
        Seller seller2= new Seller("Biac","biac@seler.sk","Biacova 96");
        sellerRepository.createSeller(seller);
        sellerRepository.createSeller(seller2);
        Product product= new Product(1,"Lepidlo","Biele lepidlo lacne", BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()),"Toys");
        Product product2= new Product(2,"Tuska","Biele tuska",BigDecimal.valueOf(0.33),5, Timestamp.from(Instant.now()),"Toys");
        BoughtProduct boughtProduct= new BoughtProduct(1,1,10, Timestamp.from(Instant.now()));

        assertNotNull(productRepository.createProduct(product));
        assertNotNull(productRepository.createProduct(product2));

        assertEquals(1,boughtProductRepository.oldCreateBoughtProduct(boughtProduct));
        assertEquals(1,boughtProductRepository.getAllProductByCustomerId(1).size());

        assertNotNull(boughtProductRepository.selectBoughtProductByCustomerIdAndProductId(1,1));

        BoughtProduct boughtProduct1= new BoughtProduct(1,2,5, Timestamp.from(Instant.now()));
        boughtProductRepository.createBoughtProduct(boughtProduct1);

        BoughtProduct boughtProduct2= new BoughtProduct(2,2,5, Timestamp.from(Instant.now()));
        boughtProductRepository.createBoughtProduct(boughtProduct2);

        assertEquals(2,boughtProductRepository.getAllProductByProductId(2).size());

        assertEquals(2,boughtProductRepository.getAllProductByCustomerId(1).size());


        //Testovanie most popular sellera
        List<SellerWithStatistic> allsellerswithstatic=sellerRepository.getMostPopularSeller();

        assertEquals(2,allsellerswithstatic.get(1).getId());



    }
}