package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Seller;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SellerImplementationTest {
    JdbcTemplate jdbcTemplate;
    @Autowired
    SellerImplementation sellerImplementation = new SellerImplementation(jdbcTemplate);

    @Test
    void readSellerById() {
        assertEquals(1, sellerImplementation.readSellerById(2L).size());
    }

    @Test
    void readSellerById2AndCompareName() {
        assertEquals("Ertr", sellerImplementation.readSellerById(2L).get(0).getName());
    }
    @Test
    void readSellerById4AndCompareName() {
        assertEquals("Erdo", sellerImplementation.readSellerById(4L).get(0).getName());
    }

    @Test
    void readAllSellersAndCompareSize() {
        assertEquals(4, sellerImplementation.readAllSellers().size());
    }

    @Test
    public void createSeller() {
        Seller seller = new Seller();
        seller.setName("Erdo");
        seller.setEmail("erdo@grag.sk");
        seller.setAddress("Amperko 54");
        sellerImplementation.createSeller(seller);
    }

    @Test
    public void updateSellerWithId2(){
        Seller seller = new Seller();
        seller.setId(5L);
        seller.setName("Apok");
        seller.setEmail("kol@grag.sk");
        seller.setAddress("kikolo 54");

        sellerImplementation.updateSeller(seller,3L);
    }
    @Test
    public void deleteCustomerWithId2(){
        sellerImplementation.deleteSeller(2L);
    }


}