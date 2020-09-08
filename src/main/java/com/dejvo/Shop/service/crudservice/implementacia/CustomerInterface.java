package com.dejvo.Shop.service.crudservice.implementacia;

import com.dejvo.Shop.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerInterface implements com.dejvo.Shop.service.crudservice.CustomerInterface {

    JdbcTemplate jdbcTemplate;

    public CustomerInterface(JdbcTemplate jdbcTemplate) {
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
            customer.setId(resultSet.getLong(0));
            customer.setName(resultSet.getString(1));
            customer.setEmail(resultSet.getString(2));
            customer.setAddress(resultSet.getString(3));
            return customer;
        } );



        return null;
    }
}
