package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
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

    //@Value("${microservices.urls.files}")
    private String filesUrl = "http://localhost:8085";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JavaType mapType = TypeFactory
        .defaultInstance()
        .constructMapType(Map.class, String.class, Object.class);

    @SneakyThrows
    @Override
    public Publisher<ApiRes> apply(ServerWebExchange serverWebExchange, ApiRes apiRes) {
        Map<String, Object> data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), mapType);

        Mono<ApiRes> resMono =  WebClient.builder()
            .baseUrl(filesUrl)
            .build()
            .method(HttpMethod.GET)
            .uri("/api/v1/units/{number}", data.get("number").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApiRes.class);

        //resMono.subscribe(System.out::println);


        return Mono.just(apiRes);
    }

    private Mono<ApiRes<GetUnitRes>> getCurrentResponse() {
        return Mono.empty();
    }

    private Mono<ApiRes<Get>>


}
