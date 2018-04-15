package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.TestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eladw on 4/15/18.
 */
public class TestResultDeserializer implements Deserializer<TestResult> {
    private static final Logger log = LoggerFactory.getLogger(TestResultDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public TestResult deserialize(String topic, byte[] data) {
        TestResult retVal = null;

        try {
            if(data!=null){
                retVal = objectMapper.readValue(data, TestResult.class);
            }
        } catch (Exception e) {
            log.error("failed to deserialize to TestResult" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
