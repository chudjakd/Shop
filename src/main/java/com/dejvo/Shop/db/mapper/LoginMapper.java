package com.dejvo.Shop.db.mapper;

import com.dejvo.Shop.model.Login;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class LoginMapper implements RowMapper<Login> {
    @Override
    public Login mapRow(ResultSet resultSet, int i) throws SQLException {
        Login login= new Login();
        login.setName(resultSet.getString("name"));
        login.setPassword(resultSet.getString("password"));
        return login;
    }
}
