package com.potato.auth.service.auth.register.impl;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import com.potato.auth.service.auth.register.AdditionalRegisterProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendKafkaMessageOnRegister implements AdditionalRegisterProcessingService {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public Mono<User> process(RegisterDto registerDto, User user) {
        kafkaTemplate.send("register", user);
        return Mono.just(user);
    }

}
