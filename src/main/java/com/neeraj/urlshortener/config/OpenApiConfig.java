package com.neeraj.urlshortener.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    public OpenAPI urlShortenerOpenAPI(){
        return new OpenAPI()
               .info(new Info()
                         .title("URL Shortener API")
                         .description("Production-grade URL Shortener built with Spring Boot, Redis & MySQL")
                         .version("v1.0"));
    }

}
