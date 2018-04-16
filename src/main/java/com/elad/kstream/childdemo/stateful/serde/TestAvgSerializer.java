package com.elad.kstream.childdemo.stateful.serde;

import com.elad.kstream.childdemo.stateful.TestAvg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eladw on 4/15/18.
 */
public class TestAvgSerializer implements Serializer<TestAvg> {

    private static final Logger log = LoggerFactory.getLogger(TestAvgSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, TestAvg data) {
        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            log.error("failed to serialize TestAvg" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
