package com.potato.auth.service.auth.login.impl.jwt;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class TokenStoreCookieService implements TokenStoreService {

    @Override
    public Mono<ServerResponse> handleToken(Map<String, String> tokens, Map<String, Object> data) {
        return ServerResponse.ok().cookie(ResponseCookie.from("jwtToken", tokens.get("jwtToken"))
                .secure(false)
                .httpOnly(false)
                .path("/")
                .sameSite("strict")
                .build()).build();
    }
}
