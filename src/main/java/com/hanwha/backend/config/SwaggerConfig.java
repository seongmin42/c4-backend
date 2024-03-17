package com.hanwha.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI realCartApiInfo() {
        return new OpenAPI()
                .info(new Info().title("C4 backend API")
                        .description("HONE C4 backend application")
                        .version("v0.0.1"));
    }

}
