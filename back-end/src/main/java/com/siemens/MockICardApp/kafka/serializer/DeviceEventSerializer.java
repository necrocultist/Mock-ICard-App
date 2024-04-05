package com.siemens.MockICardApp.kafka.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siemens.MockICardApp.data.model.event.DeviceEvent;
import org.apache.kafka.common.serialization.Serializer;

public class DeviceEventSerializer implements Serializer<DeviceEvent> {
    @Override
    public byte[] serialize(String topic, DeviceEvent data) {
        if (data == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing DeviceEvent to byte[]", e);
        }
    }
}
