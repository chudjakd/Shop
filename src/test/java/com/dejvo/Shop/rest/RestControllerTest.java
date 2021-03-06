package com.dejvo.Shop.rest;

import com.dejvo.Shop.db.crudservice.BoughtProductInterface;
import com.dejvo.Shop.db.request.*;
import com.dejvo.Shop.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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

    @Autowired
    private BoughtProductInterface boughtProductInterface;

    private final ObjectMapper objectMapper= new ObjectMapper();

    private Seller seller;
    private Product product;

    @Before
    public void createSellerandProduct() throws Exception {

        if(seller==null && product==null){
            seller= new Seller("Selerko","seleris@seler.sk","Selerova 96");
            //Create seller
            String sellerid=mockMvc.perform(post("/api/seller")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(seller)))
                    .andExpect(status().isCreated())
                    .andReturn().getResponse().getContentAsString();
            seller.setId(objectMapper.readValue(sellerid,Integer.class));

            product= new Product(seller.getId()
                    ,"Taska"
                    ,"Cervena taska vysivana"
                    ,BigDecimal.valueOf(1.25)
                    ,50
                    , Timestamp.from(Instant.now()),"Clothes");

        }
    }

    @Test
    public void testProductu() throws Exception {

                //Create product
                String idofproduct=mockMvc.perform(post("/api/product")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .content(objectMapper.writeValueAsString(product)))
                                          .andExpect(status().isCreated())
                                          .andReturn().getResponse().getContentAsString();

                product.setId(objectMapper.readValue(idofproduct,Integer.class));

                //Get product by id
                String productjson=mockMvc.perform(get("/api/product/"+product.getId())
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
                BigDecimal updateValue=product.getValue().add(BigDecimal.valueOf(1.0));
                int updateCount=product.getCount()+10;
                UpdateProductRequest request= new UpdateProductRequest(product.getName(),product.getInfo(),updateValue,updateCount);
                mockMvc.perform(patch("/api/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

                String updatedproductjson=mockMvc.perform(get("/api/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
                Product updatedproductfromdb=objectMapper.readValue(updatedproductjson,Product.class);

                Assert.assertNotEquals(product.getCount(),updatedproductfromdb.getCount());
                Assert.assertNotEquals(product.getValue(),updatedproductfromdb.getValue());

                //Test insert more products
                Product productone=new Product(4,"Babika jedna","Cierna babika",BigDecimal.valueOf(0.98),10,Timestamp.from(Instant.now()),"Toys");
        Product producttwo=new Product(4,"Babika dva","Beial babika",BigDecimal.valueOf(0.88),10,Timestamp.from(Instant.now()),"Toys");
        List<Product> moreProducts=new ArrayList<>();
        moreProducts.add(productone); moreProducts.add(producttwo);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moreProducts)))
                .andExpect(status().isOk());


        //Test update more products

        List<UpdateProductRequest> requests = new ArrayList<>();
        UpdateProductRequest request1= new UpdateProductRequest("Nieco ine ako babika","I dont know",BigDecimal.valueOf(10),10,1,"Toys");
        UpdateProductRequest request2= new UpdateProductRequest("Nieco ine ako babika dva","I dont know",BigDecimal.valueOf(8),5,2,"Toys");
        requests.add(request1);
        requests.add(request2);

        mockMvc.perform(patch("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isOk());

        List<UpdateProductRequest> requests2 = new ArrayList<>();
        UpdateProductRequest request3= new UpdateProductRequest("Picovina","I dont know",BigDecimal.valueOf(9.98),10,1,"Other");
        UpdateProductRequest request4= new UpdateProductRequest("Kokotina","I dont know",BigDecimal.valueOf(7.89),5,15,"Other");
        requests2.add(request3);
        requests2.add(request4);

        mockMvc.perform(patch("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requests2)))
                .andExpect(status().isPreconditionFailed());

        //Test update products by discount
        List<Integer> idofproducts=new ArrayList<>();
        idofproducts.add(1);
        idofproducts.add(2);
        ProductDiscountUpdate productDiscountUpdate= new ProductDiscountUpdate(idofproducts,BigDecimal.valueOf(20));

        mockMvc.perform(post("/api/products/discount")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDiscountUpdate)))
                .andExpect(status().isOk());

        String productonejson=mockMvc.perform(get("/api/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Product updatnutyproductfromdb=objectMapper.readValue(productonejson,Product.class);

        Assert.assertEquals(BigDecimal.valueOf(8.00).setScale(2),updatnutyproductfromdb.getValue());


        //Delete product by id
                mockMvc.perform(delete("/api/product/"+product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status()
                        .isOk());

                mockMvc.perform(get("/api/product/"+product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());

    }

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

        CustomerAccount customerAccount= new CustomerAccount(5,BigDecimal.valueOf(536.25));

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


                //Get seller by id
                String sellerjson=mockMvc.perform(get("/api/seller/"+seller.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

                Seller sellerfromdb= objectMapper.readValue(sellerjson,Seller.class);
                Assert.assertEquals(sellerfromdb,seller);

                //Get Seller with id that doesnt exist
                mockMvc.perform(get("/api/seller/15")
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
                int idicko=seller.getId()-1;
                Assert.assertEquals(seller,listSellers.get(idicko));
                Assert.assertEquals(seller.getId(),listSellers.size());

                //Test get all products by seller id
                int idofseller=1;
                String listOfAllProductsBySellerIdJson=mockMvc.perform(get("/api/seller/"+idofseller+"/products")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

                objectMapper.readValue(listOfAllProductsBySellerIdJson, new TypeReference<List<Product>>() {
                });

                //Delete seller
                mockMvc.perform(delete("/api/seller/"+seller.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

                //Delete seller with id that doesnt exist
                mockMvc.perform(delete("/api/seller/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void testCustomerAccount() throws Exception {
        Customer customer= new Customer("Jozko","Stasak","stasacik@gmail.com");

                String customerid=mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int customeridtoupdate=objectMapper.readValue(customerid,Integer.class);
        UpdateCustomerAccountMoney updateCustomerAccountMoney= new UpdateCustomerAccountMoney(customeridtoupdate,BigDecimal.valueOf(300));

                String messagefromupdate=mockMvc.perform(patch("/api/customer-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCustomerAccountMoney)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


                        mockMvc.perform(get("/api/customer-account/"+customeridtoupdate)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();



                //Testovanie RestShoppingu odtialto

        Product product= new Product(1,"Taska","Taska rapotaska",BigDecimal.valueOf(24.99),11,Timestamp.from(Instant.now()),"Toys");

                String productIdJson=mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        int productIdFromCreateProduct=objectMapper.readValue(productIdJson,int.class);
        BuyProductRequest buyProductRequest= new BuyProductRequest(productIdFromCreateProduct,1,3);

                String responseJson=mockMvc.perform(post("/api/shopping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyProductRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

//        BuyProductResponse buyProductResponse=objectMapper.readValue(responseJson,BuyProductResponse.class);
        System.out.println(responseJson);
        //Skuska metody ked neexistuje product musel som to zmenit na product id 3 pretoze hore v testoch sa vytvori este jeden produkt niekedy
        BuyProductRequest buyProductRequest2= new BuyProductRequest(3,1,3);

        String responseJsonFailed=mockMvc.perform(post("/api/shopping")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyProductRequest2)))
                .andExpect(status().isPreconditionFailed())
                .andReturn().getResponse().getContentAsString();

//        BuyProductResponse buyProductResponse2=objectMapper.readValue(responseJsonFailed,BuyProductResponse.class);
        System.out.println(responseJsonFailed);

    }

    @Test
    public void testovanieShoppingByCard() throws Exception {
        //Vytvorenie customera productu a customer account pretoze su potrebny na testovanie
        Customer customer= new Customer("Jozko","Stasak","stasacik@gmail.com");

        String idofcustomer=mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        int customeridtoupdate=objectMapper.readValue(idofcustomer,Integer.class);
        UpdateCustomerAccountMoney updateCustomerAccountMoney= new UpdateCustomerAccountMoney(customeridtoupdate,BigDecimal.valueOf(300));

        String messagefromupdate=mockMvc.perform(patch("/api/customer-account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCustomerAccountMoney)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(get("/api/customer-account/"+idofcustomer)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product product= new Product(1,"Taska","Taska rapotaska",BigDecimal.valueOf(24.99),11,Timestamp.from(Instant.now()),"Toys");

        String productIdJson=mockMvc.perform(post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        int productIdFromCreateProduct=objectMapper.readValue(productIdJson,int.class);


        //Otestovanie uspesneho nakupu by credit card
        BuyProductByCardRequest Request= new BuyProductByCardRequest(1
                                                                    ,1
                                                                    ,"4405 7789 6739 6700"
                                                                    ,"04/22","271"
                                                                    ,3);

                 String responsejson=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Ocakavame uspesny nakup: "+responsejson);

        //Otestovanie nakupu by card ked product neexistuje
        BuyProductByCardRequest Request1= new BuyProductByCardRequest(8
                ,1
                ,"4405 7789 6739 6700"
                ,"04/22","271"
                ,3);

        String responsejsonfailedbyproductid=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request1)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Ocakavame chybne product id"+responsejsonfailedbyproductid);

        //Testovanie requestu ktory ma chybne customer id
        BuyProductByCardRequest Request2= new BuyProductByCardRequest(1
                ,8
                ,"4405 7789 6739 6700"
                ,"04/22","271"
                ,3);

        String responsejsonfailedbycustomerid=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request2)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println("Chybne customer id: "+responsejsonfailedbycustomerid);


        //Otestovanie requestu s chybnym count od buying product
        BuyProductByCardRequest Request3= new BuyProductByCardRequest(1
                ,1
                ,"4405 7789 6739 6700"
                ,"04/22","271"
                ,125);

        String responsejsonfailedbycountofproduct=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request3)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //Otestovanie requestu s chybnou card number
        BuyProductByCardRequest Request4= new BuyProductByCardRequest(1
                ,1
                ,"44057789 6739 6700"
                ,"04/22","271"
                ,2);

        String responsejsonfailedbycardnumber=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request4)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Chybny card number: "+responsejsonfailedbycardnumber);

        //Otestovanie requestu s chybnou datecard
        BuyProductByCardRequest Request5= new BuyProductByCardRequest(1
                ,1
                ,"4405 7789 6739 6700"
                ,"04/2","271"
                ,2);

        String responsejsonfailedbydatecard=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request5)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Chybny date card: "+responsejsonfailedbydatecard);

        //Otestovanie requestu s chybnym safe codom
        BuyProductByCardRequest Request6= new BuyProductByCardRequest(1
                ,1
                ,"4405 7789 6739 6700"
                ,"04/22","27a"
                ,2);

        String responsejsonfailedbysafecode=mockMvc.perform(post("/api/shopping/card")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Request6)))
                .andExpect(status().isPreconditionFailed())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println("Chybny safe code: "+responsejsonfailedbysafecode);

    }

    @Test
    public void testBoughtProduct() throws Exception {


        Product product= new Product(1,"Jazda","Jazdovic",BigDecimal.valueOf(1.59),20,Timestamp.from(Instant.now()),"Toys");
        Product product1=new Product(1,"Slon","Elefander skvrnity",BigDecimal.valueOf(0.59),15,Timestamp.from(Instant.now()),"Toys");

        Customer customer= new Customer("Jozko","picko@gmail.com","Alopova 54");
        Customer customer1= new Customer("Alfonz","lojzik@gmail.com","Lojzova 54");
        //Add more products
        List<Product> moreProducts=new ArrayList<>();
        moreProducts.add(product); moreProducts.add(product1);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(moreProducts)))
                .andExpect(status().isOk());

        //Add customer
        String idofcustomer= mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        //Add customer1
        String idofcustomer1= mockMvc.perform(post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();


        //Add bought product to database
        BoughtProduct boughtProduct= new BoughtProduct(1,1,5,Timestamp.from(Instant.now()));
        BoughtProduct boughtProduct1= new BoughtProduct(1,2,2,Timestamp.from(Instant.now()));
        BoughtProduct boughtProduct2= new BoughtProduct(2,2,2,Timestamp.from(Instant.now()));
        BoughtProduct boughtProduct3= new BoughtProduct(2,1,10,Timestamp.from(Instant.now()));

        boughtProductInterface.createBoughtProduct(boughtProduct);
        boughtProductInterface.createBoughtProduct(boughtProduct1);
        boughtProductInterface.createBoughtProduct(boughtProduct2);
        boughtProductInterface.createBoughtProduct(boughtProduct3);

                 String listofallproductbycustomerid=mockMvc.perform(get("/api/bought-product/customer/"+idofcustomer)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

                 List<BoughtProduct> boughtProductListByCustomerId=objectMapper.readValue(listofallproductbycustomerid, new TypeReference<List<BoughtProduct>>() {
                 });

                 Assert.assertEquals(2,boughtProductListByCustomerId.size());

        String listofallproductbyproductid=mockMvc.perform(get("/api/bought-product/product/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<BoughtProduct> boughtProductListByProductId= objectMapper.readValue(listofallproductbyproductid, new TypeReference<List<BoughtProduct>>() {
        });

        Assert.assertEquals(2,boughtProductListByProductId.size());


    }


}
