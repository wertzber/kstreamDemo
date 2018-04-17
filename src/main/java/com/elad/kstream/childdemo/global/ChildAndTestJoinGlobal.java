package com.elad.kstream.childdemo.global;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.TestResult;
import com.elad.kstream.childdemo.join.ChildAndTestResultJoiner;
import com.elad.kstream.childdemo.join.ChildHightTest;
import com.elad.kstream.childdemo.producer.ChildProducer;
import com.elad.kstream.childdemo.producer.TestResultProducer;
import com.elad.kstream.childdemo.serde.ChildSerde;
import com.elad.kstream.childdemo.serde.TestResultSerde;
import com.elad.kstream.childdemo.stateful.TestAvgState;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by eladw on 4/12/18.
 * Did not create an intermediate topic
 * But create rocksDb
 */
public class ChildAndTestJoinGlobal {
    public static final String TOPIC = "child";
    public static final String TOPIC2 = "testResult";
    private static final Logger LOGGER = LoggerFactory.getLogger(ChildAndTestJoinGlobal.class);


    public static void main(String[] args) throws InterruptedException {
        GlobalKTable<String, Child> childTable = null;
        KafkaStreams streams = null;

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "global2");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.METRICS_RECORDING_LEVEL_CONFIG, "DEBUG"); //add metrics
        config.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 8);


        ChildSerde childSerde = new ChildSerde();
        TestResultSerde testResultSerde = new TestResultSerde();
        ChildAndTestResultJoiner childAndTestResultJoiner = new ChildAndTestResultJoiner();


        KStreamBuilder builder = new KStreamBuilder();

        childTable = builder.globalTable(Serdes.String(), childSerde, TOPIC);
        KStream<String, TestResult> mainStreamTest = builder.stream(Serdes.String(), new TestResultSerde(), TOPIC2);
        Predicate<String, TestResult> grade_80_100 = (k, v) -> v.getGrade() <= 100 && v.getGrade() >= 80;
        final KStream<String, TestResult>[] testBranch = mainStreamTest.branch(grade_80_100);


        testBranch[0].join(childTable,
                (a, child) -> child.getChildId()
                , (test, child) -> ChildHightTest.createHightTestFromChildAndTest(child, test)
        ).print("join ktable global");

        boolean connected = false;
        int retries = 0;

        do {
            LOGGER.info("Initiating Kafka Streams");
            try {
                streams = new KafkaStreams(builder, config);
                connected = true;
            } catch (Exception e) {
                LOGGER.error("Error during Kafka Stream initialization .. retrying", e);
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
                LOGGER.error("Uncaught exception in Thread {}", Thread.currentThread().getName(), e);
            }
        });


        streams.start();

        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(15, TOPIC);

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(15, TOPIC2);


    }

}
