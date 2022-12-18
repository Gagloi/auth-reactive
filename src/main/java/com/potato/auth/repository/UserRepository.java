package com.potato.auth.repository;

import com.potato.auth.domain.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {
    @Query("""
            SELECT * from Users u where u.login = :login
            """)
    Mono<User> findByLogin(String login);

    Mono<Boolean> existsUserByLogin(String login);

    @Query("""
            SELECT COUNT(id) from Users
            """)
    Mono<Long> countAll();

}
