package com.elad.kstream.childdemo.serde;

import com.elad.kstream.childdemo.data.Child;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;


public class ChildSerde implements Serde<Child> {

    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    public void close() {

    }

    public Serializer<Child> serializer() {
        return new ChildSerializer();
    }

    public Deserializer<Child> deserializer() {
        return new ChildDeserializer();
    }
}
