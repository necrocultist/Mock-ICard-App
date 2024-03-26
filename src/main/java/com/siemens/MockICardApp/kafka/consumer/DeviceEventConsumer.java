package com.siemens.MockICardApp.kafka.consumer;

import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import com.siemens.MockICardApp.service.EmployeeEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeviceEventConsumer {

    @Autowired
    private EmployeeEventService employeeEventService;

    @KafkaListener(topics = "${spring.kafka.topic.entrance-exit-events-topic}", groupId = "${spring.kafka.consumer.group-id.entrance-exit-events-consumer}")
    public void consume(DeviceEvent deviceEvent) {
        employeeEventService.processEmployeeEvent(deviceEvent);
    }
}
