package com.siemens.MockICardApp.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.MockICardApp.data.model.event.LogEvent;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class LogEventDeserializer implements Deserializer<LogEvent> {

    @Override
    public LogEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, LogEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing byte[] to DeviceEvent", e);
        }
    }
}
