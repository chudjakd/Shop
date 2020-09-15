package com.dejvo.Shop;

import com.dejvo.Shop.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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


    public Test() {
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


    }
}

//    public static void main(String[] args) {}
//        Timestamp TimeStamp2808=Timestamp.valueOf(LocalDateTime.of(2020,8,28,00,00,00));
//        Timestamp TimeStamp2809=Timestamp.valueOf(LocalDateTime.of(2020,9,28,00,00,00));
//
//        Timestamp TimeStamp2908=Timestamp.valueOf(LocalDateTime.of(2020,07,29,00,00,00));
//        Timestamp TimeStamp0209=Timestamp.valueOf(LocalDateTime.of(2020,9,2,00,00,00));
//        Timestamp TimeStamp0309=Timestamp.valueOf(LocalDateTime.of(2020,9,3,00,00,00));
//
//        List<Timestamp> timestamps= new ArrayList<>();
//        timestamps.add(TimeStamp2908);
//        timestamps.add(TimeStamp0209);
//        timestamps.add(TimeStamp0309);
//
//        for(Timestamp timestamp: timestamps){
//            if((timestamp.after(TimeStamp2808)&&(timestamp.before(TimeStamp2809)))){
//                System.out.println("Yes");
//            }
//        }
//    }

