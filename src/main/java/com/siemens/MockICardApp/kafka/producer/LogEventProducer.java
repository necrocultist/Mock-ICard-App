package com.siemens.MockICardApp.kafka.producer;

import com.siemens.MockICardApp.data.model.event.LogEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogEventProducer {

    @Value("${spring.kafka.topic.event-logs-topic}")
    private String topic;

    private final KafkaTemplate<String, LogEvent> kafkaTemplate;

    public LogEventProducer(KafkaTemplate<String, LogEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(LogEvent logEvent) {
        kafkaTemplate.send(topic, logEvent);
    }
}
