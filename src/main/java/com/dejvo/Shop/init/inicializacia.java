package com.dejvo.Shop.init;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class inicializacia implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            PropertiesConfiguration properties = new PropertiesConfiguration("C://Users//David Chudjak//IdeaProjects//Shop//src//main//resources//application.properties");
            properties.setProperty("spring.profiles.active", "default");
            properties.save();

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}
