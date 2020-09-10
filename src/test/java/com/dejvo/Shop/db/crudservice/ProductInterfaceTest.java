package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInterfaceTest {
    @Autowired
    ProductInterface productInterface;

    @Test
    public void createProduct(){
        Product product= new Product(1L,"Lepidlo","Biele lepidlo lacne",BigDecimal.valueOf(0.55),10, Timestamp.from(Instant.now()));
        assertNotNull(productInterface.createProduct(product));
    }

    @Test
    public void deleteProductWithId(){
        assertEquals(1,productInterface.deleteProduct(2));

    }

    @Test
    public void readAllProducts(){
        assertEquals(2,productInterface.readAllProducts());
    }

    @Test
    public void readProductById(){
        assertEquals("Taska",productInterface.readProductById(3).getName());
    }
    @Test
    public void updateProductById(){
        UpdateProductRequest request= new UpdateProductRequest("Taska Rapotaska","Trocha ina taska ale dobra", BigDecimal.valueOf(0.69),25);
        assertEquals(1,productInterface.updateProduct(request,3));
    }
    @Test
    public void updateProductWithNonExistingId(){
        UpdateProductRequest request= new UpdateProductRequest("Taska Rapotaska","Trocha ina taska ale dobra",BigDecimal.valueOf(0.69),25);
        assertEquals(0,productInterface.updateProduct(request,355));
    }
}