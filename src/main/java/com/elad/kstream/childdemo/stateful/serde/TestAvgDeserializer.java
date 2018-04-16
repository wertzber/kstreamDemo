package com.elad.kstream.childdemo.stateful.serde;

import com.elad.kstream.childdemo.data.TestResult;
import com.elad.kstream.childdemo.stateful.TestAvg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by eladw on 4/15/18.
 */
public class TestAvgDeserializer implements Deserializer<TestAvg> {
    private static final Logger log = LoggerFactory.getLogger(TestAvgDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public TestAvg deserialize(String topic, byte[] data) {
        TestAvg retVal = null;
        try {
            if(data!=null){
                retVal = objectMapper.readValue(data, TestAvg.class);
            }
        } catch (Exception e) {
            log.error("failed to deserialize to TestAvg" , e);
            throw new RuntimeException(e);
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}
