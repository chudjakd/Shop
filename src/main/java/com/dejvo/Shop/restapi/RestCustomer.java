package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.CustomerAccountInterface;
import com.dejvo.Shop.db.crudservice.CustomerInterface;
import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;
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
    @Autowired
    CustomerAccountInterface customerAccountInterface;


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
        Integer customerid=customerInterface.createCustomer(customer);
        if(customerid!=null){
            return new ResponseEntity<>(customerid,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity deleteCustomerByID(@PathVariable("id") Long id){
        if(customerInterface.readCustomerById(id)==null){
            return new ResponseEntity<>("Customer s tymto id neexistuje",HttpStatus.PRECONDITION_FAILED);
        }
        else {
            customerInterface.deleteCustomer(id);
            return new ResponseEntity<>("Delete uspesny",HttpStatus.OK);
        }

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
    @PostMapping("/customer/account")
    public ResponseEntity createCustomerAccount(@RequestBody CustomerAccount customerAccount){
        customerAccountInterface.createCustomerAccount(customerAccount);
        return new ResponseEntity<>(null,HttpStatus.OK);
    }

}
