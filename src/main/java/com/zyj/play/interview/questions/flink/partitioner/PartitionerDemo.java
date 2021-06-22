package com.zyj.play.interview.questions.flink.partitioner;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import com.zyj.play.interview.questions.flink.join.CommonEnvironment;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhangyingjie
 * <p>
 * GlobalPartitioner  global()
 */
public class PartitionerDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSink<WordWithCount> wordWithCountDataStreamSink = CommonEnvironment.getSource(env, 9000)
                .setParallelism(2)
//                .global()
//                .shuffle()
//                .rebalance()
//                .rescale()
//                .broadcast()
                //要求上下游的并行度是一样的,否则程序是无法运行的报错
//                .forward()
                //采用hash从新分区
//                .keyBy(value -> value.word)
                //自定义分区实现
                .partitionCustom(new CustomPartitioner(), value -> value.word)
                .print()
                .setParallelism(4);
        env.execute("partitioner test");
    }

    static class CustomPartitioner implements Partitioner<String> {
        @Override
        public int partition(String key, int numPartitions) {
            switch (key) {
                case "a":
                    return 0;
                case "b":
                    return 1;
                case "c":
                    return 2;
                default:
                    return 3;
            }
        }
    }
}
