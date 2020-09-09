package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.CustomerRowMapper;
import com.dejvo.Shop.db.request.UpdateCustomerRequest;
import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class CustomerRepository {

    CustomerRowMapper customerRowMapper;
    JdbcTemplate jdbcTemplate;
    @Autowired
    public CustomerRepository(CustomerRowMapper customerRowMapper, JdbcTemplate jdbcTemplate) {
        this.customerRowMapper = customerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer createCustomer(Customer customer){

        String query="INSERT INTO CUSTOMER (name,email,address) VALUES (?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,customer.getName());
                ps.setString(2,customer.getEmail());
                ps.setString(3,customer.getAddress());
                return ps;
            }
        },keyHolder);

        if(keyHolder!=null){
            return keyHolder.getKey().intValue();
        }
        else{
        return null;
        }
    }

    public Customer readCustomerById(Long id) {
        try{
            String query="SELECT * FROM CUSTOMER WHERE ID="+id.toString();
            return jdbcTemplate.queryForObject(query,customerRowMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<Customer> readAllCustomers(){

        String query="SELECT id,name,email,address FROM customer";

        return jdbcTemplate.query(query,customerRowMapper);

    }

    public int updateCustomer(UpdateCustomerRequest request, Long id){
        try{
            String updateQuery = "update Customer set name = ?, email =?, address=? where id = ?";
            jdbcTemplate.update(updateQuery,request.getName()
                    ,request.getEmail()
                    ,request.getAddress()
                    ,id);
            return 1;
        }
        catch (DataAccessException e){
            return 0;
        }
    }

    public int deleteCustomer(Long id){
        try{
            String deleteQuery= "DELETE FROM CUSTOMER WHERE ID="+id.toString();
            jdbcTemplate.update(deleteQuery);
            return 1;
        }
        catch (DataAccessException e){
            return 0;
        }
    }
}
