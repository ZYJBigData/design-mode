package com.zyj.play.interview.questions.flink.window;

import com.zyj.play.interview.questions.flink.datasource.StreamDataSourceDemo;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhangyingjie
 */
public class CountWindowsDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);
        // read source data
        DataStreamSource<Tuple2<String, Integer>> inStream = env.addSource(new StreamDataSourceDemo());
        // calculate
        DataStream<Tuple2<String, Integer>> outStream = inStream
                .keyBy(t -> t.f0)
                //表达的意思是 窗口中的元素为3个的时候 触发计算
                //需要注意的是 比如设置的窗口大小是3，最后一个窗口的数据大小不满足3 那么最后一个窗口的数据将被丢掉
                //这种方式滚动窗口的,下面的是滑动窗口的
//                .countWindow(3)
                //这个就比较难以理解，通过我配置的参数可以看出
                //当某一个key的个数达到2的时候，触发计算，计算最近3个元素内容的值
                .countWindow(3, 2)
                .reduce(
                        (ReduceFunction<Tuple2<String, Integer>>) (value1, value2) -> Tuple2.of(value1.f0, value1.f1 + value2.f1)
                );
        outStream.print();
        env.execute("WindowWordCount");
    }
}
