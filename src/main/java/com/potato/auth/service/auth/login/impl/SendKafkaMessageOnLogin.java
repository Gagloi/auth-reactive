package com.potato.auth.service.auth.login.impl;

import com.potato.auth.domain.User;
import com.potato.auth.service.auth.login.AdditionalUserProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendKafkaMessageOnLogin implements AdditionalUserProcessingService {

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public Mono<User> process(User user) {
        kafkaTemplate.send("login", user);
        return Mono.just(user);
    }

}
