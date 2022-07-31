package com.dawidpater.project.carrental.config;

import com.dawidpater.project.carrental.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Car car(){
        return new Car();
    }
    @Bean
    public Rental rental(){
        return new Rental();
    }
    @Bean
    public Feedback feedback(){
        return new Feedback();
    }
    @Bean
    public RoleOfUser role(){
        return new RoleOfUser();
    }
    @Bean
    public RentalUser rentalUser(){
        return new RentalUser();
    }
}
