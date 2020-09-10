package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.model.CustomerAccount;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class CustomerAccountInterfaceTest {
    @Autowired
    CustomerAccountInterface customerAccountInterface;


    @BeforeEach
    public void createCustomerAccounta(){
        CustomerAccount customerAccount= new CustomerAccount(6,BigDecimal.valueOf(250.5));
        customerAccountInterface.createCustomerAccount(customerAccount);
    }

    @Test
    public void testCustomerAccounta(){

        //Test Get
        customerAccountInterface.getCustomerByIdOfCustomer(6);

        CustomerAccount customerAccount= new CustomerAccount();
        customerAccount.setId(2);
        customerAccount.setCustomerid(5);
        customerAccount.setMoney(BigDecimal.valueOf(351.55));
        //Test Create
        Assert.assertNotNull( customerAccountInterface.createCustomerAccount(customerAccount));
        //Test get all
        Assert.assertEquals(2,customerAccountInterface.getAllCustomerAccounts().size());
        Assert.assertEquals(BigDecimal.valueOf(250.5),customerAccountInterface.getAllCustomerAccounts().get(0).getMoney());
        Assert.assertEquals(BigDecimal.valueOf(351.55),customerAccountInterface.getAllCustomerAccounts().get(1).getMoney());

        //Test update
        int pica=customerAccountInterface.updateMoneyOfCustomerAccount(BigDecimal.valueOf(598.2),5);
        Assert.assertEquals(1,pica);

        Assert.assertEquals(BigDecimal.valueOf(598.2),customerAccountInterface.getCustomerByIdOfCustomer(5).getMoney());

        Assert.assertEquals(BigDecimal.valueOf(598.2),customerAccountInterface.getMoneyByCustomerId(5));
        Assert.assertEquals(BigDecimal.valueOf(-1),customerAccountInterface.getMoneyByCustomerId(10));





    }
}