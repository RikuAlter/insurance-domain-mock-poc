package com.insurance_gateway.gateway_service.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.rewritePath;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class GatewayRoutingConfiguration {

    @Bean
    public RouterFunction<ServerResponse> insuranceFetchRoutingConfig(){
        return route("policy_manager")
                .GET("/api/policy-manager/**", http("http://localhost:8090"))
                .before(rewritePath("/api/policy-manager/"+"(?<segment>.*)", "/policy/${segment}"))
                .filter(tokenRelay())
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> insurancePostRoutingConfig(){
        return route("policy_manager")
                .POST("/api/policy-manager/**", http("http://localhost:8090"))
                .before(rewritePath("/api/policy-manager/"+"(?<segment>.*)", "/policy/${segment}"))
                .filter(tokenRelay())
                .build();
    }
}
