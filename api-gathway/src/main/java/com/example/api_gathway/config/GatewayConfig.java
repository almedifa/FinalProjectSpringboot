package com.example.api_gathway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-route" , r -> r
                        .path("/product/**")
                        .uri("lb://product-service"))
                .route("auth-route", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .build();
    }
}
