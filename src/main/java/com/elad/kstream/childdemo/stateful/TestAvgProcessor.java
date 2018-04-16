package com.elad.kstream.childdemo.stateful;

import com.elad.kstream.childdemo.data.TestResult;
import org.apache.commons.lang3.Validate;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.Stores;
import org.apache.kafka.streams.state.internals.InMemoryKeyValueStore;

import java.util.Properties;

/**
 * Created by eladw on 4/15/18.
 */
public class TestAvgProcessor implements Processor<String, TestResult>{

    private ProcessorContext context;
    private KeyValueStore<String, TestAvg> kvStore;

    @Override
    public void init(ProcessorContext context) {
        // keep the processor context locally because we need it in punctuate() and commit()
        this.context = context;

        // call this processor's punctuate() method every 1000 time units.
        this.context.schedule(10000);

        // retrieve the key-value store named "Counts"
        kvStore = (KeyValueStore<String, TestAvg>) context.getStateStore("test-avg2");
    }

    @Override
    public void process(String key, TestResult value) {
        TestAvg testAvgFromKV = kvStore.get(key);
        if(testAvgFromKV==null){
            System.out.println("insert new key " + key);
            TestAvg testAvg = new TestAvg();
            testAvg.setAvg(Double.valueOf(value.getGrade()));
            testAvg.setNumOfResults(1);
            kvStore.putIfAbsent(key, testAvg);
        } else {
            System.out.println("update existing key " + key);
            testAvgFromKV.setNumOfResults(testAvgFromKV.getNumOfResults() + 1);
            testAvgFromKV.setAvg((testAvgFromKV.getAvg() + value.getGrade()) / testAvgFromKV.getNumOfResults());
        }
    }

    @Override
    public void punctuate(long timestamp) {
        System.out.println("punctuate");
        KeyValueIterator<String, TestAvg> memIter = kvStore.all();
        if(memIter.hasNext()){
            System.out.println("iter: " + memIter.next());
        }
    }

    @Override
    public void close() {

        kvStore.close();
    }
}
