package com.example.jwtdemo;

import com.example.jwtdemo.domain.User;
import com.example.jwtdemo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("dockerDB")
@Slf4j
@RequiredArgsConstructor
public class DockerPostgresAppRunner implements CommandLineRunner {

    private final DatabaseClient database;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        List<String> statements = Arrays.asList(
                "DROP TABLE IF EXISTS users;",
                "CREATE TABLE users (id SERIAL PRIMARY KEY, login VARCHAR(100) UNIQUE NOT NULL, password VARCHAR(100) NOT NULL, is_admin boolean NOT NULL)"
        );

        for (String s : statements) {
            database.execute()
                    .sql(s)
                    .then()
                    .block();
        }

        String encodedPassword = encoder.encode("password");

        userRepository.insertUser(new User(1, "admin", encodedPassword, true)).subscribe();
        userRepository.insertUser(new User(2, "user", encodedPassword, false)).subscribe();

        Thread.sleep(100); // insert is async, wait before select

        log.info("Users in db:");
        userRepository.getAllUsers().subscribe(user -> log.info(user.toString()));
    }
}
