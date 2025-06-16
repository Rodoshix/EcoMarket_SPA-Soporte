package com.ecomarket.soporte.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API Soporte - EcoMarket SPA")
                    .version("1.0")
                    .description("Microservicio para gestión de solicitudes de soporte en EcoMarket SPA")
                    .contact(new Contact()
                        .name("Equipo EcoMarket")
                        .email("soporte@ecomarket.cl")
                        .url("https://ecomarket.cl")))
                .servers(List.of(new Server().url("http://localhost:8087").description("Servidor Local")));
    }

    @Bean
    public GroupedOpenApi soporteApi() {
        return GroupedOpenApi.builder()
                .group("soporte")
                .packagesToScan("com.ecomarket.soporte.controller")
                .build();
    }
}