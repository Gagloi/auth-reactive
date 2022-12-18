package com.potato.auth.repository;

import com.potato.auth.domain.UserRoles;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface UserRolesRepository extends ReactiveCrudRepository<UserRoles, Long> {

    Flux<UserRoles> findByUserId(UUID id);

}
