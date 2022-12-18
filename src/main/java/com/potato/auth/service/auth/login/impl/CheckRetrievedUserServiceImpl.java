package com.potato.auth.service.auth.login.impl;

import com.potato.auth.domain.RegisterType;
import com.potato.auth.service.auth.login.CheckRetrievedUserService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.LoginDto;
import com.potato.auth.exceptions.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CheckRetrievedUserServiceImpl implements CheckRetrievedUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> checkUser(User user, LoginDto loginDto) {
        if (!user.getEnabled()) {
            throw new RestException("User is not authenticated", 403);
        }
        if (user.getRegisterType() == RegisterType.GMAIL) {
            return Mono.just(user);
        }
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            return Mono.just(user);
        } else {
            throw new RestException("Password is incorrect", 403);
        }
    }
}
