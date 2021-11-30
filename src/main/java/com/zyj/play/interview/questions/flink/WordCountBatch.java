package com.zyj.play.interview.questions.flink;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author yingjiezhang
 */
public class WordCountBatch {
    public static void main(String[] args) throws Exception {
//        final StreamExecutionEnvironment env =
//                StreamExecutionEnvironment.getExecutionEnvironment();
//
//        DataStream<Person> flintstones = env.fromElements(
//                new Person("Fred", 35),
//                new Person("Wilma", 35),
//                new Person("Pebbles", 2));
//
//        DataStream<Person> adults = flintstones.filter(new FilterFunction<Person>() {
//            @Override
//            public boolean filter(Person person) throws Exception {
//                return person.age >= 18;
//            }
//        });
//
//        adults.print();
//        env.execute();
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
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
        System.setProperty("HADOOP_USER_NAME", "bizseer");
        wordCount.writeAsText("hdfs://yingjiedemacbook-prolocal.local:8280/zyj/haha", FileSystem.WriteMode.OVERWRITE);
//        wordCount.writeAsText("/Users/zhangyingjie/IdeaProjects/design-mode/zyj", FileSystem.WriteMode.NO_OVERWRITE);
        wordCount.print();
        env.execute("Socket window count");

    }
}
