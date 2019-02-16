package com.example.jwtdemo.config.db;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PreDestroy;

@Configuration
@Profile("dockerDB")
@RequiredArgsConstructor
public class PostgresqlConfig extends AbstractR2dbcConfiguration {

    private final PostgreSQLContainer postgres;

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
