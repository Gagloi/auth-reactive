package com.potato.auth.config;

import com.potato.auth.repository.ContextRepository;
import com.potato.auth.service.auth.FormResponseService;
import com.potato.auth.service.auth.login.impl.jwt.TokenStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.*;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    private List<TokenStoreService> tokenStoreServices;

    @Autowired
    private List<FormResponseService> formResponseServices;

    @Autowired
    private ContextRepository contextRepository;

    @Bean
    public Map<String, TokenStoreService> tokenStoreServiceMap() {
        Map<String, TokenStoreService> map = new HashMap<>();
        tokenStoreServices.forEach(it -> map.put(it.getClass().getName(), it));
        return map;
    }

    @Bean
    public Map<String, FormResponseService> formResponseServiceMap() {
        Map<String, FormResponseService> map = new HashMap<>();
        formResponseServices.forEach(it -> map.put(it.getClass().getName(), it));
        return map;
    }

    @Bean
    @DependsOn("contextRepository")
    public JavaMailSender getJavaMailSender() {
        Map<String, Object> context = contextRepository.findById(1L).block().getContext();
        Map mailSettings = (Map) context.get("mailSettings");
        Map mailProps = (Map) mailSettings.get("props");
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost((String) mailSettings.get("host"));
        mailSender.setPort((Integer) mailSettings.get("port"));

        mailSender.setUsername((String) mailSettings.get("senderUsername"));
        mailSender.setPassword((String) mailSettings.get("senderPassword"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProps.get("mail.transport.protocol"));
        props.put("mail.smtp.auth", mailProps.get("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", mailProps.get("mail.smtp.starttls.enable"));
        props.put("mail.debug", mailProps.get("mail.debug"));

        return mailSender;
    }

}
