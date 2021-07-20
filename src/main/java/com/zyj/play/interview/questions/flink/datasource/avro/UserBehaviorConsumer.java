package com.zyj.play.interview.questions.flink.datasource.avro;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

/**
 * @author zhangyingjie
 */
public class UserBehaviorConsumer {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "192.168.10.204:9092");
        prop.put("group.id", "UserBehavior");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 设置反序列化类为自定义的avro反序列化类
        prop.put("value.deserializer", "com.zyj.play.interview.questions.flink.datasource.avro.SimpleAvroSchemaJava");
        //自动提交偏移量
        prop.put("enable.auto.commit", true);
        //自动提交偏移量的时间间隔
        prop.put("auto.commit.interval.ms", 5000);
        prop.put("auto.offset.reset", "latest");
        KafkaConsumer<String, UserBehavior> consumer = new KafkaConsumer<>(prop);
        consumer.subscribe(Collections.singletonList("UserBehaviorKafkaOut"));
        while (true) {
            ConsumerRecords<String, UserBehavior> poll = consumer.poll(1000);
            for (ConsumerRecord<String, UserBehavior> stringStockConsumerRecord : poll) {
                System.out.println(stringStockConsumerRecord.value());
            }
        }
    }
}
