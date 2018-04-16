package com.elad.kstream.childdemo.producer;

/**
 * Created by eladw on 4/15/18.
 */
public class ProducerTest {
    public static final String TOPIC = "topic2";
    public static final String TOPIC2 = "input_topic3";

    public static void main(String[] args) {
        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(10, TOPIC);

//        TestResultProducer testProducer = new TestResultProducer();
//        testProducer.testResultProducerLooper(2, "topic5");
    }

}
