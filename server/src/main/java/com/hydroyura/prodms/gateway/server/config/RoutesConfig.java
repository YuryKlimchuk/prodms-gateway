package com.hydroyura.prodms.gateway.server.config;

import com.hydroyura.prodms.archive.client.model.api.ApiRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RoutesConfig {

    @Autowired
    private AggregationGetUnitRewriteFunction aggregationGetUnitRewriteFunction;

    @Bean
    RouteLocator getUnitRoute(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(
                "units-get",
                r -> r
                    .path("/api/v1/units/{number}")
                    .and()
                    .method(HttpMethod.GET)
                    .filters(this::buildAggregateFilter)
                    .uri("http://localhost:8081"))
            .build();
    }

    private GatewayFilterSpec buildAggregateFilter(GatewayFilterSpec filterSpec) {
        return filterSpec.modifyResponseBody(
            ApiRes.class,
            ApiRes.class,
            aggregationGetUnitRewriteFunction
        );
    }

}
