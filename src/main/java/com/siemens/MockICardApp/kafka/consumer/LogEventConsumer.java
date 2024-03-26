//package com.siemens.MockICardApp.kafka.consumer;
//
//import com.siemens.MockICardApp.data.model.event.LogEvent;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LogEventConsumer {
//    private static final Logger logger = LoggerFactory.getLogger(LogEventConsumer.class);
//
//    @KafkaListener(topics = "${spring.kafka.topic.event-logs-topic}", groupId = "${spring.kafka.consumer.group-id.event-logs-consumer}")
//    public void consumeLogEvent(LogEvent logEvent) {
//        logger.info("Received log event: Action - {}, Data - {}", logEvent.getAction(), logEvent.getData());
//    }
//}
