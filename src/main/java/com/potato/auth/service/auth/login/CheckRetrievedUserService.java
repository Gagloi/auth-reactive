package com.potato.auth.service.auth.login;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import reactor.core.publisher.Mono;

public interface CheckRetrievedUserService {

    Mono<User> checkUser(User user, LoginDto loginDto);

}
