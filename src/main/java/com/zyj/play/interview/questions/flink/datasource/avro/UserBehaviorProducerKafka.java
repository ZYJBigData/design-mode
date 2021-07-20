package com.zyj.play.interview.questions.flink.datasource.avro;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author zhangyingjie
 */
public class UserBehaviorProducerKafka {
    public static void main(String[] args) throws IOException, InterruptedException {
        //获取数据
        List<UserBehavior> data = getData();
        // 创建配置文件
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "192.168.10.204:9092");
        props.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.setProperty("value.serializer", "com.zyj.play.interview.questions.flink.datasource.avro.SimpleAvroSchemaJava");
        KafkaProducer<String, UserBehavior> userBehaviorProducer = new KafkaProducer<>(props);
        // 循环遍历数据
        for (UserBehavior userBehavior : data) {
            ProducerRecord<String, UserBehavior> producerRecord = new ProducerRecord<>("UserBehaviorKafka", userBehavior);
            userBehaviorProducer.send(producerRecord);
            System.out.println("数据写入成功"+data);
            Thread.sleep(1000);
        }
    }

    public static List<UserBehavior> getData() throws IOException {
        ArrayList<UserBehavior> userBehaviors = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(new File("/Users/zhangyingjie/test.csv")));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] splits = line.split(",");
            userBehaviors.add( new UserBehavior(Long.parseLong(splits[0]), Long.parseLong(splits[1]),
                    Integer.parseInt(splits[2]), splits[3], Long.parseLong(splits[4])));
        }
        return userBehaviors;
    }
}
