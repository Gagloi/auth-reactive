package com.potato.auth.service.auth.login.impl;

import com.potato.auth.service.auth.login.RetrieveUserService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import com.potato.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RetrieveUserServiceImpl implements RetrieveUserService {

    @Autowired
    private UserService userService;

    @Override
    public Mono<User> process(LoginDto loginDto) {
        return userService.loadUserByUsername(loginDto.getLogin());
    }

}
