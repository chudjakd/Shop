package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.mapper.CustomerRowMapper;
import com.dejvo.Shop.db.repository.CustomerRepository;
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

    CustomerRepository customerRepository;
    @Autowired
    CustomerImplementacia customerImplementacia = new CustomerImplementacia(customerRepository);

    @Test
    public void customer(){

    }

    @Test
    void readCustomerById2AndCompareName() {
        assertEquals("Pulder", customerImplementacia.readCustomerById(1L).getName());
    }
    @Test
    void readCustomerById5AndCompareName() {
        assertEquals("Erdo", customerImplementacia.readCustomerById(6L).getName());
    }

    @Test
    void readAllCustomers() {
        assertEquals(2, customerImplementacia.readAllCustomers().size());
    }

    @Test
    public void createCustomer() {
        Customer customer = new Customer();
        customer.setName("Erdo");
        customer.setEmail("erdo@grag.sk");
        customer.setAddress("Amperko 54");
        customerImplementacia.createCustomer(customer);
    }

//    @Test
//    public void updateCustomerWithId2(){
//        Customer customer = new Customer();
//        customer.setId(1L);
//        customer.setName("Pulder");
//        customer.setEmail("puldo@grag.sk");
//        customer.setAddress("Japerova 54");
//
//        assertEquals(0,customerImplementacia.updateCustomer(customer,2L));
//    }
    @Test
    public void deleteCustomerWithId5(){
        customerImplementacia.deleteCustomer(5L);
    }
}