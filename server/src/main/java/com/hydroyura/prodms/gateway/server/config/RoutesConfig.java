package com.hydroyura.prodms.gateway.server.config;

import com.hydroyura.prodms.gateway.server.model.api.ApiRes;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class RoutesConfig {

    @Autowired
    private TestRewriteFunction test;

    @Bean
    RouteLocator getUnitRoute(RouteLocatorBuilder builder) {
        RouteLocator build = builder.routes()
            .route(
                "units-get",
                r -> r
                    .path("/api/v1/units/{number}")
                    .and()
                    .method(HttpMethod.GET)
                    .filters(f -> f.modifyResponseBody(ApiRes.class, ApiRes.class, test))
                    .uri("http://localhost:8081"))
            .build();
        return build;
    }


}
