package com.example.jwtdemo.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityCtxRepository implements ServerSecurityContextRepository {

    private static final int BEARER_WORD_LENGTH = 7; // token should start with 'Bearer '

    private final AuthManager authManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        return authHeader == null ? Mono.empty() : createSecurityCtx(exchange, authHeader);
    }

    private Mono<SecurityContext> createSecurityCtx(ServerWebExchange exchange, String authHeader) {
        try {
            String token = authHeader.substring(BEARER_WORD_LENGTH);
            return authManager.authenticate(new JwtAuthentication(token)).map(SecurityContextImpl::new);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                String path = exchange.getRequest().getPath().toString();
                log.debug("Error on path: '{}'. Exception type: {}", path, e.getClass().toString());
            }
            log.warn("Failed to authenticate user. Authorization header: {}", authHeader);
            return Mono.empty();
        }
    }
}
