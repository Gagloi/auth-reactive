package com.potato.auth.config.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public class BasicSecurityContextRepository implements ServerSecurityContextRepository {
//
//    @Autowired
//    private BasicAuthenticationManager authenticationManager;
//
//    @Override
//    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
//        throw new IllegalStateException("");
//    }
//
//    @Override
//    public Mono<SecurityContext> load(ServerWebExchange exchange) {
//        String authHeader = exchange.getRequest()
//                .getHeaders()
//                .getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader != null && authHeader.startsWith("Basic ")) {
//            String authToken = authHeader.substring(6);
//
//            UsernamePasswordAuthenticationToken auth
//                    = new UsernamePasswordAuthenticationToken(authToken, authToken);
//            return authenticationManager
//                    .authenticate(auth)
//                    .map(SecurityContextImpl::new);
//        }
//
//        return Mono.empty();
//    }
//
//}
