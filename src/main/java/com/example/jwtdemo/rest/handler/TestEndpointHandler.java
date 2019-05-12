package com.example.jwtdemo.rest.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
@Slf4j
public class TestEndpointHandler {

    public Mono<ServerResponse> userEndpoint(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> {
                    log.debug("User endpoint hit by: {}", principal);
                    return getPrincipalResponse(principal);
                });
    }

    public Mono<ServerResponse> adminEndpoint(ServerRequest request) {
        return request.principal()
                .flatMap(principal -> {
                    log.debug("Admin endpoint hit by: {}", principal);
                    return getPrincipalResponse(principal);
                });
    }

    private Mono<ServerResponse> getPrincipalResponse(Principal principal) {
        return ServerResponse.ok().syncBody(principal.getName());
    }
}
