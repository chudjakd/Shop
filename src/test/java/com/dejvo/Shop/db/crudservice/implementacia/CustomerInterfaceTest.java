package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.mapper.CustomerRowMapper;
import com.dejvo.Shop.db.repository.CustomerRepository;
import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CustomerInterfaceTest {
    // TODO: 8. 9. 2020 Ak chces testovat musis si davat pozor lebo tieto testy su pripojene na mysql databazu a moze sa stat ze uz nebudu existovat niektory pouzivatelia
    // ak to potom chces tak prerobit tak musis pred kazdu classu dat @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)

    CustomerRepository customerRepository;
    @Autowired
    CustomerImplementacia customerImplementacia = new CustomerImplementacia(customerRepository);


    @BeforeEach
    public void createCustomerIn(){
        Customer customer= new Customer("Jozko","jozko@gmail.com","Pokorova 85");
        customerImplementacia.createCustomer(customer);
    }

    @Test
    public void customerTest(){
        assertEquals("Jozko",customerImplementacia.readCustomerById(1L).getName()); //read name by id 1

        Customer customer = new Customer();
        customer.setName("Erdo");
        customer.setEmail("erdo@grag.sk");              //create customer
        customer.setAddress("Amperko 54");
        customerImplementacia.createCustomer(customer);

        assertEquals("Erdo",customerImplementacia.readCustomerById(2L).getName()); // read name by id 2 after successful create

        assertEquals(2, customerImplementacia.readAllCustomers().size()); // read all customers

        assertNull(customerImplementacia.readCustomerById(150L));          //read customer which doesnt exist

        assertEquals(1,customerImplementacia.deleteCustomer(15L));  // delete customer which doesnt exist

    }

}