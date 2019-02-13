package com.example.jwtdemo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final DatabaseClient database;

    @Override
    public void run(String... args) throws Exception {
        List<String> statements = Arrays.asList(
                "DROP TABLE IF EXISTS users;",
                "CREATE TABLE users (id SERIAL PRIMARY KEY, login VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, is_admin boolean NOT NULL);"
        );

        for (String s : statements) {
            database.execute()
                    .sql(s)
                    .then()
                    .block();
        }
    }
}
