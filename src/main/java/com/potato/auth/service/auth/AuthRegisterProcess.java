package com.potato.auth.service.auth;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public abstract class AuthRegisterProcess {

    public AuthRegisterProcess() {
    }

    public Mono<ServerResponse> register(RegisterDto registerDto) {
        return saveUser(registerDto)
                .flatMap(savedUser ->
                        additionalProcessing(registerDto, savedUser)
                                .flatMap(this::formResponse)
                );
    }

    abstract Mono<User> saveUser(RegisterDto registerDto);

    abstract Mono<User> additionalProcessing(RegisterDto registerDto, User user);

    abstract Mono<ServerResponse> formResponse(User user);

}
