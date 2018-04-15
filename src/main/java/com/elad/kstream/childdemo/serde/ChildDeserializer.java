package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.Child;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;


public class ChildDeserializer implements Deserializer<Child>{


    private static final Logger log = LoggerFactory.getLogger(ChildDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map configs, boolean isKey) {

    }

    public Child deserialize(String topic, byte[] data) {
        Child retVal = null;

        try {
            if(data!=null){
                retVal = objectMapper.readValue(data, Child.class);
            }
        } catch (Exception e) {
            log.error("failed to deserialize to child:" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    public void close() {

    }
}
