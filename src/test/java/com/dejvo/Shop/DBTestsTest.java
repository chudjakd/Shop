package com.dejvo.Shop;

import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.CustomerAccount;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class DBTestsTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String insertCustomer= "INSERT INTO CUSTOMER (name,email,address) VALUES (?,?,?)";
    private final String insertSeller= "INSERT INTO SELLER (name,email,address) VALUES (?,?,?)";
    private final String insertProduct= "INSERT INTO PRODUCT (seller_id,name,info,value,count,datetime) VALUES (?,?,?,?,?,?)";
    private final String insertCustomerAccount="INSERT INTO customer_account  (customer_id,money) VALUES (?,?)";
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
    @Test
    public void createSeller(){
        Seller seller= new Seller();
        seller.setAddress("Abec");
        seller.setEmail("adsdfa@asdf.sl");
        seller.setName("Ertr");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(insertSeller);
                ps.setString(1,seller.getName());
                ps.setString(2,seller.getEmail());
                ps.setString(3,seller.getAddress());
                return ps;
            }
        });
    }

    @Test
    public void createProduct(){
        Product product= new Product();
        product.setSellerId(1L);
        product.setCount(150);
        product.setInfo("Hmm neviem co to je");
        product.setName("Idk");
        product.setValue(BigDecimal.valueOf(12));
        product.setDatetime(Timestamp.from(Instant.now()));

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(insertProduct);
                ps.setLong(1,product.getSellerId());
                ps.setString(2,product.getName());
                ps.setString(3,product.getInfo());
                ps.setBigDecimal(4,product.getValue());
                ps.setInt(5,product.getCount());
                ps.setTimestamp(6,product.getDatetime());
                return ps;
            }
        });

    }

    @Test
    public void createCustomerAccount(){
        CustomerAccount customerAccount= new CustomerAccount();
        customerAccount.setCustomerid(7);
        customerAccount.setMoney(250.5);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(insertCustomerAccount);
                ps.setInt(1,7);
                ps.setDouble(2,250.5);
                return ps;
            }
        });

    }

}