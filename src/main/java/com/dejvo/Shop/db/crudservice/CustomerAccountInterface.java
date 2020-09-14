package com.dejvo.Shop.db.crudservice;


import com.dejvo.Shop.db.request.UpdateCustomerAccountMoney;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;

import java.math.BigDecimal;
import java.util.List;


public interface CustomerAccountInterface {

    public Integer createCustomerAccount(CustomerAccount customerAccount);
    public CustomerAccount getCustomerByIdOfCustomer(int idofcustomer);
    public List<CustomerAccount> getAllCustomerAccounts();
    public Integer updateMoneyOfCustomerAccount(UpdateCustomerAccountMoney updateCustomerAccountMoney);
    public BigDecimal getMoneyByCustomerId(int idofcustomer);
}
