package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.Child;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ChildSerializer implements Serializer<Child>{

    private static final Logger log = LoggerFactory.getLogger(ChildDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public byte[] serialize(String topic, Child data) {

        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            log.error("failed to serialize child:" , e);
            throw new RuntimeException(e.getMessage());
        }
        return retVal;
    }

    public void close() {

    }
}
