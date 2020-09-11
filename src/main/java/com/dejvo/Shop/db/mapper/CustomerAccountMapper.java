package com.dejvo.Shop.db.mapper;

import com.dejvo.Shop.model.CustomerAccount;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class CustomerAccountMapper implements RowMapper<CustomerAccount> {
    @Override
    public CustomerAccount mapRow(ResultSet resultSet, int i) throws SQLException {
        CustomerAccount customerAccount= new CustomerAccount();
        customerAccount.setId(resultSet.getInt("id"));
        customerAccount.setCustomerid(resultSet.getInt("customer_id"));
        customerAccount.setMoney(resultSet.getBigDecimal("money"));
        return customerAccount;
    }
}
