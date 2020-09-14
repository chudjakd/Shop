package com.dejvo.Shop.restapi;

import com.dejvo.Shop.db.crudservice.CustomerAccountInterface;
import com.dejvo.Shop.db.request.UpdateCustomerAccountMoney;
import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestCustomerAccount {

    CustomerAccountInterface customerAccountInterface;

    public RestCustomerAccount(CustomerAccountInterface customerAccountInterface) {
        this.customerAccountInterface = customerAccountInterface;
    }

    //Treba davat pozor lebo toto mam dva krat da sa to vytvorit aj cez restcustomer je to tam asi lepsie
    @PostMapping("/customer-account")
    public ResponseEntity createCustomerAccount(@RequestBody CustomerAccount customerAccount){

        Integer responseFromCreate=customerAccountInterface.createCustomerAccount(customerAccount);

        if(responseFromCreate!=null){
            return new ResponseEntity<>(customerAccount.getCustomerid(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Build was not successful",HttpStatus.PRECONDITION_FAILED);
        }
    }

    @GetMapping("/customer-account/{id}")
    public ResponseEntity getCustomerAccountById(@PathVariable("id") int customerid){
        CustomerAccount customerAccount=customerAccountInterface.getCustomerByIdOfCustomer(customerid);
        if(customerAccount!=null){
            return new ResponseEntity<>(customerAccount,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Toto nebolo uspesne skus skontrolovat to id co si zadal",HttpStatus.PRECONDITION_FAILED);
        }
    }
    @PatchMapping("/customer-account")
    public ResponseEntity updateCustomerMoneyByCustomerId(@RequestBody UpdateCustomerAccountMoney updateCustomerAccountMoney){
        Integer response=customerAccountInterface.updateMoneyOfCustomerAccount(updateCustomerAccountMoney);
        if(response!=null){
            return new ResponseEntity<>("Update money pre customera s id:"+response +" prebehol uspesne",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Tak tento update nevysiel skus skontrolovat co si zadal",HttpStatus.PRECONDITION_FAILED);
        }
    }
}
