package com.mlcdev.employeeapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Employee API - Spring Version")
                        .version("2.0")
                        .description("RESTful API to manage employees")
                        .contact(new Contact()
                                .name("Matheus Leite Carneiro")
                                .email("dev.matheuscarneiro@gmail.com")));
    }

}
