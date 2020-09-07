package com.dejvo.Shop.service;

import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Configuration
public class PrintName {
    @Value("${name}")
    public String name;

    @Value("${pica}")
    private String pica;


    public void nameWithSurname(){
        System.out.println(name);
    }

    @Bean
    public void sayPica(){

        System.out.println(pica);
    }
}
