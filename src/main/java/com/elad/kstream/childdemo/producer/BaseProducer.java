package com.elad.kstream.childdemo.producer;


import com.elad.kstream.childdemo.data.Identity;
import net.andreinc.mockneat.MockNeat;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.*;

public class BaseProducer {

    protected static final int NUMBER_EVENTS_TO_PRODUCE=10;
    protected MockNeat mock = MockNeat.old();
    protected Producer producer;
    public static List<String> ids = new ArrayList<>();
    protected Properties properties  = null;

    public static void main(String[] args) throws InterruptedException {
        BaseProducer sp = new BaseProducer();
        sp.initProps();

        for (int i= 0; i < NUMBER_EVENTS_TO_PRODUCE; i++) {
           //createChildWrapper(TARGET_TEST_TOPIC);
           //createTestResultWrapper(TARGET_TEST_TOPIC + 3);
        }
        sp.close();
    }


    protected  < K, T extends Identity> void produce(T record, String topic){

        producer.send(new ProducerRecord(topic, record.getKey(), record));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected String createUUID() {
        String uuid  = UUID.randomUUID().toString();
        ids.add(uuid);
        return uuid;
    }

    protected String getUUID() {
        if(ids.size() > 0){
            return mock.fromStrings(ids).val();
        } else {
            return "123456";
        }
    }

    protected Properties initProps(){

        properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "all");
        properties.put("retries", 10);

//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        properties.put("value.serializer", "com.elad.kstream.childdemo.serde.ChildSerializer");

        return properties;
    }


    protected void close(){
        System.out.println("---------producer close method is called!!----------");
        producer.close();
    }
}

