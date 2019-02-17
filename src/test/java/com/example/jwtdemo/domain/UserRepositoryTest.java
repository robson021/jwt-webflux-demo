package com.example.jwtdemo.domain;

import com.example.jwtdemo.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

public class UserRepositoryTest extends BaseTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup() {
        databaseClient.execute()
                .sql("DELETE FROM users;")
                .then()
                .block();
    }

    @Test
    public void insertUser() {
        final User user = new User(null, "test-user", "password", false);
        final Integer expected = 1;
        userRepository.insertUser(user)
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .assertNext(expected::equals) // TODO: check assertion
                .verifyComplete();
    }

    @Test
    public void findUser() {
    }

    @Test
    public void getAllUsers() {
    }
}