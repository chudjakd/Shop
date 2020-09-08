package com.dejvo.Shop.db.mapper;

import com.dejvo.Shop.model.Seller;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class SellerRowMapper implements RowMapper<Seller> {
    @Override
    public Seller mapRow(ResultSet resultSet, int i) throws SQLException {
        Seller seller= new Seller();
        seller.setId(resultSet.getLong("id"));
        seller.setName(resultSet.getString("name"));
        seller.setEmail(resultSet.getString("email"));
        seller.setAddress(resultSet.getString("address"));
        return seller;
    }
}
