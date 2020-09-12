package com.dejvo.Shop.db.crudservice;

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
        Product product= new Product(1,"Lepidlo","Biele lepidlo lacne",BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()));
        Product product2= new Product(1,"Tuska","Biele tuska",BigDecimal.valueOf(0.33),5, Timestamp.from(Instant.now()));

        assertNotNull(productInterface.createProduct(product));
        assertNotNull(productInterface.createProduct(product2));
        assertEquals(2,productInterface.readAllProducts().size());

        UpdateProductRequest request= new UpdateProductRequest("Nove Lepidlo","Nove biele lepidlo",BigDecimal.valueOf(0.80),5,1);
        UpdateProductRequest request2= new UpdateProductRequest("Cierna tuska","Nova cierna tuska",BigDecimal.valueOf(0.70),8,2);

        List<UpdateProductRequest> requests=new ArrayList<>();
        requests.add(request);
        requests.add(request2);

        assertEquals(1,productInterface.updateMoreProducts(requests));
        assertEquals(BigDecimal.valueOf(0.80),productInterface.readProductById(1).getValue());
        assertEquals("Cierna tuska",productInterface.readProductById(2).getName());

    }





    @Test
    @Disabled("OldTest")
    public void createProduct(){
        Product product= new Product(1,"Lepidlo","Biele lepidlo lacne",BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()));
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