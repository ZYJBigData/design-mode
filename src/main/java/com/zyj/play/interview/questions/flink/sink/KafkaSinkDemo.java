package com.zyj.play.interview.questions.flink.sink;


import com.zyj.play.interview.questions.flink.datasource.avro.SimpleAvroSchemaFlink;
import com.zyj.play.interview.questions.flink.datasource.avro.UserBehavior;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

import java.util.Properties;

/**
 * @author zhangyingjie
 */
public class KafkaSinkDemo {
    public static FlinkKafkaProducer<UserBehavior> getKafkaProducer() {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "192.168.10.204:9092");
        return new FlinkKafkaProducer<>("UserBehaviorKafkaOut", new SimpleAvroSchemaFlink(),
                prop);
    }
}
