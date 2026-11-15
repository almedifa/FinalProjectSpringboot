package com.example.api_gathway.config;

import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                // Deny the internal import endpoint before the broader product route matches it.
                .route("product-internal-import-deny", r -> r
                        .path("/product/internal/import", "/product/internal/import/**")
                        .filters(f -> f.setStatus(HttpStatus.FORBIDDEN))
                        .uri("no://op"))
                .route("product-route" , r -> r
                        .path("/product/**")
                        .uri("lb://product-service"))
                .route("auth-route", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .route("scheduler-route", r -> r
                        .path("/scheduler/**")
                        .uri("lb://scheduler-service"))
                .build();
    }
}
