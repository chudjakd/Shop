package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.ProductRowMapper;
import com.dejvo.Shop.db.mapper.SellerRowMapper;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.ConnectionProxy;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductRepository {

    ProductRowMapper productRowMapper;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(ProductRowMapper productRowMapper, JdbcTemplate jdbcTemplate) {
        this.productRowMapper = productRowMapper;
        this.jdbcTemplate = jdbcTemplate;

    }

    public Integer createProduct(Product product){

        String query="INSERT INTO PRODUCT (seller_id,name,info,value,count,created_at) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,product.getSellerId());
                ps.setString(2,product.getName());
                ps.setString(3,product.getInfo());
                ps.setBigDecimal(4,product.getValue());
                ps.setInt(5,product.getCount());
                if(product.getDatetime()==null){
                    ps.setTimestamp(6, Timestamp.from(Instant.now()));
                }else{
                    ps.setTimestamp(6,product.getDatetime());
                }
                return ps;
            }
        },keyHolder);

        if(keyHolder!=null){
            return keyHolder.getKey().intValue();
        }
        else{
            return null;
        }
    }

    public Product readProductById(int id) {
        try{
            String query="SELECT * FROM PRODUCT WHERE ID="+id;
            return jdbcTemplate.queryForObject(query,productRowMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<Product> readAllProducts(){

        String query="SELECT * FROM product";

        return jdbcTemplate.query(query,productRowMapper);

    }

    public int updateProduct(int id, UpdateProductRequest request){
        try{
            String updateQuery = "update Product set name = ?, info =?, value=?, count =? where id = ?";
            jdbcTemplate.update(updateQuery,request.getName()
                    ,request.getInfo()
                    ,request.getValue()
                    ,request.getCount()
                    ,id);
            return 1;
        }
        catch (DataAccessException e){
            return 0;
        }
    }

    public int deleteProduct(int id){
        try{
            String deleteQuery= "DELETE FROM product WHERE ID="+id;
            jdbcTemplate.update(deleteQuery);
            return 1;
        }
        catch (DataAccessException e){
            System.out.println("nepodarilo sa");
            return 0;
        }
    }

    public Integer updateProductCount(int productid, int newcount){
        try{
            String url="UPDATE PRODUCT SET COUNT=? WHERE ID=?";
            jdbcTemplate.update(url,newcount,productid);
            return 1;
        }
        catch (DataAccessException e){
            return null;
        }
    }

    public List<Product> getAllProductsBySellerId(int sellerid){
        try{
            String url="SELECT * FROM PRODUCT WHERE SELLER_ID="+sellerid;
            return jdbcTemplate.query(url,productRowMapper);
        }catch (Exception e){
            return null;
        }
    }

    public Integer createMoreProduct(List<Product> products){
        try{
            String query="INSERT INTO PRODUCT (seller_id,name,info,value,count,created_at) VALUES (?,?,?,?,?,?)";

            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps= connection.prepareStatement(query);
                    int counter=0;
                    for(Product product:products) {
                        ps.setLong(1, product.getSellerId());
                        ps.setString(2, product.getName());
                        ps.setString(3, product.getInfo());
                        ps.setBigDecimal(4, product.getValue());
                        ps.setInt(5, product.getCount());
                        if (product.getDatetime() == null) {
                            ps.setTimestamp(6, Timestamp.from(Instant.now()));
                        } else {
                            ps.setTimestamp(6, product.getDatetime());
                        }
                        counter++;
                        if(counter==products.size()){

                        }else{
                            ps.addBatch();
                        }
                    }
                    ps.executeBatch();
                    return ps;
                }
            });
            return 1;
        } catch (Exception e){
            return null;
        }
    }

    public Integer updateMoreProducts(List<UpdateProductRequest> requests){
       List<Integer> allidofproducts=getAllIdOfProducts();
       for(UpdateProductRequest request:requests){
           if(allidofproducts.contains(request.getId())){

           }else{
               return null;
           }
       }
        try{
            String url="UPDATE PRODUCT SET name=?, info=?, value=?, count=? where id=?";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps= connection.prepareStatement(url);
                    for(UpdateProductRequest request:requests){
                                 ps.setString(1,request.getName());
                                ps.setString(2,request.getInfo());
                                ps.setBigDecimal(3,request.getValue());
                                ps.setInt(4,request.getCount());
                                ps.setInt(5,request.getId());
                                ps.addBatch();
                    }
                    ps.executeBatch();
                    return ps;
                }
            });
            return 1;
        }catch (Exception e){
            return null;
        }
    }

    public List<Integer> getAllIdOfProducts(){
        String url="SELECT ID FROM PRODUCT";
        try{
            return jdbcTemplate.queryForList(url,Integer.class);
        }catch (Exception e){
            return null;
        }
    }

}
