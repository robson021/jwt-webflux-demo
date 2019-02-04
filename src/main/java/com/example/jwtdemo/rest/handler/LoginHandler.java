package com.example.jwtdemo.rest.handler;

import com.example.jwtdemo.domain.UserRepository;
import com.example.jwtdemo.security.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoginHandler {

    private final UserRepository repository;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public Mono<ServerResponse> logUserIn(ServerRequest request) {
        final String login = request.queryParam("user").orElseThrow();
        final String password = request.queryParam("password").orElseThrow();

        return repository.findUser(login)
                .flatMap(user -> {
                    if (!isPasswordValid(password, user.getPassword())) {
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                    }
                    String jwt = jwtService.sign(user.getId(), login, user.isAdmin());
                    return ServerResponse.ok().syncBody("Bearer " + jwt);
                });
    }

    private boolean isPasswordValid(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
