package com.example.someappjava.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin(@Value("${spring.kafka.producer.bootstrap-servers}") final String kafkaAddresses) {
        final Map<String, Object> config = new HashMap<>();
        config.put(BOOTSTRAP_SERVERS_CONFIG, kafkaAddresses);

        return new KafkaAdmin(config);
    }

    @Bean
    public NewTopic articlesTopic(@Value("${articles.kafka.topic}") final String topicName) {
        return new NewTopic(topicName, 1, (short) 1);
    }
}
