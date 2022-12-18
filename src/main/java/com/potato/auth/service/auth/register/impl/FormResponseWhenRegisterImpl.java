package com.potato.auth.service.auth.register.impl;

import com.potato.auth.service.auth.FormResponseService;
import com.potato.auth.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class FormResponseWhenRegisterImpl implements FormResponseService<User> {

    @Override
    public Mono<ServerResponse> formResponse(User user) {
        return ServerResponse.ok().body(Mono.just(user), User.class);
    }

}
