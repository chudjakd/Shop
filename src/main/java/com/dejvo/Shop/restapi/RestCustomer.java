package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.CustomerInterface;
import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCustomer {

    @Autowired
    CustomerInterface customerInterface;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerInterface.readAllCustomers();

    }

    @GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id){
        return customerInterface.readCustomerById(id);
    }

    @PostMapping("/customer")
    public Customer createCustomerById(@RequestBody Customer customer){
        return customerInterface.createCustomer(customer);
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomerByID(@PathVariable("id") Long id){
        customerInterface.deleteCustomer(id);
    }
    @PutMapping("/customer/{id}")
    public void updateCustomerById(@PathVariable("id") Long id,@RequestBody Customer customer){
        customerInterface.updateCustomer(customer,id);
    }

}
