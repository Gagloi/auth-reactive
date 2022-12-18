package com.potato.auth.service.auth.login.impl;

import com.potato.auth.service.auth.login.AdditionalUserProcessingService;
import com.potato.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AdditionalUserWhenLoginHandleServiceImpl implements AdditionalUserProcessingService {

    @Override
    public Mono<User> process(User user) {
        return Mono.just(user);
    }

}
