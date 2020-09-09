package com.dejvo.Shop.db.repository;

import com.dejvo.Shop.db.mapper.SellerRowMapper;
import com.dejvo.Shop.db.request.UpdateSellerRequest;
import com.dejvo.Shop.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
public class SellerRepository {

    SellerRowMapper sellerRowMapper;
    JdbcTemplate jdbcTemplate;

    @Autowired
    public SellerRepository(SellerRowMapper sellerRowMapper, JdbcTemplate jdbcTemplate) {
        this.sellerRowMapper = sellerRowMapper;
        this.jdbcTemplate = jdbcTemplate;
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
            String updateQuery = "update Seller name =?, email=?,address =? where id = ?";
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

}
