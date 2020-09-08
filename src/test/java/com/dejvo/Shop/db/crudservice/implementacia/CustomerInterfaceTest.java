package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerInterfaceTest {


    JdbcTemplate jdbcTemplate;
    @Autowired
    CustomerImplementacia customerInterface = new CustomerImplementacia(jdbcTemplate);

    @Test
    void readCustomerById() {
        assertEquals(1, customerInterface.readCustomerById(2L).size());
    }

    @Test
    void readCustomerById2AndCompareName() {
        assertEquals("Janko", customerInterface.readCustomerById(2L).get(0).getName());
    }
    @Test
    void readCustomerById5AndCompareName() {
        assertEquals("Erdo", customerInterface.readCustomerById(5L).get(0).getName());
    }

    @Test
    void readAllCustomers() {
        assertEquals(3, customerInterface.readAllCustomers().size());
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setName("Erdo");
        customer.setEmail("erdo@grag.sk");
        customer.setAddress("Amperko 54");
        customerInterface.createCustomer(customer);
    }

    @Test
    public void updateCustomerWithId2(){
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Pulder");
        customer.setEmail("puldo@grag.sk");
        customer.setAddress("Japerova 54");

        customerInterface.updateCustomer(customer,2L);
    }
    @Test
    public void deleteCustomerWithId5(){
        customerInterface.deleteCustomer(5L);
    }
}