package com.elad.kstream.childdemo.producer;


import com.elad.kstream.childdemo.data.Child;
import net.andreinc.mockneat.MockNeat;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by vladif on 06/01/2018.
 */
public class SimpleProducer {

    private static final int NUMBER_EVENTS_TO_PRODUCE=10;
    private static final String TARGET_TEST_TOPIC="input_topic";
    private static MockNeat mock = MockNeat.old();
    private static Producer producer;


    public static void main(String[] args) throws InterruptedException {

        start();

       for (int i= 0; i < NUMBER_EVENTS_TO_PRODUCE; i++) {
           Child child = createChild();
           produce(child,child.getId(),5, TARGET_TEST_TOPIC);
       }

       close();
    }

    public static void start(){
        init();
    }

    public static < K, T> void produce( T record, String key, int numIter, String topic){

        for(int i = 0; i < numIter; i++) {
            producer.send(new ProducerRecord(topic, key, record));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //e.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static Child createChild(){
        Child child = new Child();
        child.setId(UUID.randomUUID().toString());
        child.setAge(mock.ints()
                .range(0, 30)
                .val());
        child.setHight(mock.doubles()
                .range(50, 200)
                .val());
        child.setFirstName(mock.names().first().val());
        child.setLastName(mock.names().last().val());
        //child.(mock.fromStrings(Arrays.asList("address1", "address2","address3")).val());

        return child;

    }

    public static  Properties init (){

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 10);

//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);

        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.elad.kstream.childdemo.serde.ChildSerializer");

        producer = new KafkaProducer(props);

        return props;
    }


    public static void close(){
        System.out.println("---------producer close method is called!!----------");
        producer.close();
    }
}

