package com.potato.auth.service.auth.login.impl.jwt;


import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface TokenStoreService {

    Mono<ServerResponse> handleToken(Map<String, String> tokens, Map<String, Object> data);

}
