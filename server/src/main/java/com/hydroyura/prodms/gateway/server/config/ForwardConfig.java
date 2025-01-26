package com.hydroyura.prodms.gateway.server.config;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class ForwardConfig {

    //@Bean
    RouterFunction<ServerResponse> test() {
        return route("simple_route").GET("/api/v1/units/{number}", http("http://localhost:8081")).build();
    }

    @Bean
    RouterFunction<ServerResponse> test1() {
        return route("simple_route").POST("/api/v1/units", http("http://localhost:8081")).build();
    }

    @Bean
    RouterFunction<ServerResponse> test2() {
        return new CustomRoute();
    }


    class CustomRoute implements RouterFunction<ServerResponse> {

        HandlerFunction<ServerResponse> handlerFunction = new CustomHandler();

        @Override
        public Optional<HandlerFunction<ServerResponse>> route(ServerRequest request) {
            return Optional.of(handlerFunction);
        }

    }

    class CustomHandler implements HandlerFunction<ServerResponse> {

        @Override
        public ServerResponse handle(ServerRequest request) throws Exception {
            return null;
        }
    }

    class CustomPredicate implements RequestPredicate {

        @Override
        public boolean test(ServerRequest request) {
            return true;
        }
    }


}
