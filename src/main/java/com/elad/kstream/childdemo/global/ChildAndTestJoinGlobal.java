package com.elad.kstream.childdemo.global;

import com.elad.kstream.childdemo.data.Child;
import com.elad.kstream.childdemo.data.TestResult;
import com.elad.kstream.childdemo.join.ChildAndTestResultJoiner;
import com.elad.kstream.childdemo.join.ChildHightTest;
import com.elad.kstream.childdemo.producer.ChildProducer;
import com.elad.kstream.childdemo.producer.TestResultProducer;
import com.elad.kstream.childdemo.serde.ChildSerde;
import com.elad.kstream.childdemo.serde.TestResultSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

/**
 * Created by eladw on 4/12/18.
 * Did not create an intermediate topic
 * But create rocksDb
 */
public class ChildAndTestJoinGlobal {
    public static final String TOPIC = "child";
    public static final String TOPIC2 = "testResult";


    public static void main(String[] args) throws InterruptedException {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "global");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        ChildSerde childSerde = new ChildSerde();
        TestResultSerde testResultSerde = new TestResultSerde();
        ChildAndTestResultJoiner childAndTestResultJoiner = new ChildAndTestResultJoiner();


        KStreamBuilder builder = new KStreamBuilder();

        GlobalKTable<String, Child> childTable = builder.globalTable(Serdes.String(), childSerde, TOPIC);
        KStream<String, TestResult> mainStreamTest = builder.stream(Serdes.String(), new TestResultSerde(), TOPIC2);
        Predicate<String,TestResult> grade_80_100   = (k, v)-> v.getGrade() <= 100 && v.getGrade() >=80 ;
        final KStream<String, TestResult>[] testBranch = mainStreamTest.branch(grade_80_100);


        testBranch[0].join(childTable,
                (a, child)->child.getChildId()
                ,(test,child)-> ChildHightTest.createHightTestFromChildAndTest(child,test)
                ).print("join ktable global");


        KafkaStreams streams = new KafkaStreams(builder,config);
        streams.start();

        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(15, TOPIC);

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(15, TOPIC2);


    }

}
