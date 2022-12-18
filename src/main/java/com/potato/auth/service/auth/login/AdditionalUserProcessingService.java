package com.potato.auth.service.auth.login;

import com.potato.auth.domain.User;
import reactor.core.publisher.Mono;

public interface AdditionalUserProcessingService {

    Mono<User> process(User user);

}
