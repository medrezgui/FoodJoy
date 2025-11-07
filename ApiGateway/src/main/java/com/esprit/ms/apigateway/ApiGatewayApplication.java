package com.esprit.ms.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
    @Bean
    public RouteLocator getwayRoutes(RouteLocatorBuilder builder)
    {
        return builder.routes()
                .route("route1ReservationMS",r->r.path("/reservations/**")
                        .uri("lb://ReservationMS"))
             //   .route("route1JOB",r->r.path("/jobs/**")
               //         .uri("lb://le nom de votre ms dans app.properties"))
                .build();
    }
}
