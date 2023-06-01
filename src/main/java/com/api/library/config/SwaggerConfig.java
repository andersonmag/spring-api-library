package com.api.library.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Spring Library API")
                .packagesToScan("com.api.library")
                .build();
    }

    @Bean
    public OpenAPI apiInfo(ServletContext context) {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Library API")
                        .description("Study project of a Library API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Anderson")
                                .email("andersondel.dev@gmail.com")
                                .url("https://github.com/andersonmag")))
                .components(new Components()
                        .addSecuritySchemes("token-authotization",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .servers(List.of(new Server().url(context.getContextPath())));
    }
}
