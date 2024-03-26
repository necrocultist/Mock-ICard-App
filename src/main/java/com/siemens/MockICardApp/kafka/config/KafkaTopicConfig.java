package com.siemens.MockICardApp.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.topic.entrance-exit-events-topic}")
    private String employeeEventTopic;

    @Value("${spring.kafka.topic.event-logs-topic}")
    private String eventLogsTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic employeeEventTopic() {
        return new NewTopic(employeeEventTopic, 1, (short) 1);
    }

    @Bean
    public NewTopic eventLogsTopic() {
        return new NewTopic(eventLogsTopic, 1, (short) 1);
    }
}
