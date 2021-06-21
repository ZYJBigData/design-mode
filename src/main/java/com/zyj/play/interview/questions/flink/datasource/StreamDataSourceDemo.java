package com.zyj.play.interview.questions.flink.datasource;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @author zhangyingjie
 */
public class StreamDataSourceDemo extends RichParallelSourceFunction<Tuple2<String, Integer>> {
    private volatile boolean running = true;

    Tuple2[] elements = new Tuple2[]{
            Tuple2.of("a", 1),
            Tuple2.of("a", 2),
            Tuple2.of("a", 3),
            Tuple2.of("a", 4),
            Tuple2.of("a", 5),
            Tuple2.of("a", 6),
            Tuple2.of("b", 7),
            Tuple2.of("b", 8),
            Tuple2.of("b", 9),
            Tuple2.of("c", 10),
            Tuple2.of("c", 11)
    };

    @Override
    public void run(SourceFunction.SourceContext<Tuple2<String, Integer>> ctx) throws InterruptedException {
        int count = 0;
        while (running && count < elements.length) {
            ctx.collect(new Tuple2<>((String) elements[count].f0,  (Integer)elements[count].f1));
            count++;
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // read source data
        DataStreamSource<Tuple2<String, Integer>> inStream = env.addSource(new StreamDataSourceDemo());
        inStream.print();
        env.execute();
    }
}
