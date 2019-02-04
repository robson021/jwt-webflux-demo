package com.example.jwtdemo.domain;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, User> userMap;

    public UserRepository(PasswordEncoder encoder) {
        String userLogin = "user";
        String adminLogin = "admin";
        String encodedPassword = encoder.encode("password");

        userMap = Map.of(
                userLogin, new User(1, userLogin, encodedPassword, false),
                adminLogin, new User(2, adminLogin, encodedPassword, true)
        );
    }

    public Mono<User> findUser(String username) {
        User user = userMap.get(username);
        return Mono.justOrEmpty(user);
    }
}
