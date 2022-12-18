package com.potato.auth.service.auth.register.impl;

import com.potato.auth.domain.User;
import com.potato.auth.domain.dto.RegisterDto;
import com.potato.auth.service.auth.register.AdditionalRegisterProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SendMailOnRegisterServiceImpl implements AdditionalRegisterProcessingService {
    @Autowired
    private JavaMailSender emailSender;

    @Override
    public Mono<User> process(RegisterDto registerDto, User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pivenjaroslav@gmail.com");
        message.setTo(user.getLogin());
        message.setSubject("auth reactive");
        message.setText("You have registered into auth reactive");
        emailSender.send(message);
        return Mono.just(user);
    }

}
