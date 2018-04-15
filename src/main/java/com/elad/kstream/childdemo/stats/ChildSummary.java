package com.elad.kstream.childdemo.stats;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.producer.ChildProducer;
import com.elad.kstream.childdemo.serde.ChildSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import java.util.Properties;

/**
 * Created by eladw on 4/11/18.
 * kstream holds the child summary as ktable
 */
public class ChildSummary {

    public static final String TOPIC = "input_topic";
    public static void main(String[] args) throws InterruptedException {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "childSummary");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, ChildSerde.class);


        //Serde<Child> Serde = Serdes.serdeFrom(new ChildSerializer(),new ChildDeserializer() );

        KStreamBuilder builder = new KStreamBuilder();
        //KTable
        KTable<String, Child> childKTable = builder.table(Serdes.String(), new ChildSerde(),
                TOPIC, "child-store");

        childKTable.toStream().print("childSummary");

        //example 1 - add filter for age and then print.
        //childKTable.filter((k,v) -> v.getAge()>10).toStream().print("filter 10");

        //example 2 - groupby
        //[firstName]: Luciana, 1
        //[firstName]: Maxwell, 1
        //[firstName]: Merlin, 2
        KTable<String, Long> firstNamesCount = childKTable.toStream()
        .groupBy((k,v)->v.getFirstName(), Serdes.String(), new ChildSerde()).count();
        firstNamesCount.toStream().print("firstName");

        KafkaStreams streams = new KafkaStreams(builder,config);
        streams.start();

        ChildProducer childProducer = new ChildProducer();

        //produce data into input topic
        childProducer.childProducerLooper(15, TOPIC);


    }



}
