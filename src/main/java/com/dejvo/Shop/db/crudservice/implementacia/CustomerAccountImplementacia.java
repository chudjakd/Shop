package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.CustomerAccountInterface;
import com.dejvo.Shop.db.repository.CustomerAccountRepository;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerAccountImplementacia implements CustomerAccountInterface {

    CustomerAccountRepository customerAccountRepository;

    public CustomerAccountImplementacia(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public Integer createCustomerAccount(CustomerAccount customerAccount) {
        return customerAccountRepository.createCustomerAccount(customerAccount);
    }

    @Override
    public CustomerAccount getCustomerByIdOfCustomer(int id) {
       return customerAccountRepository.getCustomerAccountByIdOfCustomer(id);
    }

    @Override
    public List<CustomerAccount> getAllCustomerAccounts() {
        return customerAccountRepository.getAllCustomerAccount();
    }

    @Override
    public Integer updateMoneyOfCustomerAccount(double money, int id) {
        return customerAccountRepository.updateCustomerAccountMoney(money,id);
    }

    @Override
    public double getMoneyByCustomerId(int idofcustomer) {
        return customerAccountRepository.getMoneyByCustomerId(idofcustomer);
    }
}
