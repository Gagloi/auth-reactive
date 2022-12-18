package com.potato.auth.service.auth;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface FormResponseService<T> {

    Mono<ServerResponse> formResponse(T t);

}
