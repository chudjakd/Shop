package com.dejvo.Shop.db.crudservice.implementacia;


import com.dejvo.Shop.db.repository.CustomerRepository;
import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;
import org.hibernate.sql.Update;
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
        Integer key=customerRepository.createCustomer(customer);
        return key;
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
    public int updateCustomer(UpdateCustomerRequest request, Long id){
        return customerRepository.updateCustomer(request,id);
    }

    @Override
    public int deleteCustomer(Long id) {
        return customerRepository.deleteCustomer(id);
    }
}
