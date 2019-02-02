package com.example.jwtdemo.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthManager authenticationManager;

    private final SecurityCtxRepository securityContextRepository;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.GET, "/login**", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.ico").permitAll()
                .pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
//              .anyExchange().hasAuthority("ROLE_USER")
                .anyExchange().authenticated()
                .and().build();
    }
}
