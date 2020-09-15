package com.dejvo.Shop;

import com.dejvo.Shop.model.Customer;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySourcesPropertyResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class Test {


    public static void main(String[] args) {
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration("C://Users//David Chudjak//IdeaProjects//Shop//src//main//resources//application.properties");
            properties.setProperty("spring.profiles.active", "jazda");
            properties.save();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}


//    public Test() {
//        RestTemplate restTemplate= new RestTemplate();
//        this.jdbcTemplate = jdbcTemplate;
//        Customer customer= new Customer();
//        String url="SELECT * FROM CUSTOMER WHERE ID=1";
//
//        List<Customer> list=this.jdbcTemplate.query("SELECT * FROM CUSTOMER WHERE ID=1",(resultSet, i) ->
//                new Customer(resultSet.getLong("id")
//                            ,resultSet.getString("name")
//                            ,resultSet.getString("email")
//                             ,resultSet.getString("address")) );
//
//        System.out.println(list.get(0).getEmail()
//                +list.get(0).getAddress()
//                +list.get(0).getName());



