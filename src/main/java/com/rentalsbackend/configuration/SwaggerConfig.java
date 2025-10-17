package com.rentalsbackend.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Slf4j
@Configuration
@EnableWebSecurity
public class SwaggerConfig {


    /**
     * Configures the OpenAPI object.
     *
     * This method configures the OpenAPI object to provide information about the API
     * and to define the security scheme used by the API.
     *
     * @return the configured OpenAPI object.
     */
    @Bean
    public OpenAPI rentalPortalOpenAPI() {
        log.info("### SWAGGER CONFIGURATION ###");
        Server devServer = new Server().description("Server URL in Development environment");

        Info info = new Info()
                .title("Rental Portal API")
                .version("1.0")
                .description("Documentation for Rental Portal API to manage rentals properties and users in Chatop company");

        Components components = new Components()
                .addSecuritySchemes("bearer-key",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                );

        return new OpenAPI().info(info).servers(List.of(devServer)).components(components);
    }
}