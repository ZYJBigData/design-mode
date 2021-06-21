package com.zyj.play.interview.questions.flink.window;

import com.zyj.play.interview.questions.flink.datasource.SourceFunctionDemo;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author zhangyingjie
 * <p>
 * trigger
 * evictor  清除内存中的数据，全局窗口
 */
public class SessionWindowDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.addSource(new SourceFunctionDemo())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<Long, String, Integer>>forBoundedOutOfOrderness(Duration.ofSeconds(2))
                        .withTimestampAssigner((t, l) -> t.f0))
                .keyBy(t -> t.f1)
                //设置静态的值
                .window(EventTimeSessionWindows.withGap(Time.seconds(6)))
                //尝试设置动态的值
//                .window(EventTimeSessionWindows.withDynamicGap(new SessionWindowTimeGapExtractor<Tuple3<String, Long, Integer>>() {
//                    @Override
//                    public long extract(Tuple3<String, Long, Integer> element) {
//                        return element.f2;
//                    }
//                }))
                .apply(new RichWindowFunction<Tuple3<Long, String, Integer>, String, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow window, Iterable<Tuple3<Long, String, Integer>> input, Collector<String> out) throws Exception {
                        Iterator<Tuple3<Long, String, Integer>> iterator = input.iterator();
                        ArrayList<Tuple3<Long, String, Integer>> list = new ArrayList<>();
                        while (iterator.hasNext()) {
                            list.add(iterator.next());
                        }
                        out.collect(s + "( " + window.getStart() + " , "
                                + window.getEnd() + " , " + list.size() + " )");
                    }
                }).print();
        env.execute("测试session window");
    }
}
