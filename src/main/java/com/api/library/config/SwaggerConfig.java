package com.api.library.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Library API")
                        .description("Study project of a Library API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Anderson")
                                .email("https://github.com/andersonmag")
                                .url("andersondel.dev@gmail.com")));
    }
}
