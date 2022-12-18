package com.potato.auth.router;

import com.potato.auth.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> route(UserHandler userHandler) {
        return RouterFunctions
                .route(GET("/user/{login}").and(accept(MediaType.APPLICATION_JSON)), userHandler::findUserByUserName)
                .andRoute(GET("/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::getUsers)
                .andRoute(POST("/user/activate/{login}").and(accept(MediaType.APPLICATION_JSON)), userHandler::activateUser)
                .andRoute(DELETE("/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::deleteUser)
                .andRoute(PUT("/user/{login}/roles"), userHandler::setRoles)
                .andRoute(POST("/restart-app"), userHandler::restartApp);
    }
}