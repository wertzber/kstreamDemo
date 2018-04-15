package com.elad.kstream.childdemo.producer;


import com.elad.kstream.childdemo.data.TestResult;
import com.elad.kstream.childdemo.serde.TestResultSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.*;

public class TestResultProducer extends BaseProducer {

    private void createTestResultWrapper(String topic) {
        if(properties==null){
            initProps();
        }
        if(producer==null){
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TestResultSerializer.class.getName());
            producer = new KafkaProducer(properties);
        }
        TestResult testResult = createTestResult();
        produce(testResult, topic);
    }

    public void testResultProducerLooper(int numOfIter, String topic){
        for(int i=0; i<numOfIter; i++){
            createTestResultWrapper(topic);
        }
    }

    public TestResult createTestResult(){
        TestResult testResult = new TestResult();
        testResult.setChildId(getUUID());
        testResult.setCategory(mock.fromStrings(Arrays.asList("math", "history","talmud", "english")).val());
        testResult.setGrade(mock.ints()
                .range(50, 100)
                .val());
        testResult.setTime(System.currentTimeMillis());
        return testResult;

    }

}

