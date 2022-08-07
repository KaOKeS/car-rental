package com.dawidpater.project.carrental.config;

import com.dawidpater.project.carrental.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableWebMvc
//@EnableSwagger2
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("^(?!/(error).*$).*$"))
                .build();
    }

    @Bean
    public Car car() {
        return new Car();
    }

    @Bean
    public Rental rental() {
        return new Rental();
    }

    @Bean
    public Feedback feedback() {
        return new Feedback();
    }

    @Bean
    public UserRole role() {
        return new UserRole();
    }

    @Bean
    public RentalUser rentalUser() {
        return new RentalUser();
    }
}

