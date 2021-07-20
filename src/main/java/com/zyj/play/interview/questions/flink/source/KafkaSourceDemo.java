package com.zyj.play.interview.questions.flink.source;

import com.zyj.play.interview.questions.flink.datasource.avro.SimpleAvroSchemaFlink;
import com.zyj.play.interview.questions.flink.datasource.avro.UserBehavior;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

/**
 * @author zhangyingjie
 * 验证失败转移策略
 */
public class KafkaSourceDemo {
    public static FlinkKafkaConsumer<UserBehavior> getKafkaSource() {
        Properties prop = new Properties();
        prop.put("bootstrap.servers", "192.168.10.204:9092");
        prop.put("group.id", "UserBehavior");
        prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 设置反序列化类为自定义的avro反序列化类
        prop.put("value.deserializer", "src.main.java.com.zyj.play.interview.questions.flink.datasource.avro.SimpleAvroSchemaFlink.java");
//        //自动提交偏移量
        prop.put("enable.auto.commit", true);
        //自动提交偏移量的时间间隔
        prop.put("auto.commit.interval.ms", 5000);
        // 指定kafka的消费者从哪里开始消费数据
        prop.put("auto.offset.reset", "latest");

        FlinkKafkaConsumer<UserBehavior> consumer = new FlinkKafkaConsumer<>(
                "UserBehaviorKafka",
                new SimpleAvroSchemaFlink(),
                prop);
        //设置checkpoint后在提交offset，即oncheckpoint模式
        // 该值默认为true，
//        consumer.setCommitOffsetsOnCheckpoints(true);
        // 最早的数据开始消费
        // 该模式下，Kafka 中的 committed offset 将被忽略，不会用作起始位置。
//        consumer.setStartFromEarliest();

        // 消费者组最近一次提交的偏移量，默认。
        // 如果找不到分区的偏移量，那么将会使用配置中的 auto.offset.reset 设置
//        consumer.setStartFromGroupOffsets();

        // 最新的数据开始消费
        // 该模式下，Kafka 中的 committed offset 将被忽略，不会用作起始位置。
        //consumer.setStartFromLatest();
        // 指定具体的偏移量时间戳,毫秒
        // 对于每个分区，其时间戳大于或等于指定时间戳的记录将用作起始位置。
        // 如果一个分区的最新记录早于指定的时间戳，则只从最新记录读取该分区数据。
        // 在这种模式下，Kafka 中的已提交 offset 将被忽略，不会用作起始位置。
        //consumer.setStartFromTimestamp(1585047859000L);
        // 为每个分区指定偏移量
        /*Map<KafkaTopicPartition, Long> specificStartOffsets = new HashMap<>();
        specificStartOffsets.put(new KafkaTopicPartition("qfbap_ods.code_city", 0), 23L);
        specificStartOffsets.put(new KafkaTopicPartition("qfbap_ods.code_city", 1), 31L);
        specificStartOffsets.put(new KafkaTopicPartition("qfbap_ods.code_city", 2), 43L);
        consumer1.setStartFromSpecificOffsets(specificStartOffsets);*/
        /**
         *
         * 请注意：当 Job 从故障中自动恢复或使用 savepoint 手动恢复时，
         * 这些起始位置配置方法不会影响消费的起始位置。
         * 在恢复时，每个 Kafka 分区的起始位置由存储在 savepoint 或 checkpoint 中的 offset 确定
         *
         */
        return consumer;
    }
}
