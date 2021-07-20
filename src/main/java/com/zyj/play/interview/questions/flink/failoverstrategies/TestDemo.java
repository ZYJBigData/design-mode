package com.zyj.play.interview.questions.flink.failoverstrategies;

import com.zyj.play.interview.questions.flink.sink.KafkaSinkDemo;
import com.zyj.play.interview.questions.flink.source.KafkaSourceDemo;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

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

        env.addSource(KafkaSourceDemo.getKafkaSource()).addSink(KafkaSinkDemo.getKafkaProducer());
        env.execute("word count from kafka data");
    }
}
