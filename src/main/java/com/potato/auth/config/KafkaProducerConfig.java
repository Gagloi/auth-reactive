package com.potato.auth.config;

import com.potato.auth.domain.User;
import com.potato.auth.repository.ContextRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Autowired
    private ContextRepository contextRepository;

    @Bean
    @DependsOn("contextRepository")
    public ProducerFactory<String, User> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        Map<String, Object> context = contextRepository.findById(1L).block().getContext();
        String servers = (String) ((Map)context.get("kafkaSettings")).get("bootstrapServers");
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                servers);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, User> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
