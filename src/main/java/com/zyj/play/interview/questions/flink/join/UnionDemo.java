package com.zyj.play.interview.questions.flink.join;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @author zhangyingjie
 * 单纯的将两个流合并在一起，两个流的数据全部输出
 */
public class UnionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        CommonEnvironment.getSource(env, 9000)
                .union(CommonEnvironment.getSource(env, 9001))
                .windowAll(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                .apply(new AllWindowFunction<WordWithCount, WordWithCount, TimeWindow>() {
                    @Override
                    public void apply(TimeWindow window, Iterable<WordWithCount> values, Collector<WordWithCount> out) throws Exception {
                        Iterator<WordWithCount> iterator = values.iterator();
                        while (iterator.hasNext()) {
                            out.collect(iterator.next());
                        }
                    }
                }).print();
        env.execute();
    }
}
