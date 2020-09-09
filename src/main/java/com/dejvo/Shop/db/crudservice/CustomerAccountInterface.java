package com.dejvo.Shop.db.crudservice;


import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;

import java.util.List;


public interface CustomerAccountInterface {

    public Integer createCustomerAccount(CustomerAccount customerAccount);
    public CustomerAccount getCustomerById(int id);
    public List<CustomerAccount> getAllCustomerAccounts();
    public Integer updateMoneyOfCustomerAccount(double money, int id);
}
