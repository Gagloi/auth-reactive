package com.potato.auth.service.mail;

import com.potato.auth.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendMailService {

    @Autowired
    private JavaMailSender emailSender;

    @Async
    public void sendMessage(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("pivenjaroslav@gmail.com");
        message.setTo(user.getLogin());
        message.setSubject("Log into reactive auth");
        message.setText("You successfully logged in");
        emailSender.send(message);
    }

}
