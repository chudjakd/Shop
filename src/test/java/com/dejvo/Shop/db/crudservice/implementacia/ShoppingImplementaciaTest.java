package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.CustomerInterface;
import com.dejvo.Shop.db.crudservice.ProductInterface;
import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import org.junit.Assert;
import org.junit.Before;
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
public class ShoppingImplementaciaTest {
    @Autowired
    ProductInterface productInterface;
    @Autowired
    CustomerInterface customerInterface;
    @Autowired
    SellerInterface sellerInterface;

    @Before
    public void createCustomerAProductov(){
        Customer customer= new Customer("Janko Customer","customerko@gmail","customerova 25");
        Customer customer1= new Customer("Erzo ","erzotko@gmail","erzebet 25");
        Seller seller=new Seller("Sellerko","Selleris@seller.sk","sellerova 96");
        Seller seller1=new Seller("Ojebavac","Ojebavaci@seller.sk","Ojebavacova 14");
        Product product= new Product(1L,"Popici taska","Cierna taska je vzdy dobra ma aj zips",9.99,4, Timestamp.from(Instant.now()));
        Product product1= new Product(2L,"Tuska","Farebna tuska na vsetko",1.05,10, Timestamp.from(Instant.now()));
        Assert.assertNotNull(customerInterface.createCustomer(customer));
        Assert.assertNotNull(customerInterface.createCustomer(customer1));
        Assert.assertNotNull(sellerInterface.createSeller(seller));
        Assert.assertNotNull(sellerInterface.createSeller(seller1));
        Assert.assertNotNull(productInterface.createProduct(product));
        Assert.assertNotNull(productInterface.createProduct(product1));

    }

    @Test
    public void testovanieShopping(){

    }
}