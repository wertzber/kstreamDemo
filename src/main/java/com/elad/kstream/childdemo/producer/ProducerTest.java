package com.elad.kstream.childdemo.producer;

/**
 * Created by eladw on 4/15/18.
 */
public class ProducerTest {
    public static final String TOPIC = "input_topic";
    public static final String TOPIC2 = "input_topic3";

    public static void main(String[] args) {
        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(30, TOPIC);

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(30, TOPIC2);
    }

}
