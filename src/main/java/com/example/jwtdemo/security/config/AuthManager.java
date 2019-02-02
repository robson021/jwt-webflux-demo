package com.example.jwtdemo.security.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        try {
            return doAuthenticate(authentication);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Mono.empty();
        }
    }

    private Mono<Authentication> doAuthenticate(Authentication authentication) {
        String jwt = authentication.getCredentials().toString();
        DecodedJWT decodedJWT = jwtService.verifyToken(jwt);

        long id = decodedJWT.getClaim(JwtService.ID).asLong();
        String username = decodedJWT.getClaim(JwtService.USERNAME).asString();
        boolean isAdmin = decodedJWT.getClaim(JwtService.IS_ADMIN).asBoolean();

        return Mono.just(new JwtAuthentication(id, username, isAdmin, true, jwt));
    }
}
