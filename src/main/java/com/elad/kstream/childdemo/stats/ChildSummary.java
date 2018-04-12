package com.elad.kstream.childdemo.stats;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.producer.SimpleProducer;
import com.elad.kstream.childdemo.serde.ChildDeserializer;
import com.elad.kstream.childdemo.serde.ChildSerde;
import com.elad.kstream.childdemo.serde.ChildSerializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;

import java.util.Properties;

/**
 * Created by eladw on 4/11/18.
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

        KafkaStreams streams = new KafkaStreams(builder,config);
        streams.start();

        //produce data for the tests:
        SimpleProducer.start();

        //send the same PersonalInfo object to show KTable difference from KStream
        Child child = SimpleProducer.createChild();
        //produce data into input topic
        SimpleProducer.produce(child, child.getId(), 15, TOPIC);

        SimpleProducer.close();

    }



}
