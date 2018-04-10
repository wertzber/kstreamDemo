# kafka-streams-tech
Kafka streams for developers. All you need to build microservices. 

## Prerequisites 
1. Kafka. Download and install Kafka locally. The recommended Kafka version 0.11.0.2. You can download Kafka from https://kafka.apache.org/downloads
2. Java 1.8

Set KAFKA_HOME environment variable to the the Kafka installation folder

To check installation run :

Start ZK server : $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

Start Kafka server : $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties

Check in the logs (console) both ZK and Kafka started properly

## Introduction to Stream Processing 
[Quick introduction to Stream Processing](https://docs.google.com/presentation/d/1s32b-_upP3-J2XabYlHsRp45iG5R0lzaXtLXISXI6G4/edit#slide=id.g2e3217117f_0_64)

### Part1. Kafka-Stream DSL. 
####Stateless topologies
Some use cases doesn't require state. For example if all you need is to filter, or/and modify records values - all you need it just stateless topology.
Some examples of stateless stream transformations : 
`map`,`mapValues`,`filter`,`flatMap`,`flatMapValues`,`branch`,`foreach`,`print`,etc..
##### First simplest stateless toplogy
This topology changes input stream. Every text record in the stream it changes to uppercase and appends *"Hello"* to every record.
###### How to run
   1. Start ZK server : $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties
   2. Start Kafka server : $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties
   3. Create 2 topics: input and output topic
   
          $KAFKA_HOME/bin/kafka-topics.sh --create --topic input_topic --partitions 2 --replication-factor 1 --zookeeper localhost:2181
          $KAFKA_HOME/bin/kafka-topics.sh --create --topic output_topic --partitions 2 --replication-factor 1 --zookeeper localhost:2181

   To check all exisitng topics run : 
   
      $KAFKA_HOME/bin/kafka-topics.sh --list --zookeeper localhost:2181
      
   To describe specific topic run :
   
   $KAFKA_HOME/bin/kafka-topics.sh --describe --topic input_topic --zookeeper localhost:2181
      
   4. Start kafka producer on input topic :
   
          $KAFKA_HOME/bin/kafka-console-producer.sh --topic input_topic --broker-list localhost:9092

   5. Start kafka consumer on output topic
    
          $KAFKA_HOME/bin/kafka-console-consumer.sh --topic output_topic  --bootstrap-server localhost:9092 --from-beginning

         In order to print the key in the console you should use following command (with value deserializer in this example):
         
         $KAFKA_HOME/bin/kafka-console-consumer.sh --topic output_topic --bootstrap-server localhost:9092  --new-consumer  --property print.key=true --property value.deserializer=org.apache.kafka.common.serialization.LongDeserializer

          
   6. Run the topology and type some messages in the producer terminal (you can run the topology from Intellij)
   

Exercise : Add a filter , filtering out all null or empty records. Hint: use `filter` method :-) 

The **second example** shows how to read and process LivePerson `LPEvent`(avro). 

Take a look into `serdes` folder. These are the examples of `LPEvent` serializer and deserialzer classes

We use these classes in the setting Kafka Streams topology:
   
    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
   
    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, LPEventSerde.class);

This way you should define default serdes for your Kafka topology. 
But if you need to change the default serdes within the stream processing code you have to use overloaded methods (almost all methods  are overlaoded with the serdes generics)

Other very important point to take a look is 

    config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, LogAndNotSkipOnInvalidTimestamp.class);
    
`LogAndNotSkipOnInvalidTimestamp` class goal is to extract message (event) timestamp. It's cruicial for (time) window based (stateful) processing. By default Kafka streams will use the message injestion time.  

**Next example** `BranchExampleTopology` shows the example of staless `branch` method. 
According to the predefined predicates we split input stream of jsons

Anoter point to pay attention is using json serdes and overloaded method `stream` getting as a parameter json serdes 

    JsonSerializer<PersonalInfo> personalInfoJsonSerializer = new JsonSerializer<>();
    JsonDeserializer<PersonalInfo> personalInfoJsonDeserializer = new JsonDeserializer<>(PersonalInfo.class);
    Serde<PersonalInfo> personalInfoSerde = Serdes.serdeFrom(personalInfoJsonSerializer,personalInfoJsonDeserializer );
   
    KStream<String,PersonalInfo> kstreams = builder.stream(Serdes.String(), personalInfoSerde, "input_topic");
    branches[0].transformValues(()-> new PersonalInfoTransformer()).print(); 
    
**And finally** in this example take a look `transformValues`method. It allows you to run any code (transformation) message by message within stream. 

**This method has access to the local state so you can do easily enrichment of your events** for example. But about that in the next sessions.

In order to run this example you should run `SimpleProducer`, which writes events in format json into topic `input_topic`

Last example is `MySecondTopology` which is short intro inti Kafka Streams stateful processing, which we cover in the next session 
The methods to pay attention here are `groupByKey` and `count`
 
 








