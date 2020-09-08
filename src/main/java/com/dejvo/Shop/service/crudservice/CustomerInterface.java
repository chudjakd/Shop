package com.dejvo.Shop.service.crudservice;

import com.dejvo.Shop.model.Customer;

import java.util.List;

public interface CustomerInterface {

    public void createCustomer(Customer customer);
    public List<Customer> readCustomerById(Long id);
}
