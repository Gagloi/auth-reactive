package com.potato.auth.router;

import com.potato.auth.handler.AuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AuthRouter {

    @Bean
    public RouterFunction<ServerResponse> authRoute(AuthHandler authHandler) {
        return RouterFunctions
                .route(POST("/gateway/auth/login").and(accept(MediaType.APPLICATION_JSON)), authHandler::login)
                .andRoute(POST("/gateway/auth/register").and(accept(MediaType.APPLICATION_JSON)), authHandler::register)
                .andRoute(POST("/auth/google").and(accept(MediaType.APPLICATION_JSON)), authHandler::googleAuthHandler);
    }

}
