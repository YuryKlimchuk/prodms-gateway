package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import com.hydroyura.prodms.files.server.api.res.GetLatestRes;
import com.hydroyura.prodms.gateway.server.model.res.GetUnitDetailedRes;
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
public class TestRewriteFunction implements RewriteFunction<ApiRes<GetUnitRes>, ApiRes<GetUnitDetailedRes>> {

    //@Value("${microservices.urls.files}")
    private String filesUrl = "http://localhost:8085";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JavaType mapType = TypeFactory
        .defaultInstance()
        .constructMapType(Map.class, String.class, Object.class);

    @SneakyThrows
    @Override
    public Publisher<ApiRes<?>> apply(ServerWebExchange serverWebExchange, ApiRes<GetUnitRes> apiRes) {
        String number = "test";
        return Mono.zip(prepareCurrentResponse(apiRes), getUrls(number)).map(turple -> aggregate(turple.getT1(), turple.getT2()));
    }

    @SneakyThrows
    private Mono<ApiRes<GetUnitRes>> prepareCurrentResponse(ApiRes<?> apiRes) {
        //Map<String, Object> data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), mapType);
        GetUnitRes data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), GetUnitRes.class);
        return Mono.empty();
    }

    private Mono<ApiRes<GetLatestRes>> getUrls(String number) {
        /*
        Mono<ApiRes> resMono =  WebClient.builder()
            .baseUrl(filesUrl)
            .build()
            .method(HttpMethod.GET)
            .uri("/api/v1/units/{number}", data.get("number").toString())
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(ApiRes.class);
         */
        return Mono.empty();
    }

    private ApiRes<?> aggregate(ApiRes<GetUnitRes> archiveRes, ApiRes<GetLatestRes> filesRes) {
        return new ApiRes<>();
    }





}
