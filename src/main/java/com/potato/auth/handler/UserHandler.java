package com.potato.auth.handler;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.SetRolesDto;
import com.potato.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UserHandler {

    @Autowired
    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> findUserByUserName(ServerRequest request) {
        Mono<User> user = userService.loadUserByUsername(request.pathVariable("login")).log();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(user, User.class));
    }

    public Mono<ServerResponse> getUsers(ServerRequest request) {
        int page = 0;
        int size = 10;
        if (request.queryParam("page").isPresent()) {
            page = Integer.parseInt(request.queryParam("page").get());
        }
        if (request.queryParam("size").isPresent()) {
            size = Integer.parseInt(request.queryParam("size").get());
        }
        return ServerResponse.ok().body(userService.getUsers(PageRequest.of(page,size)), User.class);
    }

    public Mono<ServerResponse> activateUser(ServerRequest request) {
        Mono<User> user = userService.activateUser(request.pathVariable("login"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(user, User.class));
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        Mono<Void> deletedResult = userService.deleteUser(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromProducer(deletedResult, Void.class));
    }

    public Mono<ServerResponse> setRoles(ServerRequest request) {
        return request.bodyToMono(SetRolesDto.class)
                .flatMap(dto -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromProducer(userService.setRole(request.pathVariable("login"), dto.getRoles()), User.class)));
    }

    public Mono<ServerResponse> restartApp(ServerRequest request) {
        return userService.restartApp().flatMap(it -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).build());
    }
}