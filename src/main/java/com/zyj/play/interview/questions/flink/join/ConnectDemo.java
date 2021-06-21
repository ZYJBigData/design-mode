package com.zyj.play.interview.questions.flink.join;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoFlatMapFunction;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;


/**
 * @author zhangyingjie
 */
public class ConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        CommonEnvironment.getSource(env, 9000).connect(CommonEnvironment.getSource(env, 9001))
                .flatMap(new CoFlatMapFunction<WordWithCount, WordWithCount, String>() {
                    @Override
                    public void flatMap1(WordWithCount value, Collector<String> out) throws Exception {
                        System.out.println("flatMap1 value" + value);
                        out.collect(value.word + "  => " + value.count);
                    }

                    @Override
                    public void flatMap2(WordWithCount value, Collector<String> out) throws Exception {
                        System.out.println("flatMap2 value " + value);
                        out.collect(value.word + "  => " + value.count);
                    }
                }).windowAll(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                .apply(new AllWindowFunction<String, String, TimeWindow>() {
                    @Override
                    public void apply(TimeWindow window, Iterable<String> values, Collector<String> out) throws Exception {
                        Iterator<String> iterator = values.iterator();
                        while (iterator.hasNext()) {
                            out.collect(iterator.next());
                        }
                    }
                }).print();
        env.execute();
    }
}
