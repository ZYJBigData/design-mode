package com.zyj.play.interview.questions.flink.join;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 * <p>
 * 结果和coGroup是一样
 */
public class JoinDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        CommonEnvironment.getSource(env, 9000).join(CommonEnvironment.getSource(env, 9001))
                .where(value -> value.word).equalTo(value -> value.word)
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                .apply(new FlatJoinFunction<WordWithCount, WordWithCount, String>() {
                    @Override
                    public void join(WordWithCount first, WordWithCount second, Collector<String> out) throws Exception {
                        out.collect(first.word + " => " + second.word);
                    }
                }).print();
//                .apply(new JoinFunction<WordWithCount, WordWithCount, String>() {
//                    @Override
//                    public String join(WordWithCount first, WordWithCount second) throws Exception {
//                        System.out.println(first.word + " => " + second.word);
//                        return first.word + second.word;
//                    }
//                }).print();
        env.execute();
    }
}
