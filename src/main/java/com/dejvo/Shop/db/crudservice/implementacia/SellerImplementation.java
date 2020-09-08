package com.dejvo.Shop.db.crudservice.implementacia;

import com.dejvo.Shop.db.crudservice.SellerInterface;
import com.dejvo.Shop.model.Customer;
import com.dejvo.Shop.model.Seller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SellerImplementation implements SellerInterface {

    JdbcTemplate jdbcTemplate;

    public SellerImplementation(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createSeller(Seller seller) {
        String query="INSERT INTO SELLER (name,email,address) VALUES (?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps= connection.prepareStatement(query);
                ps.setString(1,seller.getName());
                ps.setString(2,seller.getEmail());
                ps.setString(3,seller.getAddress());
                return ps;
            }
        });
    }

    @Override
    public List<Seller> readSellerById(Long id) {

        Seller seller= new Seller();
        String query="SELECT * FROM SELLER WHERE ID="+id.toString();

        List<Seller> sellers=jdbcTemplate.query(query,(resultSet, i) -> {
            seller.setId(resultSet.getLong(1));
            seller.setName(resultSet.getString(2));
            seller.setEmail(resultSet.getString(3));
            seller.setAddress(resultSet.getString(4));
            return seller;
        } );

        return sellers;
    }

    @Override
    public List<Seller> readAllSellers() {

        Seller seller= new Seller();
        String query="SELECT id,name,email,address FROM seller";

        List<Seller> sellers=jdbcTemplate.query(query,(resultSet, i) -> {
            seller.setId(resultSet.getLong(1));
            seller.setName(resultSet.getString(2));
            seller.setEmail(resultSet.getString(3));
            seller.setAddress(resultSet.getString(4));
            return seller;
        } );

        return sellers;
    }

    @Override
    public int updateSeller(Seller seller, Long id) {

        String updateQuery = "update Seller set id = ?, name =?, email=?,address =? where id = ?";

        jdbcTemplate.update(updateQuery,seller.getId()
                ,seller.getName()
                ,seller.getEmail()
                ,seller.getAddress()
                ,id);
        return 1;
    }

    @Override
    public void deleteSeller(Long id) {
        String deleteQuery= "DELETE FROM SELLER WHERE ID="+id.toString();
        jdbcTemplate.update(deleteQuery);
    }
}
