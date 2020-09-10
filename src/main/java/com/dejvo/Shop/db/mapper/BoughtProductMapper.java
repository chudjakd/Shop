package com.dejvo.Shop.db.mapper;

import com.dejvo.Shop.model.BoughtProduct;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class BoughtProductMapper implements RowMapper<BoughtProduct> {
    @Override
    public BoughtProduct mapRow(ResultSet resultSet, int i) throws SQLException {
        BoughtProduct boughtProduct=new BoughtProduct();
        boughtProduct.setCustomerid(resultSet.getInt("customer_id"));
        boughtProduct.setProductid(resultSet.getInt("product_id"));
        boughtProduct.setCount(resultSet.getInt("value"));
        boughtProduct.setBoughtat(resultSet.getTimestamp("bought_at"));
        return boughtProduct;
    }
}
