package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.model.Customer;

import java.util.List;

public interface CustomerInterface {

    public Integer createCustomer(Customer customer);
    public Customer readCustomerById(Long id);
    public List<Customer> readAllCustomers();
    public int updateCustomer (Customer customer, Long id);
    public void deleteCustomer (Long id);
}
