package com.dawidpater.project.carrental.config;

import com.dawidpater.project.carrental.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@EnableSwagger2
public class AppConfig {

    @Bean
    public Docket swaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("^(?!/(error).*$).*$"))
                .build();
    }
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
