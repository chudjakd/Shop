package com.dejvo.Shop;

import com.dejvo.Shop.model.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class DBTestsTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertCustomer= "INSERT INTO CUSTOMER (name,email,address) VALUES (?,?,?)";
    private final String insertProduct= "INSERT INTO PRODUCT (name,email,address) VALUES (?,?,?)";
    @Test
    public void createCustomer(){
        Customer customer= new Customer();
        customer.setName("Janko");
        customer.setEmail("janko@grag.sk");
        customer.setAddress("picovina 54");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(insertCustomer);
                ps.setString(1,"Janko");
                ps.setString(2,"janko@grag.sk");
                ps.setString(3,"picovina 54");
                return ps;
            }
        });

    }

}