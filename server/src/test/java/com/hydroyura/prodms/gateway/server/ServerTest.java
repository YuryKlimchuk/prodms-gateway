package com.hydroyura.prodms.gateway.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockserver.model.HttpRequest.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTest {

    static ClientAndServer mockServer;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeAll
    static void startServer() {
        mockServer = ClientAndServer.startClientAndServer(8081);
    }

    @AfterAll
    static void afterAll() {
        mockServer.stop();
    }

    @Test
    void contextLoad() {
        // given
        mockServer
            .when(request().withPath("/api/v1/units").withMethod("POST"))
            .respond(HttpResponse.response("the best response"));

        // when
        webTestClient
            .post()
            .uri("/api/v1/units")
            .exchange()
            .expectStatus()
            .isOk();
        // then

    }

}