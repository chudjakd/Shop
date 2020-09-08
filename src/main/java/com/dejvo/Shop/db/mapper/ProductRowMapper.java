package com.dejvo.Shop.db.mapper;

import com.dejvo.Shop.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {
        Product product= new Product();
        product.setId(resultSet.getLong("id"));
        product.setDatetime(resultSet.getTimestamp("datetime"));
        product.setSellerId(resultSet.getLong("seller_id"));
        product.setValue(resultSet.getInt("value"));
        product.setName(resultSet.getString("name"));
        product.setInfo(resultSet.getString("info"));
        product.setCount(resultSet.getInt("count"));
        return product;
    }
}
