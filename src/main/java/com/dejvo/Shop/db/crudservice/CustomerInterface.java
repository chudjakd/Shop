package com.dejvo.Shop.db.crudservice;

import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;

import java.util.List;

public interface CustomerInterface {

    public Integer createCustomer(Customer customer);
    public Customer readCustomerById(Long id);
    public List<Customer> readAllCustomers();
    public int updateCustomer (UpdateCustomerRequest request, Long id);
    public int deleteCustomer (Long id);
}
