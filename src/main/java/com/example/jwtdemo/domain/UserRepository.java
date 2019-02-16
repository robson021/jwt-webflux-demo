package com.example.jwtdemo.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository {

    private final DatabaseClient database;

    public Mono<Void> insertUser(User user) {
        return database.insert()
                .into(User.class)
                .table("users")
                .using(user)
                .then();
    }

    public Mono<User> findUser(String login) {
        return database.execute()
                .sql("SELECT * FROM users AS u WHERE u.login = $1")
                .bind("$1", login)
                .as(User.class)
                .fetch()
                .one();
    }

    public Flux<User> getAllUsers() {
        return database.select()
                .from("users")
                .as(User.class)
                .fetch()
                .all();
    }
}
