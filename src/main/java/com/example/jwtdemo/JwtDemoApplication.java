package com.example.jwtdemo;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwtdemo.rest.handler.LoginHandler;
import com.example.jwtdemo.rest.handler.TestEndpointHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        ReactiveSecurityAutoConfiguration.class,
})
public class JwtDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtDemoApplication.class, args);
    }

    @Bean
    public Algorithm algorithm(@Value("${com.example.security.secret}") String secret) {
        return Algorithm.HMAC512(secret);
    }

    @Bean
    public RouterFunction<ServerResponse> peopleRoutes(LoginHandler loginHandler, TestEndpointHandler testEndpointHandler) {
        return RouterFunctions //
                .route(GET("/login"), loginHandler::logUserIn)
                .andRoute(GET("/user-endpoint"), testEndpointHandler::userEndpoint)
                .andRoute(GET("/admin/admin-endpoint"), testEndpointHandler::adminEndpoint);
    }

}