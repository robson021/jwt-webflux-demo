package com.example.jwtdemo.config.db;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PreDestroy;

@Configuration
@RequiredArgsConstructor
public class PostgresqlConfig extends AbstractR2dbcConfiguration {

    private final PostgreSQLContainer postgres;

    /*private final DatabaseClient database;

    @EventListener(ApplicationReadyEvent.class)
    public void createSchemaAndInsertData() {
        List<String> statements = Arrays.asList(
                "DROP TABLE IF EXISTS users;",
                "CREATE TABLE users ( id SERIAL PRIMARY KEY, login VARCHAR(100) NOT NULL, password VARCHAR(100) NOT NULL, is_admin boolean NOT NULL);"
        );
        for (String s : statements) {
            database.execute()
                    .sql(s)
                    .then()
                    .block();
        }
    }*/

    @Bean
    @NotNull
    @Override
    public PostgresqlConnectionFactory connectionFactory() {
        postgres.start();

        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
                .host(postgres.getContainerIpAddress())
                .port(postgres.getFirstMappedPort())
                .database(postgres.getDatabaseName())
                .username(postgres.getUsername())
                .password(postgres.getPassword())
                .build();

        return new PostgresqlConnectionFactory(config);
    }

    @PreDestroy
    public void shutdown() {
        postgres.stop();
    }
}
