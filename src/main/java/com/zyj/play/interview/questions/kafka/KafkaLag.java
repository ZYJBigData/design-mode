package com.zyj.play.interview.questions.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListConsumerGroupOffsetsResult;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

@Slf4j
public class KafkaLag {
    //    @Autowired
//    private static KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry = new KafkaListenerEndpointRegistry();
//
    public static void main(String[] args) throws TimeoutException, URISyntaxException, IOException {
//        testLag();
        lagOf("zyj-in", "10.0.90.74:9000,10.0.90.74:9000,10.0.90.74:9000");
    }

    public static void getRemoteFile() {
        //        SmbFile f = new SmbFile("smb://bizseer@Bizseer@2020@10.0.100.237/proc/1207/stat");
//        System.out.println(f.canRead());
//        System.out.println(f.getParent());
//        InputStreamReader inputStreamReader = Reader.new InputStreamReader(f.getInputStream());
//        inputStreamRead
//        System.out.println);
//        System.out.println(f.getContent());
    }


    public static Map<TopicPartition, Long> lagOf(String groupID, String bootstrapServers) throws TimeoutException {
        Properties props = new Properties();
        props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        try (AdminClient client = AdminClient.create(props)) {
            ListConsumerGroupOffsetsResult result = client.listConsumerGroupOffsets(groupID);
            try {
                Map<TopicPartition, OffsetAndMetadata> consumedOffsets = result.partitionsToOffsetAndMetadata().get(10, TimeUnit.SECONDS);
                props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false); // 禁止自动提交位移
                props.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

                try (final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
                    Map<TopicPartition, Long> endOffsets = consumer.endOffsets(consumedOffsets.keySet());
                    Map<TopicPartition, Long> collect = endOffsets.entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey(),
                            entry -> {
                                System.out.println(entry.getKey());
                                System.out.println(entry.getValue());
                                System.out.println(consumedOffsets.get(entry.getKey()).offset());
                                System.out.println(entry.getValue() - consumedOffsets.get(entry.getKey()).offset());
                                return entry.getValue();
                            }));
                    return collect;
                }


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // 处理中断异常
                // ...
                return Collections.emptyMap();
            } catch (ExecutionException e) {
                // 处理ExecutionException
                // ...
                return Collections.emptyMap();
            } catch (TimeoutException e) {
                throw new TimeoutException("Timed out when getting lag for consumer group " + groupID);
            }
        }
    }
}
