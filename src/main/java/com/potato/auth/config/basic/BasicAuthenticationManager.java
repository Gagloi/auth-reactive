package com.potato.auth.config.basic;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

//@Component
//public class BasicAuthenticationManager implements ReactiveAuthenticationManager {

//    @Autowired
//    public BasicUtil basicUtil;
//
//    @Override
//    public Mono<Authentication> authenticate(Authentication authentication) {
//        String authToken = authentication.getCredentials().toString();
//
//        String username;
//
//        try {
//            username = basicUtil.extractUsername(authToken);
//        } catch (Exception e) {
//            username = null;
//            System.out.println(e);
//        }
//        return Mono.just(new UsernamePasswordAuthenticationToken(username, null, null));

//        if (username != null && basicUtil.validateToken(authToken)) {
//            Claims claims = jwtUtil.getClaimsFromToken(authToken);
//            List<String> role = claims.get("role", List.class);
//            List<SimpleGrantedAuthority> authorities = role.stream()
//                    .map(SimpleGrantedAuthority::new)
//                    .collect(Collectors.toList());
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                    username,
//                    null,
//                    authorities
//            );
//
//            return Mono.just(authenticationToken);
//        } else {
//            return Mono.empty();
//        }
//    }
//}
