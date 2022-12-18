package com.potato.auth.service.auth.login.impl.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class TokenStoreHeaderService implements TokenStoreService {

    @Override
    public Mono<ServerResponse> handleToken(Map<String, String> tokens, Map<String, Object> data) {
        return ServerResponse.ok().header("jwtToken", tokens.get("jwtToken")).build();
    }

}
