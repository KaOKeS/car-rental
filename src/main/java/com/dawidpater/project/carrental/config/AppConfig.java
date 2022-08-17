package com.dawidpater.project.carrental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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
}

