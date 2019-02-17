package com.example.jwtdemo;

import com.example.jwtdemo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Profile("dockerDB")
@Slf4j
@RequiredArgsConstructor
public class DockerPostgresAppRunner implements CommandLineRunner {

    private final DatabaseClient database;

    private final UserRepository userRepository;

    @Override
    @SuppressWarnings("ConstantConditions")
    public void run(String... args) throws Exception {
        URL schemaUrl = getClass().getClassLoader().getResource("db_schema.sql");
        Path path = Paths.get(schemaUrl.toURI());

        String sql = Files.readString(path);
        database.execute()
                .sql(sql)
                .then()
                .block();

        log.info("Users in db:");
        userRepository.getAllUsers().subscribe(user -> log.info(user.toString()));
    }
}
