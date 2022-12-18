package com.potato.auth.service.auth.register.impl;

import com.potato.auth.service.auth.register.AdditionalRegisterProcessingService;
import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AdditionalRegisterProcessingServiceImpl implements AdditionalRegisterProcessingService {

    @Override
    public Mono<User> process(RegisterDto registerDto, User user) {
        return Mono.just(user);
    }

}
