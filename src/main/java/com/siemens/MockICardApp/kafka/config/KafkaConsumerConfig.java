package com.siemens.MockICardApp.kafka.config;

import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import com.siemens.MockICardApp.kafka.serializer.DeviceEventDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);  //
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String, DeviceEvent> deviceEventConsumerFactory() {
        Map<String, Object> props = consumerConfigs();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DeviceEventDeserializer.class); //
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DeviceEvent> deviceEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DeviceEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deviceEventConsumerFactory());
        return factory;
    }
//
//    @Bean
//    public ConsumerFactory<String, LogEvent> logEventConsumerFactory() {
//        Map<String, Object> props = consumerConfigs();
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LogEventDeserializer.class);
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, LogEvent> logEventListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, LogEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(logEventConsumerFactory());
//        return factory;
//    }
}
