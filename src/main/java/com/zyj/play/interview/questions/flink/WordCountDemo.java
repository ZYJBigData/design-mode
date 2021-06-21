package com.zyj.play.interview.questions.flink;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 * 窗口中进行统计
 */
public class WordCountDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource =
                env.socketTextStream("localhost", 9999, "\n");
        SingleOutputStreamOperator<WordWithCount> wordCount = dataStreamSource.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                String[] splits = value.split("\\s");
                for (String split : splits) {
                    out.collect(new WordWithCount(split, 1L));
                }
            }
            //将相同的key值 分配到不同的子任务
        }).keyBy(WordWithCount::getWord)
                //这种方式已经过时
//                .keyBy("word")
                //滑动窗口，指定窗口的大小为2秒，间隔的时间为1秒
                //注意一点的是 如果是滑动窗口和滑动窗口有中间重合的部分，就会触发两个窗口的计算
                .window(SlidingProcessingTimeWindows.of(Time.seconds(2), Time.seconds(2)))
                //这个sum方法和reduce方式是一致，sum底层也是用reduce实现的
//                .sum("count");
                .reduce(new ReduceFunction<WordWithCount>() {
                    @Override
                    public WordWithCount reduce(WordWithCount value1, WordWithCount value2) throws Exception {
                        return new WordWithCount(value1.getWord(), value1.count + value2.count);
                    }
                });
        wordCount.print().setParallelism(2);
        env.execute("Socket window count");
    }
}
