package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.*;
import com.dejvo.Shop.db.request.BuyProductByCardRequest;
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
        Product product= new Product(1,"Popici taska","Cierna taska je vzdy dobra ma aj zips", BigDecimal.valueOf(9.99),60, Timestamp.from(Instant.now()),"Toys");
        Product product1= new Product(2,"Tuska","Farebna tuska na vsetko",BigDecimal.valueOf(1.05),10, Timestamp.from(Instant.now()),"Toys");

        Assert.assertNotNull(customerInterface.createCustomer(customer));
        Assert.assertNotNull(customerInterface.createCustomer(customer1));
        Assert.assertNotNull(sellerInterface.createSeller(seller));
        Assert.assertNotNull(sellerInterface.createSeller(seller1));
        Assert.assertNotNull(productInterface.createProduct(product));
        Assert.assertNotNull(productInterface.createProduct(product1));

        // TODO: 14. 9. 2020 Davaj pozor ked sa vytvori hore customer tak po novom sa vytvara aj customer account takze dole pod tymto vymenit to namiesto update customer accout
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

    @Test
    public void testovanieShoppingByCard(){
        String cardnumber="4405 7789 6739 6700";
        String datetime="04/22";
        String safenumber="272";
        int countofbuyingproduct=5;

        //Otestovanie zleho cardnumber
        String cardnumber2="4405 7789 67396700";
        String datetime2="04/22";
        String safenumber2="272";

        BuyProductByCardRequest buyProductByCardReques2= new BuyProductByCardRequest(1,1,cardnumber2,datetime2,safenumber2,countofbuyingproduct);

        assertFalse(shoppingInterface.buyProductByCard(buyProductByCardReques2).isSuccess());

        //Otestovanie zleho datecard
        String cardnumber3="4405 7789 6739 6700";
        String datetime3="04 22";
        String safenumber3="272";

        BuyProductByCardRequest buyProductByCardReques3= new BuyProductByCardRequest(1,1,cardnumber3,datetime3,safenumber3,countofbuyingproduct);

        assertFalse(shoppingInterface.buyProductByCard(buyProductByCardReques3).isSuccess());

        //Otestovanie zleho safenumber
        String cardnumber4="4405 7789 6739 6700";
        String datetime4="04/22";
        String safenumber4="272 ";

        BuyProductByCardRequest buyProductByCardRequest4= new BuyProductByCardRequest(1,1,cardnumber4,datetime4,safenumber4,countofbuyingproduct);

        assertFalse(shoppingInterface.buyProductByCard(buyProductByCardRequest4).isSuccess());

        //Otestovanie zleho id productu

        BuyProductByCardRequest buyProductByCardRequest5= new BuyProductByCardRequest(5,1,cardnumber,datetime,safenumber,countofbuyingproduct);
        BuyProductResponse response5=shoppingInterface.buyProductByCard(buyProductByCardRequest5);
        assertFalse(response5.isSuccess());
        System.out.println("Ocakavame ze id productu neexistuje: "+response5.getErrormessage());

        //Otestovanie neexistujuceho customera

        BuyProductByCardRequest buyProductByCardRequest6= new BuyProductByCardRequest(1,5,cardnumber,datetime,safenumber,countofbuyingproduct);
        BuyProductResponse response6=shoppingInterface.buyProductByCard(buyProductByCardRequest6);
        assertFalse(response6.isSuccess());
        System.out.println("Ocakavame ze customer neexistuje: "+response6.getErrormessage());

        // Otestovanie nedostatocneho poctu produktov
        BuyProductByCardRequest buyProductByCardRequest7= new BuyProductByCardRequest(2,1,cardnumber,datetime,safenumber,20);
        BuyProductResponse response7=shoppingInterface.buyProductByCard(buyProductByCardRequest7);
        assertFalse(response7.isSuccess());
        System.out.println("Ocakavame ze nie je dostatok produktov: "+response7.getErrormessage());

        //Otestovanie spravneho nakupu
        BuyProductByCardRequest buyProductByCardRequest= new BuyProductByCardRequest(1,1,cardnumber,datetime,safenumber,countofbuyingproduct);
        BuyProductResponse buyProductResponse8=shoppingInterface.buyProductByCard(buyProductByCardRequest);
        assertTrue(buyProductResponse8.isSuccess());
        System.out.println("Ocakavame message spravny nakup: "+buyProductResponse8.getErrormessage());

        //Otestovanie spravneho nakupu
        BuyProductByCardRequest buyProductByCardRequest8= new BuyProductByCardRequest(1,1,cardnumber,datetime,safenumber,2);
        BuyProductResponse buyProductResponse9=shoppingInterface.buyProductByCard(buyProductByCardRequest8);
        assertTrue(buyProductResponse9.isSuccess());
        System.out.println("Ocakavame message pripocitania: "+buyProductResponse9.getErrormessage());



    }
}