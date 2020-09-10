package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.CustomerAccountMapper;
import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class CustomerAccountRepository {

    JdbcTemplate jdbcTemplate;
    CustomerAccountMapper customerAccountMapper;

    public CustomerAccountRepository(JdbcTemplate jdbcTemplate, CustomerAccountMapper customerAccountMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerAccountMapper = customerAccountMapper;
    }

    public Integer createCustomerAccount(CustomerAccount customerAccount){
        String createQuery="INSERT INTO CUSTOMER_ACCOUNT (CUSTOMER_ID,MONEY) VALUES (?,?)";
        KeyHolder keyHolder= new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,customerAccount.getCustomerid());
                ps.setBigDecimal(2,customerAccount.getMoney());
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

    public CustomerAccount getCustomerAccountByIdOfCustomer(int idofcustomer){
        try{
            String getCustomerAccountQuery="SELECT * FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID="+idofcustomer;
            return jdbcTemplate.queryForObject(getCustomerAccountQuery,customerAccountMapper);
        }
        catch (Exception e){
            return null;
        }

    }

    public Integer updateCustomerAccountMoney (BigDecimal money, int idofcustomer){
        String updateMoney="UPDATE CUSTOMER_ACCOUNT SET money=? WHERE CUSTOMER_ID=?";
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                                    @Override
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement(updateMoney);
                                        ps.setBigDecimal(1, money);
                                        ps.setInt(2, idofcustomer);
                                        return ps;
                                    }
                                }
            );
            return 1;
        }
        catch (Exception e){
            return 0;
        }

    }

    public List<CustomerAccount> getAllCustomerAccount(){
        String getAllCustomerAccountQuery="SELECT * FROM CUSTOMER_ACCOUNT";
        return jdbcTemplate.query(getAllCustomerAccountQuery,customerAccountMapper);
    }
    /*
    Davat pozor ze metoda vracia -1
     */
    public BigDecimal getMoneyByCustomerId(int customerid){
        try{
            String query="SELECT MONEY FROM CUSTOMER_ACCOUNT WHERE CUSTOMER_ID="+customerid;
            return jdbcTemplate.queryForObject(query,BigDecimal.class);
        }catch (DataAccessException e){
            return null;
        }

    }
}
