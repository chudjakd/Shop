package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Component
public class CustomerAccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer createCustomerAccount(CustomerAccount customerAccount){
        String createQuery="INSERT INTO CUSTOMER_ACCOUNT (CUSTOMER_ID,MONEY) VALUES (?,?)";
        KeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(createQuery);
                ps.setInt(1,customerAccount.getCustomerid());
                ps.setDouble(2,customerAccount.getMoney());
                return ps;
            }
        },keyHolder);
        if(keyHolder!=null){
            return keyHolder.getKey().intValue();
        }
        else {
            return null;
        }
    }
}
