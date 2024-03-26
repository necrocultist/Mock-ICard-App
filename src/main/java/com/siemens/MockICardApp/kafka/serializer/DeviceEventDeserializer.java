package com.siemens.MockICardApp.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class DeviceEventDeserializer implements Deserializer<DeviceEvent> {

    @Override
    public DeviceEvent deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(data, DeviceEvent.class);
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing byte[] to DeviceEvent", e);
        }
    }
}
