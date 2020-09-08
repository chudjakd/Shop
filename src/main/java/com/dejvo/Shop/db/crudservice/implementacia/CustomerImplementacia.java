package com.dejvo.Shop.db.crudservice.implementacia;


import com.dejvo.Shop.db.repository.CustomerRepository;
import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
;
import java.util.List;

@Service
public class CustomerImplementacia implements com.dejvo.Shop.db.crudservice.CustomerInterface {

    CustomerRepository customerRepository;
    @Autowired
    public CustomerImplementacia(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Integer createCustomer(Customer customer) {
        return customerRepository.createCustomer(customer);
    }

    @Override
    public Customer readCustomerById(Long id) {
        return customerRepository.readCustomerById(id);
    }

    @Override
    public List<Customer> readAllCustomers() {
        return customerRepository.readAllCustomers();
    }
    @Override
    public int updateCustomer(Customer customer, Long id){
        return customerRepository.updateCustomer(customer,id);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteCustomer(id);
    }
}
