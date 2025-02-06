package com.hydroyura.prodms.gateway.server;

import static com.hydroyura.prodms.gateway.server.TestUtils.UNIT_NUMBER_1;
import static com.hydroyura.prodms.gateway.server.TestUtils.URI_ARCHIVE_GET_UNIT;
import static org.mockserver.model.HttpRequest.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import com.hydroyura.prodms.archive.client.model.enums.UnitStatus;
import com.hydroyura.prodms.archive.client.model.enums.UnitType;
import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import java.util.Properties;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServerTest {

    static ClientAndServer archiveMockServer;
    static ClientAndServer filesMockServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private Properties properties;

    @Autowired
    private RouteLocator locator;

    @BeforeAll
    static void startServers() {
        archiveMockServer = ClientAndServer.startClientAndServer(8089);
        filesMockServer = ClientAndServer.startClientAndServer(8088);
    }

    @AfterAll
    static void stopServers() {
        archiveMockServer.stop();
        filesMockServer.stop();
    }

    @DynamicPropertySource
    static void tuneProperties(DynamicPropertyRegistry registry) {
        registry.add("microservices.urls.archive", () -> "http://localhost:" + archiveMockServer.getLocalPort());
    }

    @Test
    void contextLoad() {
        // given
        archiveMockServer
            .when(request()
                .withPath(URI_ARCHIVE_GET_UNIT.formatted(UNIT_NUMBER_1))
                .withMethod(HttpMethod.GET.name())
                .withContentType(MediaType.APPLICATION_JSON))
            .respond(HttpResponse
                .response()
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(response())
                .withStatusCode(HttpStatus.OK.value())
            );

        // when
        var response = webTestClient
            .get()
            .uri(URI_ARCHIVE_GET_UNIT.formatted(UNIT_NUMBER_1))
            .header("Content-Type", MediaType.APPLICATION_JSON.toString())
            .exchange()
            .expectBody(JsonNode.class)
            .returnResult();
        // then

        int a = 1;
    }

    @SneakyThrows
    private String response() {
        GetUnitRes getUnitRes = new GetUnitRes();
        getUnitRes.setNumber(UNIT_NUMBER_1);
        getUnitRes.setVersion(1);
        getUnitRes.setType(UnitType.PART);
        getUnitRes.setStatus(UnitStatus.DESIGN);

        ApiRes<GetUnitRes> res = new ApiRes<>();
        res.setData(getUnitRes);
        res.setId(UUID.randomUUID());

        return objectMapper.writeValueAsString(res);
    }

}