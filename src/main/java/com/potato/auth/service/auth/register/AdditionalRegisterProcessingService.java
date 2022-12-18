package com.potato.auth.service.auth.register;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import reactor.core.publisher.Mono;

public interface AdditionalRegisterProcessingService {

    Mono<User> process(RegisterDto registerDto, User user);

}
