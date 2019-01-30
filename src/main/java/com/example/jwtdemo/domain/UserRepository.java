package com.example.jwtdemo.domain;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;

@Repository
public class UserRepository {

    private final Map<String, User> userMap;

    public UserRepository() {
        String userLogin = "user";
        String userPassword = "password";

        String adminLogin = "admin";
        String adminPassword = "password";

        userMap = Map.of(
                userLogin, new User(1, userLogin, userPassword, false),
                adminLogin, new User(2, adminLogin, adminPassword, true)
        );
    }

    public Mono<User> findUser(String username) {
        User user = userMap.get(username);
        return Mono.justOrEmpty(user);
    }
}
