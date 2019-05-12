package com.example.jwtdemo;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = JwtDemoApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {

    private static final String USERNAME = "admin";

    private static String jwt;

    @Autowired
    private ApplicationContext appCtx;

    private WebTestClient webClient;

    @Before
    public void setup() {
        this.webClient = WebTestClient.bindToApplicationContext(appCtx)
                .build();
    }

    @Test
    public void _1_logUserIn() {
        byte[] response = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/login")
                        .queryParam("user", USERNAME)
                        .queryParam("password", "password")
                        .build())
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .returnResult()
                .getResponseBody();

        jwt = new String(response);
    }

    @Test
    public void _2_hitAdminEndpoint() {
        validateEndpointHit("/admin/admin-endpoint");
    }

    @Test
    public void _3_hitUserEndpoint() {
        validateEndpointHit("/user-endpoint");
    }

    private void validateEndpointHit(String uri) {
        webClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, jwt)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(String.class)
                .isEqualTo(USERNAME);
    }
}
