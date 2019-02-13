package com.example.jwtdemo.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         AuthManager authManager,
                                                         SecurityCtxRepository securityCtxRepository) {
        return http.csrf().disable()
                .authenticationManager(authManager)
                .securityContextRepository(securityCtxRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.GET, "/login**", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.ico").permitAll()
                .pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//              .anyExchange().hasAuthority("ROLE_USER")
                .anyExchange().authenticated()
                .and().build();
    }
}
