package com.hydroyura.prodms.gateway.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydroyura.prodms.gateway.server.model.api.ApiRes;
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



    @SneakyThrows
    @Override
    public Publisher<ApiRes> apply(ServerWebExchange serverWebExchange, ApiRes apiRes) {
//        Map data = objectMapper.readValue(objectMapper.writeValueAsString(apiRes.getData()), Map.class);
//        data.put("links", Map.of("link", "ahref", "link2", "ahgd"));
//        //apiRes.setData(data);


        return Mono.just(apiRes);
    }


}
