package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.repository.SellerRepository;
import com.dejvo.Shop.model.Seller;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Deprecated
class SellerImplementationTest {

    SellerRepository sellerRepository;
    @Autowired
    SellerImplementation sellerImplementation = new SellerImplementation(sellerRepository);

    @Test
    void readSellerById2AndCompareName() {
        assertEquals("Ertr", sellerImplementation.readSellerById(1L).getName());
    }

    @Test
    void readSellerById4AndCompareName() {
        assertEquals("Erdo", sellerImplementation.readSellerById(4L).getName());
    }

    @Test
    void readAllSellersAndCompareSize() {
        assertEquals(3, sellerImplementation.readAllSellers().size());
    }

    @Test
    public void createSeller() {
        Seller seller = new Seller();
        seller.setName("New seller");
        seller.setEmail("sellerko@grag.sk");
        seller.setAddress("Aperkasdfad 54");
        sellerImplementation.createSeller(seller);
    }

//    @Test
//    public void updateSellerWithId2(){
//        Seller seller = new Seller();
//        seller.setId(5L);
//        seller.setName("Apok");
//        seller.setEmail("kol@grag.sk");
//        seller.setAddress("kikolo 54");
//
//        sellerImplementation.updateSeller(req,3L);
//    }
    @Test
    public void deleteCustomerWithId2(){
        sellerImplementation.deleteSeller(5L);
    }


}