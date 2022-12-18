package com.potato.auth.repository;


import com.potato.auth.domain.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {

    Flux<Role> findAllById(List<Long> ids);

    Flux<Role> findByNameIn(List<String> names);

    @Query(
            """
            select * from roles r
            left join user_roles ur on ur.user_id = :userId
            where r.id = ur.role_id         
            """
    )
    Flux<Role> findRoleByUserId(UUID userId);

}
