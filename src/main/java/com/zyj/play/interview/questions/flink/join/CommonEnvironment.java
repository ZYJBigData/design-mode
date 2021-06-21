package com.zyj.play.interview.questions.flink.join;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 */
public class CommonEnvironment {
    public static SingleOutputStreamOperator<WordWithCount> getSource(StreamExecutionEnvironment env,int port) {
        return env.socketTextStream("localhost", port).flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                String[] splits = value.split("\\s");
                for (String split : splits) {
                    out.collect(new WordWithCount(split, 1L));
                }
            }
        });
    }
}
