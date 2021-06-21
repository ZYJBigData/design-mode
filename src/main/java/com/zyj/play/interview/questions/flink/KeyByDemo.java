package com.zyj.play.interview.questions.flink;

import com.zyj.play.interview.questions.flink.datasource.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 */
public class KeyByDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);
        DataStreamSource<Tuple3<String, String, Integer>> dataStreamSource = env.fromCollection(DataSource.getTuple3ToList());

        SingleOutputStreamOperator<String> dataStream = dataStreamSource
                .keyBy(f -> f.f1).process(new KeyedProcessFunction<String, Tuple3<String, String, Integer>, String>() {
                    @Override
                    public void processElement(Tuple3<String, String, Integer> value, Context ctx, Collector<String> out) throws Exception {
                        //获取每一行的值
                        String currentKey = ctx.getCurrentKey();
                        ctx.timerService().registerEventTimeTimer(System.currentTimeMillis() / 1000 - 2000);
                        out.collect("key: " + currentKey + "  value:  " + value);
                    }

                    @Override
                    public void onTimer(long timestamp, OnTimerContext ctx, Collector<String> out) {

                    }
                });
        dataStream.print();
        env.execute("key by stream");
    }
}
