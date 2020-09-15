package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.BoughtProductMapper;
import com.dejvo.Shop.db.mapper.ProductRowMapper;
import com.dejvo.Shop.db.mapper.SellerRowMapper;
import com.dejvo.Shop.db.request.UpdateSellerRequest;
import com.dejvo.Shop.model.BoughtProduct;
import com.dejvo.Shop.model.Product;
import com.dejvo.Shop.model.Seller;
import com.dejvo.Shop.model.SellerWithStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Component
public class SellerRepository {

    SellerRowMapper sellerRowMapper;
    JdbcTemplate jdbcTemplate;
    BoughtProductMapper boughtProductMapper;
    ProductRowMapper productRowMapper;

    @Autowired
    public SellerRepository(SellerRowMapper sellerRowMapper, JdbcTemplate jdbcTemplate, BoughtProductMapper boughtProductMapper, ProductRowMapper productRowMapper) {
        this.sellerRowMapper = sellerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.boughtProductMapper = boughtProductMapper;
        this.productRowMapper = productRowMapper;
    }

    public Integer createSeller(Seller seller){

        String query="INSERT INTO SELLER (name,email,address) VALUES (?,?,?)";
        KeyHolder keyHolder=new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,seller.getName());
                ps.setString(2,seller.getEmail());
                ps.setString(3,seller.getAddress());
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

    public Seller readSellerById(Long id) {
        try{
            String query="SELECT * FROM SELLER WHERE ID="+id.toString();
            return jdbcTemplate.queryForObject(query,sellerRowMapper);

        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public List<Seller> readAllSellers(){

        String query="SELECT id,name,email,address FROM seller";

        return jdbcTemplate.query(query,sellerRowMapper);

    }

    public int updateSeller(UpdateSellerRequest request, Long id){
        try{
            String updateQuery = "update Seller set name =?, email=?,address =? where id = ?";
            jdbcTemplate.update(updateQuery
                    ,request.getName()
                    ,request.getEmail()
                    ,request.getAddress()
                    ,id);
            return 1;
        }
        catch (DataAccessException e){
            return 0;
        }
    }

    public void deleteSeller(Long id){
        try{
            String deleteQuery= "DELETE FROM seller WHERE ID="+id.toString();
            jdbcTemplate.update(deleteQuery);
        }
        catch (DataAccessException e){
            System.out.println("nepodarilo sa");
        }
    }

    public List<SellerWithStatistic> getMostPopularSeller(){
        try{
            String url="SELECT product_id, count from bought_product";
            List<Integer> allboughtproduct=jdbcTemplate.query(url, new RowMapper<Integer>() {
                @Override
                public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                    return resultSet.getInt("product_id");
                }
            });
            String urlproduct="SELECT * from product";
            List<Product> allproducts=jdbcTemplate.query(urlproduct,productRowMapper);
            HashMap<Integer,Integer> selleridandcountofsellproducts= new HashMap<>();
            for(Integer value:allboughtproduct){
                int productid=value;
                Product productright=allproducts.stream().filter(product -> product.getId()==productid).findFirst().orElse(null);
                if(productid==0 || productright==null){

                }else{
                    //
                    int sellerid=productright.getSellerId();

                    if(selleridandcountofsellproducts.containsKey(sellerid)){
                        int newvalue=selleridandcountofsellproducts.get(sellerid)+1;
                        selleridandcountofsellproducts.replace(sellerid,newvalue);
                    }else{
                        selleridandcountofsellproducts.put(sellerid,1);

                    }
                }
            }
            String urlseller="SELECT * FROM SELLER";
            List<Seller> allsellers=jdbcTemplate.query(urlseller,sellerRowMapper);
            List<SellerWithStatistic> allsellerswithstatistic= new ArrayList<>();
            for(Seller seller:allsellers){
                if(selleridandcountofsellproducts.containsKey(seller.getId())){
                    SellerWithStatistic sellerWithStatistic= new SellerWithStatistic(seller.getId()
                            ,seller.getName()
                            ,seller.getEmail()
                            ,seller.getAddress()
                            ,selleridandcountofsellproducts.get(seller.getId())
                            ,0);
                    allsellerswithstatistic.add(sellerWithStatistic);
                }else{
                    SellerWithStatistic sellerWithStatistic= new SellerWithStatistic(seller.getId()
                            ,seller.getName()
                            ,seller.getEmail()
                            ,seller.getAddress()
                            ,0
                            ,0);
                    allsellerswithstatistic.add(sellerWithStatistic);
                }
            }

            allsellerswithstatistic.sort(Comparator.comparing(SellerWithStatistic::getCountoftipsofsellproduct).reversed());

            return allsellerswithstatistic;
        }catch (Exception e){return null;}
    }

}
