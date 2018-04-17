package com.elad.kstream.childdemo.producer;

/**
 * Created by eladw on 4/15/18.
 */
public class ProducerTest {
    public static final String TOPIC = "child";
    public static final String TOPIC2 = "testResult";

    public static void main(String[] args) {
        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(10, TOPIC);

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(5000, TOPIC2);
    }

}
