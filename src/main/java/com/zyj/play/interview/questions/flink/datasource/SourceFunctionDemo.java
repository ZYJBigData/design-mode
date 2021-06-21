package com.zyj.play.interview.questions.flink.datasource;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

import java.util.List;

/**
 * @author zhangyingjie
 */
public class SourceFunctionDemo implements ParallelSourceFunction<Tuple3<Long, String, Integer>> {
    private volatile boolean running = true;

    @Override
    public void run(SourceContext<Tuple3<Long, String, Integer>> ctx) throws Exception {
        List<Tuple3<Long, String, Integer>> list = DataSource.getTuple2List_test();
        int count = 0;
        while (running && count < list.size()) {
            System.out.println("list==" + list.get(count));
            ctx.collect(list.get(count));
            count++;
//            Thread.sleep(3000);
        }
    }

    @Override
    public void cancel() {
        running = true;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<Tuple3<Long, String, Integer>> source = env.addSource(new SourceFunctionDemo());
        source.print("产生的数据。。。。。");
        env.execute();
    }
}
