package com.siemens.MockICardApp.kafka.config;

import com.siemens.MockICardApp.data.model.event.LogEvent;
import com.siemens.MockICardApp.kafka.serializer.LogEventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);  //
        return props;
    }

    @Bean
    public KafkaTemplate<String, LogEvent> logEventKafkaTemplate() {
        return new KafkaTemplate<>(logEventProducerFactory());
    }


    @Bean
    public ProducerFactory<String, LogEvent> logEventProducerFactory() {
        Map<String, Object> props = producerConfigs();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, LogEventSerializer.class);
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    //    @Bean
//    public ProducerFactory<String, DeviceEvent> deviceEventProducerFactory() {
//        Map<String, Object> props = producerConfigs();
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DeviceEventSerializer.class);   //
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, DeviceEvent> deviceEventKafkaTemplate() {
//        return new KafkaTemplate<>(deviceEventProducerFactory());
//    }
}
