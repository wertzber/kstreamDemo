package com.elad.kstream.childdemo.stats;

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
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;

/**
 * Created by eladw on 4/12/18.
 */
public class ChildAndTestJoin {
    public static final String TOPIC = "input_topic";
    public static final String TOPIC2 = "input_topic3";


    public static void main(String[] args) throws InterruptedException {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "childSummary");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());


        ChildSerde childSerde = new ChildSerde();
        TestResultSerde testResultSerde = new TestResultSerde();
        ChildAndTestResultJoiner childAndTestResultJoiner = new ChildAndTestResultJoiner();


        KStreamBuilder builder = new KStreamBuilder();

        KStream<String, Child> mainStream = builder.stream(Serdes.String(), new ChildSerde(), TOPIC);
        Predicate<String,Child> hight_50_150   = (k, v)-> v.getHight()<= 150 && v.getHight()> 50;
        final KStream<String, Child>[] childAgeBranch = mainStream.branch(hight_50_150);
        //KStream<String, ChildHightTransformed> afterChildHightTransform = childAgeBranch[0].transformValues(()-> new ChildTransformer());

        KStream<String, TestResult> mainStreamTest = builder.stream(Serdes.String(), new TestResultSerde(), TOPIC2);
        Predicate<String,TestResult> grade_80_100   = (k, v)-> v.getGrade() <= 100 && v.getGrade() >=80 ;
        final KStream<String, TestResult>[] testBranch = mainStreamTest.branch(grade_80_100);

        JoinWindows oneMinuteWindow = JoinWindows.of(60 * 1000);

        testBranch[0].join(childAgeBranch[0], childAndTestResultJoiner
//                new ValueJoiner<TestResult, Child, ChildHightTest>() {
//
//            @Override
//            public ChildHightTest apply(TestResult testResult, Child child) {
//                ChildHightTest join = new ChildHightTest();
//                join.setGrade(testResult.getGrade());
//                join.setCategory(testResult.getCategory());
//                join.setId(child.getId());
//                join.setAge(child.getAge());
//                join.setHight(child.getHight());
//                return join;
//            }
//        }
        ,oneMinuteWindow
        ,Serdes.String()
        ,testResultSerde
        ,childSerde).print("join");




        KafkaStreams streams = new KafkaStreams(builder,config);
        streams.start();

        ChildProducer childProducer = new ChildProducer();
        childProducer.childProducerLooper(15, TOPIC);

        TestResultProducer testProducer = new TestResultProducer();
        testProducer.testResultProducerLooper(15, TOPIC2);


    }

}
