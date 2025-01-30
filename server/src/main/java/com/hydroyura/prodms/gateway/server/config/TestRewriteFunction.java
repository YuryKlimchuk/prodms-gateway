package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import java.util.Map;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TestRewriteFunction implements RewriteFunction<ApiRes, ApiRes> {

    @Value("${microservices.urls.files}")
    private String filesUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JavaType mapType = TypeFactory
        .defaultInstance()
        .constructMapType(Map.class, String.class, Object.class);

    @SneakyThrows
    @Override
    public Publisher<ApiRes> apply(ServerWebExchange serverWebExchange, ApiRes apiRes) {
        Map<String, Object> data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), mapType);

        ResponseEntity<ApiRes> urls =  WebClient.builder()
            .baseUrl(filesUrl)
            .build()
            .method(HttpMethod.GET)
            .uri("/api/v1/units/{number}", data.get("number").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(ApiRes.class)
            .block();




        return Mono.just(apiRes);
    }


}
