package com.potato.auth.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic login() {
        return new NewTopic("login", 1, (short) 1);
    }

    @Bean
    public NewTopic register() {
        return new NewTopic("register", 1, (short) 1);
    }
}