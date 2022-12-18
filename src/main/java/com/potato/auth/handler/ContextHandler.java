package com.potato.auth.handler;

import com.potato.auth.domain.Context;
import com.potato.auth.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class ContextHandler {

    @Autowired
    private ContextRepository contextRepository;

    public Mono<ServerResponse> updateContext(ServerRequest request) {
        return request.bodyToMono(Map.class)
                .flatMap(it -> ServerResponse.ok().body(contextRepository.existsById(1L).flatMap(
                        contextExist -> {
                            if (!contextExist) {
                                return contextRepository.save(Context.builder().context(it).build());
                            } else {
                                return contextRepository.findById(1L).flatMap(at -> {
                                    at.setContext(it);
                                    return contextRepository.save(at);
                                });
                            }
                        }
                ), Context.class));
    }

    public Mono<ServerResponse> getContext(ServerRequest request) {
        return ServerResponse.ok().body(contextRepository.findById(1L), Context.class);
    }

}
