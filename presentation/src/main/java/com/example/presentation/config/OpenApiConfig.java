package com.example.presentation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Server URL in Development environment");

        Contact contact = new Contact();
        contact.setEmail("info@example.com");
        contact.setName("API Support");
        contact.setUrl("https://www.example.com");

        License license = new License()
            .name("Apache License, Version 2.0")
            .url("https://www.apache.org/licenses/LICENSE-2.0");

        Info info = new Info()
            .title("Product Management API")
            .version("1.0")
            .contact(contact)
            .description("This API exposes endpoints to manage products.")
            .license(license);

        return new OpenAPI()
            .info(info)
            .servers(List.of(devServer));
    }
}
