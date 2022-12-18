package com.potato.auth.service.auth.login.impl;

import com.potato.auth.domain.User;
import com.potato.auth.service.auth.login.AdditionalUserProcessingService;
import com.potato.auth.service.mail.SendMailService;
import io.netty.util.concurrent.CompleteFuture;
import io.netty.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendMailWhenLoginServiceImpl implements AdditionalUserProcessingService {

    @Autowired
    private SendMailService sendMailService;

    @Override
    public Mono<User> process(User user) {
        sendMailService.sendMessage(user);
        return Mono.just(user);
    }

}
