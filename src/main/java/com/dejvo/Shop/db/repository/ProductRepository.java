package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.ProductRowMapper;
import com.dejvo.Shop.db.request.ProductDiscountUpdate;
import com.dejvo.Shop.db.request.UpdateProductRequest;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.helpmodels.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.Instant;
import java.util.List;


@Component
public class ProductRepository {

    ProductRowMapper productRowMapper;
    JdbcTemplate jdbcTemplate;
    ProductCategory productCategory;
    @Autowired
    public ProductRepository(ProductRowMapper productRowMapper, JdbcTemplate jdbcTemplate, ProductCategory productCategory) {
        this.productRowMapper = productRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.productCategory = productCategory;
    }

    public Integer createProduct(Product product){

        String query="INSERT INTO PRODUCT (seller_id,name,info,value,count,created_at,category) VALUES (?,?,?,?,?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        try{
            jdbcTemplate.update(connection -> {
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
                ps.setString(7,productCategory.getProductCategoryIfExistIfNotReturnOther(product.getCategory()));
                return ps;
            },keyHolder);

                return keyHolder.getKey().intValue();

        }catch (DataAccessException e){
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
            String updateQuery = "update Product set name = ?, info =?, value=?, count =?, category=? where id = ?";
            jdbcTemplate.update(updateQuery,request.getName()
                    ,request.getInfo()
                    ,request.getValue()
                    ,request.getCount()
                    ,productCategory.getProductCategoryIfExistIfNotReturnOther(request.getCategory())
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
            String query="INSERT INTO PRODUCT (seller_id,name,info,value,count,created_at,category) VALUES (?,?,?,?,?,?,?)";

            jdbcTemplate.update(connection -> {
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
                    ps.setString(7,productCategory.getProductCategoryIfExistIfNotReturnOther(product.getCategory()));
                    counter++;
                    if(counter==products.size()){

                    }else{
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
                return ps;
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
            String url="UPDATE PRODUCT SET name=?, info=?, value=?, count=?, category=? where id=?";
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps= connection.prepareStatement(url);
                    for(UpdateProductRequest request:requests){
                                 ps.setString(1,request.getName());
                                ps.setString(2,request.getInfo());
                                ps.setBigDecimal(3,request.getValue());
                                ps.setInt(4,request.getCount());
                                ps.setString(5,productCategory.getProductCategoryIfExistIfNotReturnOther(request.getCategory()));
                                ps.setInt(6,request.getId());
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

    public Integer editProductsDiscountThem(ProductDiscountUpdate productDiscountUpdate){

    List<Integer> allIdOfProducts=getAllIdOfProducts();
    List<Integer> allIdOfProductsWhichWannaBeUpdated=productDiscountUpdate.getIdofproducts();

    for(Integer id:allIdOfProductsWhichWannaBeUpdated){
        if(!allIdOfProducts.contains(id)){
            return null;
        }
        try{
               jdbcTemplate.update(new PreparedStatementCreator() {
                   @Override
                   public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                       String urlUpdate = "UPDATE PRODUCT SET value=? WHERE ID=?";
                       PreparedStatement ps= connection.prepareStatement(urlUpdate);

                       for(Integer idofdiscountedproduct:allIdOfProductsWhichWannaBeUpdated) {

                           String urlSelect = "SELECT VALUE FROM PRODUCT WHERE ID=" + idofdiscountedproduct;
                           BigDecimal oldvalue = jdbcTemplate.queryForObject(urlSelect, BigDecimal.class).setScale(2);
                           BigDecimal discountedvalue = oldvalue.subtract(oldvalue.divide(BigDecimal.valueOf(100))
                                   .multiply(productDiscountUpdate.getPercentofdiscount())).setScale(2, RoundingMode.HALF_UP);

                           ps.setBigDecimal(1, discountedvalue);
                           ps.setInt(2, idofdiscountedproduct);
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
        return null;
    }

    public List<Integer> getAllIdOfProducts(){
        String url="SELECT ID FROM PRODUCT";
        try{
            return jdbcTemplate.queryForList(url,Integer.class);
        }catch (Exception e){
            return null;
        }
    }

    public List<Product> getAllProductsByCategory(String category){
        if(category.equals("foodstuff")){
            category="Food stuff";
        }else {
            if(Character.isLetter(category.charAt(0))&&Character.isLowerCase(category.charAt(0))){
                StringBuilder newcategory= new StringBuilder(category);
                newcategory.setCharAt(0,category.toUpperCase().charAt(0));
                category=newcategory.toString();
            }
        }
        try{
            String url= "SELECT * FROM PRODUCT WHERE CATEGORY="+"'"+category+"'";
            return jdbcTemplate.query(url,productRowMapper);
        }catch (Exception e){
            return null;
        }
    }

}
