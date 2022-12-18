package com.potato.auth.service.auth.login.impl.jwt;

import com.potato.auth.config.jwt.JwtUtil;
import com.potato.auth.repository.ContextRepository;
import com.potato.auth.service.auth.FormResponseService;
import com.potato.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormResponseWhenLoginJWTService implements FormResponseService<User> {

    @Autowired
    private Map<String, TokenStoreService> tokenStoreServiceMap;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ContextRepository contextRepository;

    @Override
    public Mono<ServerResponse> formResponse(User user) {
        return contextRepository.findById(1L).flatMap(context -> {
            Map<String, Boolean> maps = (Map) ((Map)context.getContext().get("jwt")).get("formResponseServices");
            String service = maps.entrySet().stream().filter(it -> it.getValue()).map(at -> at.getKey()).collect(Collectors.toList()).get(0);
            return tokenStoreServiceMap.get(service).handleToken(Map.of("jwtToken", jwtUtil.generateToken(user)), new HashMap<>());
        });
    }

}
