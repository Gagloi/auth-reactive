package com.potato.auth.service.auth.login;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import reactor.core.publisher.Mono;

public interface RetrieveUserService {

    Mono<User> process(LoginDto loginDto);

}
