package com.siemens.MockICardApp.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.MockICardApp.data.model.event.LogEvent;
import org.apache.kafka.common.serialization.Serializer;

public class LogEventSerializer implements Serializer<LogEvent> {
    @Override
    public byte[] serialize(String topic, LogEvent data) {
        if (data == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing LogEvent to byte[]", e);
        }
    }
}
