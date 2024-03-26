//package com.siemens.MockICardApp.kafka.producer;
//
//import com.siemens.MockICardApp.data.model.event.DeviceEvent;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DeviceEventProducer {
//
//    @Value("${spring.kafka.topic.entrance-exit-events-topic}")
//    private String topic;
//
//    private final KafkaTemplate<String, DeviceEvent> kafkaTemplate;
//
//    public DeviceEventProducer(KafkaTemplate<String, DeviceEvent> kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    public void sendMessage(DeviceEvent deviceEvent) {
//        kafkaTemplate.send(topic, deviceEvent);
//    }
//}
