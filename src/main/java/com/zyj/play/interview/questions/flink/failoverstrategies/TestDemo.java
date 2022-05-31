package com.zyj.play.interview.questions.flink.failoverstrategies;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyingjie
 */
@Slf4j
public class TestDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        //验证重启策略
//        env.setRestartStrategy(RestartStrategies.failureRateRestart(
//                // max failures per interval
//                3,
//                //time interval for measuring failure rate
//                Time.of(5, TimeUnit.MINUTES),
//                // delay
//                Time.of(10, TimeUnit.SECONDS)
//        ));
//        //开启checkpoint
//        env.enableCheckpointing(5000L);
//        //选择状态后端
//        env.setStateBackend(new FsStateBackend(new Path("hdfs://192.168.10.204:8020/checkpoint")));

//        env.addSource(KafkaSourceDemo.getKafkaSource()).addSink(KafkaSinkDemo.getKafkaProducer());
//        env.execute("word count from kafka data");

        List<String> lastNotNullValue = new ArrayList<>();
        String a = Strings.isNotBlank(null)? "a": CollectionUtils.isNotEmpty(lastNotNullValue) ? lastNotNullValue.get(0) : null;
        System.out.println("a=={}"+a);

//        if (CollectionUtils.isNotEmpty(lastNotNullValue)) {
//            if (Strings.isNotBlank("a")) {
//                System.out.println("a=={}" + a);
//            } else {
//                lastNotNullValue.get(0);
//            }
//        }
//        System.out.println("null");
    }
}
