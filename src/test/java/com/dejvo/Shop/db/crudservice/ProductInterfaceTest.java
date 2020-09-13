package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.ProductDiscountUpdate;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInterfaceTest {
    @Autowired
    ProductInterface productInterface;


    @Test
    public void TestProductu(){
        //Testovanie update viacerych produktov
        Product product= new Product(1,"Lepidlo","Biele lepidlo lacne",BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()),"Toys");
        Product product2= new Product(1,"Tuska","Biele tuska",BigDecimal.valueOf(0.33),5, Timestamp.from(Instant.now()),"Toys");

        assertNotNull(productInterface.createProduct(product));
        assertNotNull(productInterface.createProduct(product2));
        assertEquals(2,productInterface.readAllProducts().size());

        UpdateProductRequest request= new UpdateProductRequest("Nove Lepidlo","Nove biele lepidlo",BigDecimal.valueOf(0.80),5,1,"Toys");
        UpdateProductRequest request2= new UpdateProductRequest("Cierna tuska","Nova cierna tuska",BigDecimal.valueOf(0.70),8,2,"Toys");

        List<UpdateProductRequest> requests=new ArrayList<>();
        requests.add(request);
        requests.add(request2);

        assertNotNull(productInterface.updateMoreProducts(requests));

        assertEquals(1,productInterface.updateMoreProducts(requests));
        assertEquals(BigDecimal.valueOf(0.80).setScale(2),productInterface.readProductById(1).getValue().setScale(2));
        assertEquals("Cierna tuska",productInterface.readProductById(2).getName());

        //Testovanie get all id of products
        assertEquals(2,productInterface.getAllIdOfProducts().size());
        assertEquals(1,productInterface.getAllIdOfProducts().get(0));
        assertEquals(2,productInterface.getAllIdOfProducts().get(1));

        //Testovanie update more products
        List<Integer> ajdickanaupdate=new ArrayList<>();
        ajdickanaupdate.add(1);
        ajdickanaupdate.add(2);
        ProductDiscountUpdate productDiscountUpdate= new ProductDiscountUpdate(ajdickanaupdate,BigDecimal.valueOf(10));

        assertEquals(1,productInterface.updateProductByDiscount(productDiscountUpdate));

        assertEquals(BigDecimal.valueOf(0.72),productInterface.readProductById(1).getValue());
        assertEquals(BigDecimal.valueOf(0.63),productInterface.readProductById(2).getValue());

        List<Integer> ajdickanaupdatektoreneexistuju=new ArrayList<>();
        ajdickanaupdate.add(1);
        ajdickanaupdate.add(5);
        ProductDiscountUpdate productDiscountUpdateFailed= new ProductDiscountUpdate(ajdickanaupdatektoreneexistuju,BigDecimal.valueOf(10));

        assertNull(productInterface.updateProductByDiscount(productDiscountUpdateFailed));


    }





    @Test
    @Disabled("OldTest")
    public void createProduct(){
        Product product= new Product(1,"Lepidlo","Biele lepidlo lacne",BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()),"Toys");
        assertNotNull(productInterface.createProduct(product));
    }

    @Test
    @Disabled("OldTest")
    public void deleteProductWithId(){
        assertEquals(1,productInterface.deleteProduct(2));

    }

    @Test
    @Disabled("OldTest")
    public void readAllProducts(){
        assertEquals(2,productInterface.readAllProducts());
    }

    @Test
    @Disabled("OldTest")
    public void readProductById(){
        assertEquals("Taska",productInterface.readProductById(3).getName());
    }
    @Test
    @Disabled("OldTest")
    public void updateProductById(){
        UpdateProductRequest request= new UpdateProductRequest("Taska Rapotaska","Trocha ina taska ale dobra", BigDecimal.valueOf(0.69),25);
        assertEquals(1,productInterface.updateProduct(request,3));
    }
    @Test
    @Disabled("OldTest")
    public void updateProductWithNonExistingId(){
        UpdateProductRequest request= new UpdateProductRequest("Taska Rapotaska","Trocha ina taska ale dobra",BigDecimal.valueOf(0.69),25);
        assertEquals(0,productInterface.updateProduct(request,355));
    }
}