package com.zyj.play.interview.questions.flink.window;

import com.zyj.play.interview.questions.flink.datasource.StreamDataSourceDemo;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @author zhangyingjie
 */
public class CountWindowsDemo {
    public static boolean first = true;
    public static String lastKey;

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // read source data
        DataStreamSource<Tuple2<String, Integer>> inStream = env.addSource(new StreamDataSourceDemo());


        // calculate
//        DataStream<Tuple2<String, Integer>> outStream = 
//        inStream
//                .keyBy(t -> t.f0)
        //表达的意思是 窗口中的元素为3个的时候 触发计算
        //需要注意的是 比如设置的窗口大小是3，最后一个窗口的数据大小不满足3 那么最后一个窗口的数据将被丢掉
        //这种方式滚动窗口的,下面的是滑动窗口的
//                .countWindow(3)
        //这个就比较难以理解，通过我配置的参数可以看出
        //当某一个key的个数达到2的时候，触发计算，计算最近3个元素内容的值
//                .countWindow(3, 2)


        SingleOutputStreamOperator<Object> apply = inStream.keyBy(f -> f.f0).countWindow(2, 1)
                .apply(new WindowFunction<Tuple2<String, Integer>, Object, String, GlobalWindow>() {
                    @Override
                    public void apply(String s, GlobalWindow globalWindow, Iterable<Tuple2<String, Integer>> iterable, Collector<Object> collector) throws Exception {
                        if (first || !lastKey.equalsIgnoreCase(s)) {
                            lastKey = s;
                            first = false;
                        } else {
                            Iterator<Tuple2<String, Integer>> iterator = iterable.iterator();
//                        while (iterator.hasNext()) {
//                            if (first) {
//                                firstTuple = iterator.next();
//                                System.out.println("firstTuple==={}" + firstTuple);
//                            } else { 
//                                iterator.next();
//                                secondTuple = iterator.next();
//                                System.out.println("secondTuple===={}" + secondTuple);
//                                int i = secondTuple.f1 - firstTuple.f1;
//                                collector.collect(i);
//                            }
//                        }
//                        firstTuple = secondTuple;
//                        first = false;
                            Tuple2<String, Integer> next = iterator.next();
                            Tuple2<String, Integer> next1 = iterator.next();
                            Tuple2<Object, Object> result = new Tuple2<>();
                            result.f0 = next.f0;
                            result.f1 = next1.f1 - next.f1;
                            collector.collect(result);
                        }
                    }
                });
        apply.print();

//        SingleOutputStreamOperator<Object> apply = inStream.countWindowAll(2, 1).apply(new AllWindowFunction<Tuple2<String, Integer>, Object, GlobalWindow>() {
//            @Override
//            public void apply(GlobalWindow globalWindow, Iterable<Tuple2<String, Integer>> iterable, Collector<Object> collector) throws Exception {
//                if (first.get()) {
//                    first.set(false);
//                } else {
//                    Iterator<Tuple2<String, Integer>> iterator = iterable.iterator();
//                    Tuple2<String, Integer> firstValue = iterator.next();
//                    Tuple2<String, Integer> secondValue = iterator.next();
//                    int i = secondValue.f1 - firstValue.f1;
//                    collector.collect(i);
//                }
//            }
//        });
//        apply.print();


//                .reduce(
//                        (ReduceFunction<Tuple2<String, Integer>>) (value1, value2) -> Tuple2.of(value1.f0, value1.f1 + value2.f1)
//                );


//        SingleOutputStreamOperator<Object> process = inStream.keyBy(f->f.f0)
//                .process(new ProcessFunction<Tuple2<String, Integer>, Object>() {
//                    Tuple2<String, Integer> cur;
//
//                    @Override
//                    public void processElement(Tuple2<String, Integer> t1, Context context, Collector<Object> collector) throws Exception {
//                        System.out.println("获取的值是：：：：：："+t1.f1);
//                        if (first.get()) {
//                            first.set(false);
//                            cur = t1;
//                        } else {
//                            int i = t1.f1 - cur.f1;
//                            collector.collect(i);
//                            cur = t1;
//                        }
//                    }
//                });

        env.execute("WindowWordCount");
    }
}
