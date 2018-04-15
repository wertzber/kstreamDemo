package com.elad.kstream.childdemo.producer;


import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.serde.ChildSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.*;

public class ChildProducer extends BaseProducer {

    private void createChildWrapper(String topic) {
        if(properties==null){
            initProps();
        }
        if(producer==null){
            properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ChildSerializer.class.getName());
            producer = new KafkaProducer(properties);
        }
        Child child = createChild();
        produce(child, topic);
    }

    public void childProducerLooper(int numOfIter, String topic){
        for(int i=0; i<numOfIter; i++){
            createChildWrapper(topic);
        }
    }

    public Child createChild(){
        Child child = new Child();

        child.setId(createUUID());
        child.setAge(mock.ints()
                .range(0, 30)
                .val());
        child.setHight(mock.doubles()
                .range(50, 200)
                .val());
        child.setFirstName(mock.names().first().val());
        child.setLastName(mock.names().last().val());
        return child;

    }

    public Properties initProps(){

        properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("acks", "all");
        properties.put("retries", 10);

//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);

        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "com.elad.kstream.childdemo.serde.ChildSerializer");

        return properties;
    }


    public void close(){
        System.out.println("---------producer close method is called!!----------");
        producer.close();
    }
}

