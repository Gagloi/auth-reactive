package com.potato.auth.router;

import com.potato.auth.handler.ContextHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ContextRouter {

    @Bean
    public RouterFunction<ServerResponse> contextRoute(ContextHandler contextHandler) {
        return RouterFunctions
                .route(GET("/context").and(accept(MediaType.APPLICATION_JSON)), contextHandler::getContext)
                .andRoute(PUT("/context").and(accept(MediaType.APPLICATION_JSON)), contextHandler::updateContext);
    }

}
