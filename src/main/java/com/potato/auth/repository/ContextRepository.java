package com.potato.auth.repository;

import com.potato.auth.domain.Context;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ContextRepository extends ReactiveCrudRepository<Context, Long> {

    Mono<Context> findById(Long id);

}
