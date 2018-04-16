package com.elad.kstream.childdemo.stateful.serde;

import com.elad.kstream.childdemo.data.TestResult;
import com.elad.kstream.childdemo.stateful.TestAvg;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class TestAvgSerde implements Serde<TestAvg> {

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

    public Serializer<TestAvg> serializer() {
        return new TestAvgSerializer();
    }

    public Deserializer<TestAvg> deserializer() {
        return new TestAvgDeserializer();
    }
}
