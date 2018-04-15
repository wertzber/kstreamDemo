package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.TestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eladw on 4/15/18.
 */
public class TestResultSerializer implements Serializer<TestResult> {

    private static final Logger log = LoggerFactory.getLogger(TestResultSerializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, TestResult data) {
        byte[] retVal = null;
        try {
            retVal = objectMapper.writeValueAsString(data).getBytes();
        } catch (Exception e) {
            log.error("failed to serialize testResult" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
