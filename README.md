# kafka-streams  - basic tutorial
Kafka streams for developers. All you need to build microservices. 

## Prerequisites 
1. Kafka. Download and install Kafka locally. The recommended Kafka version 0.11.0.2. You can download Kafka from https://kafka.apache.org/downloads
2. Java 1.8

Set KAFKA_HOME environment variable to the the Kafka installation folder

To check installation run :

Start ZK server : $KAFKA_HOME/bin/zookeeper-server-start.sh $KAFKA_HOME/config/zookeeper.properties

Start Kafka server : $KAFKA_HOME/bin/kafka-server-start.sh $KAFKA_HOME/config/server.properties

Check in the logs (console) both ZK and Kafka started properly

## Link 
[kstream example](http://vishnuviswanath.com/kafka-streams-part2.html)

### Part1. Kafka-Stream DSL. 

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
   



 








