package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.CustomerAccountInterface;
import com.dejvo.Shop.db.repository.CustomerAccountRepository;
import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountImplementacia implements CustomerAccountInterface {

    CustomerAccountRepository customerAccountRepository;

    public CustomerAccountImplementacia(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public Integer createCustomerAccount(CustomerAccount customerAccount) {
        Integer key=customerAccountRepository.createCustomerAccount(customerAccount);
        return key;
    }
}
