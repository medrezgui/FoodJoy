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
                .route("route2TableMS",r->r.path("/tables/**")
                        .uri("lb://TableMS"))
                .route("route3GestionCommandeMS",r->r.path("/api/commandes/**")
                        .uri("lb://gestion-commande"))
                .route("route3bGestionCommandeMS",r->r.path("/api/lignes-commande/**")
                        .uri("lb://gestion-commande"))
                .route("route4MenuPlatMS",r->r.path("/Plat/**")
                        .uri("lb://Menu_Plat"))
                .route("route4bMenuPlatMS",r->r.path("/Menu/**")
                        .uri("lb://Menu_Plat"))
                .route("route5Employee",r->r.path("/api/employees/**")
                        .uri("lb://Employee"))
                .route("route6Facture",r->r.path("/api/factures/**")
                        .uri("lb://Facture"))
                .route("route6bFacture",r->r.path("/api/dashboards/**")
                        .uri("lb://Facture"))
                .route("route7Stock",r->r.path("/api/stocks/**")
                        .uri("lb://Gestion-du-stock"))
                .route("route7bStock",r->r.path("/api/ingredients/**")
                        .uri("lb://Gestion-du-stock"))
                .route("route7cStock",r->r.path("/email/**")
                        .uri("lb://Gestion-du-stock"))
             //   .route("route1JOB",r->r.path("/jobs/**")
               //         .uri("lb://le nom de votre ms dans app.properties"))
                .build();
    }
}
