package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.TestResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class TestResultSerde implements Serde<TestResult> {

    private static final Logger log = LoggerFactory.getLogger(TestResultSerde.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

    public Serializer<TestResult> serializer() {
        return new TestResultSerializer();
    }

    public Deserializer<TestResult> deserializer() {
        return new TestResultDeserializer();
    }
}
