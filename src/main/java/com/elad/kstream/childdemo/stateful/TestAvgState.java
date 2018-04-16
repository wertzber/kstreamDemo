package com.elad.kstream.childdemo.stateful;

import com.elad.kstream.childdemo.producer.TestResultProducer;
import com.elad.kstream.childdemo.serde.ChildDeserializer;
import com.elad.kstream.childdemo.serde.TestResultDeserializer;
import com.elad.kstream.childdemo.serde.TestResultSerde;
import com.elad.kstream.childdemo.stateful.serde.TestAvgSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


/**
 * Created by eladw on 4/15/18.
 */
public class TestAvgState {


    private static final String TOPIC4 = "topic5";
    private static final Logger LOGGER = LoggerFactory.getLogger(TestAvgState.class);

    public static void main(String[] args) {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "test-stat");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        StateStoreSupplier countStore = Stores.create("test-avg2")
                .withKeys(Serdes.String())
                .withValues(new TestAvgSerde())
                //.persistent()
                .inMemory()
                .build();

        TopologyBuilder builder = new TopologyBuilder();

        // add the source processor node that takes Kafka topic "source-topic" as input
        builder.addSource("Source", new StringDeserializer(), new TestResultDeserializer(), TOPIC4)

                // add the WordCountProcessor node which takes the source processor as its upstream processor
                .addProcessor("Process", () -> new TestAvgProcessor(), "Source")

                // create the countStore associated with the TestAvgProcessor processor
                .addStateStore(countStore, "Process")

                // add the sink processor node that takes Kafka topic "sink-topic" as output
                // and the TestAvgProcessor node as its upstream processor
                .addSink("Sink", "sink-topic", "Process");

        KafkaStreams streams = null;
        boolean connected = false;
        int retries = 0;

        do {
            LOGGER.info("Initiating Kafka Streams");
            try {
                streams = new KafkaStreams(builder, config);
                connected = true;
            } catch (Exception e) {
                LOGGER.error("Error during Kafka Stream initialization {0} .. retrying", e.getMessage());
                retries++;
            }

        } while (!connected && retries <= 1); //retry

        if (!connected) {
            LOGGER.warn("Unable to initialize Kafka Streams.. exiting");
            System.exit(0);
        }

        streams.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                LOGGER.error("Uncaught exception in Thread {}",Thread.currentThread().getName(), e);
            }
        });

        streams.start();

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(1, TOPIC4);
    }

}
