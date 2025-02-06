package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import com.hydroyura.prodms.archive.client.model.res.GetUnitRes;
import com.hydroyura.prodms.files.server.api.res.GetLatestRes;
import com.hydroyura.prodms.gateway.server.model.res.GetUnitDetailedRes;
import jakarta.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@SuppressWarnings("rawtypes")
@Slf4j
public class AggregationGetUnitRewriteFunction implements RewriteFunction<JsonNode, ApiRes> {

    @Value("${microservices.urls.files}")
    private String filesUrl;

    //TODO: replace with bean
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JavaType archiveResponseType = TypeFactory
        .defaultInstance()
        .constructParametricType(ApiRes.class, GetUnitRes.class);

    @PostConstruct
    void init() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    @Override
    public Publisher<ApiRes> apply(ServerWebExchange serverWebExchange, JsonNode responseFromArchive) {
        Boolean isSuccessRequestToArchive = Optional
            .of(serverWebExchange.getResponse())
            .map(ServerHttpResponse::getStatusCode)
            .map(HttpStatusCode::is2xxSuccessful)
            .orElse(Boolean.FALSE);

        String number = extractNumberFromRequest(serverWebExchange);

        if (isSuccessRequestToArchive) {
            Mono.zip(prepareCurrentResponse(responseFromArchive), fetchUrls(number));
        }
        throw new RuntimeException("Need to handle case when got bad result from Archive");


//        return Mono
//            .zip(prepareCurrentResponse(apiRes), getUrls(number))
//            .map(tuple -> aggregate(tuple.getT1(), tuple.getT2()));
        //return Mono.empty();
    }

    //TODO: handle error
    private String extractNumberFromRequest(ServerWebExchange serverWebExchange) {
        return Optional
            .ofNullable(serverWebExchange.getAttribute(ServerWebExchangeUtils.URI_TEMPLATE_VARIABLES_ATTRIBUTE))
            .map(Map.class::cast)
            .map(m -> String.valueOf(m.get("number")))
            .orElseThrow(() -> new RuntimeException("Handle it!"));
    }

    @SneakyThrows
    private Mono<ApiRes<GetUnitRes>> prepareCurrentResponse(JsonNode responseFromArchive) {
        ApiRes<GetUnitRes> castedResponseFromArchive = objectMapper.readValue(
            responseFromArchive.traverse(),
            archiveResponseType
        );

        ApiRes<GetUnitDetailedRes> aggregatedResponse = new ApiRes<>();

//        aggregatedResponse.setId(UUID.fromString(responseFromArchive.t("id").toString()));
//        aggregatedResponse.setTimestamp(Timestamp.valueOf(responseFromArchive.));
        return Mono.empty();
    }

//    private UUID id;
//    private Timestamp timestamp;
//    private T data;
//    private Collection<String> warnings = new ArrayList();
//    private Collection<String> errors = new ArrayList();

    private Mono<ApiRes<GetLatestRes>> fetchUrls(String number) {
        return WebClient.builder()
            .baseUrl(filesUrl)
            .build()
            .method(HttpMethod.GET)
            .uri("/api/v1/drawings/{number}", number)
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<ApiRes<GetLatestRes>>() {});
    }

    private ApiRes<GetUnitDetailedRes> aggregate(ApiRes<GetUnitRes> archiveRes, ApiRes<GetLatestRes> filesRes) {

        return new ApiRes<>();
    }





}
