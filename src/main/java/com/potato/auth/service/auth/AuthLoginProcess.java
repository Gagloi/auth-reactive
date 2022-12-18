package com.potato.auth.service.auth;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class AuthLoginProcess {

    public AuthLoginProcess() {
    }

    public Mono<ServerResponse> logIn(LoginDto loginDto) {
        return retrieveUser(loginDto)
                .flatMap(retrievedUser ->
                        checkUser(retrievedUser, loginDto)
                                .flatMap(checkedUser ->
                                    additionalProcessing(checkedUser)
                                        .flatMap(this::formResponse)
                                )
                );
    }

    abstract Mono<User> retrieveUser(LoginDto loginDto);

    abstract Mono<User> checkUser(User user, LoginDto loginDto);

    abstract Mono<User> additionalProcessing(User user);

    abstract Mono<ServerResponse> formResponse(User user);

}
