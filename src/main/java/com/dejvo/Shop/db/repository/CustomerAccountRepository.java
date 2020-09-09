package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.CustomerAccountMapper;
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
import java.util.List;

@Component
public class CustomerAccountRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CustomerAccountMapper customerAccountMapper;

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

    public CustomerAccount getCustomerAccountById(int idofcustomer){
        try{
            String getCustomerAccountQuery="SELECT * FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID="+idofcustomer;
            return jdbcTemplate.queryForObject(getCustomerAccountQuery,customerAccountMapper);
        }
        catch (Exception e){
            return null;
        }

    }

    public Integer updateCustomerAccountMoney (double money, int idofcustomer){
        String updateMoney="UPDATE CUSTOMER_ACCOUNT SET money=? WHERE ID=?";
        KeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(updateMoney);
                ps.setDouble(1,money);
                ps.setInt(2,idofcustomer);
                return ps;
            }
        },keyHolder);

        return keyHolder.getKey().intValue();
    }

    public List<CustomerAccount> getAllCustomerAccount(){
        String getAllCustomerAccountQuery="SELECT * FROM CUSTOMER_ACCOUNT";
        return jdbcTemplate.query(getAllCustomerAccountQuery,customerAccountMapper);
    }
}
