package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class CustomerImplementacia implements com.dejvo.Shop.db.crudservice.CustomerInterface {


    JdbcTemplate jdbcTemplate;
    @Autowired
    public CustomerImplementacia(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public void createCustomer(Customer customer) {
        String query="INSERT INTO CUSTOMER (name,email,address) VALUES (?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(query);
                ps.setString(1,customer.getName());
                ps.setString(2,customer.getEmail());
                ps.setString(3,customer.getAddress());
                return ps;
            }
        });
    }

    @Override
    public List<Customer> readCustomerById(Long id) {
        Customer customer= new Customer();
        String query="SELECT * FROM CUSTOMER WHERE ID="+id.toString();
        List<Customer> customers=jdbcTemplate.query(query,(resultSet, i) -> {
            customer.setId(resultSet.getLong(1));
            customer.setName(resultSet.getString(2));
            customer.setEmail(resultSet.getString(3));
            customer.setAddress(resultSet.getString(4));
            return customer;
        } );

        return customers;
    }

    @Override
    public List<Customer> readAllCustomers() {
        Customer customer= new Customer();
        String query="SELECT id,name,email,address FROM customer";
        List<Customer> customers=jdbcTemplate.query(query,(resultSet, i) -> {
            customer.setId(resultSet.getLong(1));
            customer.setName(resultSet.getString(2));
            customer.setEmail(resultSet.getString(3));
            customer.setAddress(resultSet.getString(4));
            return customer;
        } );

        return customers;
    }
    @Override
    public int updateCustomer(Customer customer, Long id){
        String updateQuery = "update Customer set id = ?, name =?, email=?,address =? where id = ?";
        jdbcTemplate.update(updateQuery,customer.getId()
                                       ,customer.getName()
                                       ,customer.getEmail()
                                       ,customer.getAddress()
                                       ,id);
        return 1;
    }

    @Override
    public void deleteCustomer(Long id) {
        String deleteQuery= "DELETE FROM CUSTOMER WHERE ID="+id.toString();
        jdbcTemplate.update(deleteQuery);
    }
}
