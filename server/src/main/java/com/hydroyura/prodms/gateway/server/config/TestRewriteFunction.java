package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import java.util.Map;
import lombok.SneakyThrows;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TestRewriteFunction implements RewriteFunction<ApiRes, ApiRes> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JavaType mapType = TypeFactory
        .defaultInstance()
        .constructMapType(Map.class, String.class, Object.class);

    @SneakyThrows
    @Override
    public Publisher<ApiRes> apply(ServerWebExchange serverWebExchange, ApiRes apiRes) {
        var data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), mapType);


        return Mono.just(apiRes);
    }


}
