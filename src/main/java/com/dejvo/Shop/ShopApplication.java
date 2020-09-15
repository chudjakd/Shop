package com.dejvo.Shop;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext kontext;
		kontext=SpringApplication.run(ShopApplication.class, args);
	}

}
