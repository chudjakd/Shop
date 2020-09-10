package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.BoughtProductMapper;
import com.dejvo.Shop.model.BoughtProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class BoughtProductRepository {

    JdbcTemplate jdbcTemplate;

    BoughtProductMapper boughtProductMapper;
    @Autowired
    public BoughtProductRepository(JdbcTemplate jdbcTemplate, BoughtProductMapper boughtProductMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.boughtProductMapper = boughtProductMapper;
    }

    public Integer createBoughtProduct(BoughtProduct boughtProduct){
        try{
            String query="INSERT INTO BOUGHT_PRODUCT (customer_id,product_id,count,bought_at) VALUES (?,?,?,?)";
            jdbcTemplate.update(query,boughtProduct.getCustomerid(),boughtProduct.getProductid(),boughtProduct.getCount(),boughtProduct.getBoughtat());
            return 1;
        }
        catch (DataAccessException e){
            return null;
        }
    }

    public Integer oldCreateBoughtProduct(BoughtProduct boughtProduct){
        try{
            String query="INSERT INTO BOUGHT_PRODUCT (customer_id,product_id,count,bought_at) VALUES (?,?,?,?)";
            KeyHolder keyHolder= new GeneratedKeyHolder();
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1,boughtProduct.getCustomerid());
                    ps.setInt(2,boughtProduct.getProductid());
                    ps.setInt(3,boughtProduct.getCount());
                    ps.setTimestamp(4,boughtProduct.getBoughtat());
                    return ps;
                }
            },keyHolder);
            if(keyHolder!=null){
                return keyHolder.getKey().intValue();
            }
            else return 0;
        }
        catch (DataAccessException e){
            return null;
        }
    }

    public BoughtProduct selectBoughtProductByCustomerIdAndProductId(int customerid,int productid){
        try{
            String query="SELECT * FROM BOUGHT_PRODUCT WHERE CUSTOMER_ID="+customerid+" AND PRODUCT_ID="+productid;
            return jdbcTemplate.queryForObject(query,boughtProductMapper);
        }
        catch (DataAccessException e){
            return null;
        }
    }
    public Integer updateValueOfBoughtProduct(BoughtProduct boughtProduct, int count){
        if(boughtProduct!=null){
        try{
            String query="UPDATE BOUGHT_PRODUCT SET COUNT=? WHERE CUSTOMER_ID=? AND PRODUCT_ID=?";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps= connection.prepareStatement(query);
                    ps.setInt(1,count);
                    ps.setInt(2,boughtProduct.getCustomerid());
                    ps.setInt(3,boughtProduct.getProductid());
                    return ps;
                }
            });
            return 1;
        }
        catch (DataAccessException e){
            return null;
        }
        }
        else{
            return null;
        }
    }

    public List<BoughtProduct> getAllProductByCustomerId(int customerid){
        try{
            String query="SELECT * FROM bought_product WHERE customer_id="+customerid;
            List<BoughtProduct> allboughtproduct=jdbcTemplate.query(query,boughtProductMapper);
            return allboughtproduct;
        }
        catch (DataAccessException e){
            return null;
        }
    }

}
