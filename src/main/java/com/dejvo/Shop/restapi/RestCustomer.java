package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.CustomerInterface;
import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCustomer {

    @Autowired
    CustomerInterface customerInterface;


    @GetMapping("/customers")
    public ResponseEntity getAllCustomers(){

        List<Customer> listOfCustomers=customerInterface.readAllCustomers();
        return new ResponseEntity<>(listOfCustomers, HttpStatus.OK);

    }

    @GetMapping("/customer/{id}")
    public ResponseEntity getCustomerById(@PathVariable("id") Long id){
        Customer customer=customerInterface.readCustomerById(id);
        if(customer!=null){
            return new ResponseEntity<>(customer,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity createCustomerById(@RequestBody Customer customer){
        Customer customeris=customerInterface.createCustomer(customer);
        if(customeris!=null){
            return new ResponseEntity<>(customeris,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/customer/{id}")
    public void deleteCustomerByID(@PathVariable("id") Long id){
        customerInterface.deleteCustomer(id);
    }

    @PutMapping("/customer/{id}")
    public ResponseEntity updateCustomerById(@PathVariable("id") Long id,@RequestBody UpdateCustomerRequest request){
        if(customerInterface.updateCustomer(request,id)==1){
            return new ResponseEntity<>("{}",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

}
