package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.*;
import com.dejvo.Shop.db.request.BuyProductRequest;
import com.dejvo.Shop.db.response.BuyProductResponse;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
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
    @Autowired
    CustomerAccountInterface customerAccountInterface;
    @Autowired
    ShoppingInterface shoppingInterface;

    @Before
    public void createCustomerAProductov(){

        Customer customer= new Customer("Janko Customer","customerko@gmail","customerova 25");
        Customer customer1= new Customer("Erzo ","erzotko@gmail","erzebet 25");
        Seller seller=new Seller("Sellerko","Selleris@seller.sk","sellerova 96");
        Seller seller1=new Seller("Ojebavac","Ojebavaci@seller.sk","Ojebavacova 14");
        Product product= new Product(1,"Popici taska","Cierna taska je vzdy dobra ma aj zips", BigDecimal.valueOf(9.99),60, Timestamp.from(Instant.now()));
        Product product1= new Product(2,"Tuska","Farebna tuska na vsetko",BigDecimal.valueOf(1.05),10, Timestamp.from(Instant.now()));

        Assert.assertNotNull(customerInterface.createCustomer(customer));
        Assert.assertNotNull(customerInterface.createCustomer(customer1));
        Assert.assertNotNull(sellerInterface.createSeller(seller));
        Assert.assertNotNull(sellerInterface.createSeller(seller1));
        Assert.assertNotNull(productInterface.createProduct(product));
        Assert.assertNotNull(productInterface.createProduct(product1));

        Long customerid=customerInterface.readAllCustomers().get(0).getId();
        Long customerid2=customerInterface.readAllCustomers().get(1).getId();
        CustomerAccount customerAccount=new CustomerAccount(customerid.intValue(),BigDecimal.valueOf(523.63));
        CustomerAccount customerAccount1=new CustomerAccount(customerid2.intValue(),BigDecimal.valueOf(98.25));
        customerAccountInterface.createCustomerAccount(customerAccount);
        customerAccountInterface.createCustomerAccount(customerAccount1);

    }

    @Test
    public void testovanieShopping(){
        //Otestovanie prveho if
        //Neexistuje product
        BuyProductRequest buyProductRequest= new BuyProductRequest(5,1,20);
        BuyProductResponse response1 =shoppingInterface.buyProduct(buyProductRequest);
        assertFalse(response1.isSuccess());
        System.out.println("Response1: "+response1.getErrormessage());
        //Neexistuje customer
        BuyProductRequest buyProductRequest2= new BuyProductRequest(1,5,20);
        BuyProductResponse response2 =shoppingInterface.buyProduct(buyProductRequest2);
        assertFalse(response2.isSuccess());
        System.out.println("Response2: "+response2.getErrormessage());
        //Neexistuje ani product ani customer
        BuyProductRequest buyProductRequest3= new BuyProductRequest(5,5,20);
        BuyProductResponse response3 =shoppingInterface.buyProduct(buyProductRequest3);
        assertFalse(response3.isSuccess());
        System.out.println("Response3: "+response3.getErrormessage());

        //Otestovanie druheho ifu
        //Customer nema dostatok penazi kedze chce kupit 20 produktov po 10 to je 200 a ma len 98
        BuyProductRequest buyProductRequest4= new BuyProductRequest(1,2,20);
        BuyProductResponse response4 =shoppingInterface.buyProduct(buyProductRequest4);
        assertFalse(response4.isSuccess());
        System.out.println("Response4: "+response4.getErrormessage());

        //Otestovanie tretieho ifu
        //Nie je dostatocny pocet produktov na sklade
        BuyProductRequest buyProductRequest5= new BuyProductRequest(2,1,20);
        BuyProductResponse response5 =shoppingInterface.buyProduct(buyProductRequest5);
        assertFalse(response5.isSuccess());
        System.out.println("Response5: "+response5.getErrormessage());

        //Otestovanie stvrteho ifu a spravnej funkcnosti
        BuyProductRequest buyProductRequest6= new BuyProductRequest(2,1,5);
        BuyProductResponse response6 =shoppingInterface.buyProduct(buyProductRequest6);
        assertTrue(response6.isSuccess());
        System.out.println("Response6 ocakavame uspesny nakup: "+response6.getErrormessage());
        //Customer 1 kupi produkt ktory uz ma ocakavame inu spravu
        BuyProductRequest buyProductRequest7= new BuyProductRequest(2,1,5);
        BuyProductResponse response7 =shoppingInterface.buyProduct(buyProductRequest7);
        assertTrue(response7.isSuccess());
        System.out.println("Response7 ocakavame uspesny pripocitanie produktu: "+response7.getErrormessage());

        //Kontrola penazi customera a taktiez poctu produktov na sklade

        assertEquals(0,productInterface.readProductById(2).getCount());
        System.out.println("Ocakavame ze bude mat menej penazi ako 523.63 penize: "+customerAccountInterface.getCustomerByIdOfCustomer(1).getMoney());

        BuyProductRequest buyProductRequest8= new BuyProductRequest(2,1,5);
        BuyProductResponse response8 =shoppingInterface.buyProduct(buyProductRequest8);
        assertFalse(response8.isSuccess());
        System.out.println("Response8 ocakavame ze nie je dostatok produktov: "+response8.getErrormessage());


    }
}