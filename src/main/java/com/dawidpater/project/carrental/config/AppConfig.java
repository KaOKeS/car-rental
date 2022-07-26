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
    public CarRental carRental(){
        return new CarRental();
    }
    @Bean
    public Rental rental(){
        return new Rental();
    }
    @Bean
    public Review review(){
        return new Review();
    }
    @Bean
    public Role role(){
        return new Role();
    }
    @Bean
    public User user(){
        return new User();
    }
    @Bean
    public UserRental userRental(){
        return new UserRental();
    }
    @Bean
    public WebContent webContent(){
        return new WebContent();
    }
}
