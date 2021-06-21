package com.zyj.play.interview.questions.flink.join;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.Iterator;


/**
 * @author zhangyingjie
 * <p>
 * coGroup ：
 * 输入的一条数据是：a b c d e f g
 * 输入的另一条数据是：a b c e
 * 输出的结果是：
 * 6> c=>c
 * 8> a=>a
 * 3> b=>b
 * 1> e=>e
 */
public class CoGroupDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        CommonEnvironment.getSource(env, 9000).coGroup(CommonEnvironment.getSource(env, 9001))
                //source的key
                .where(value -> value.word)
                //source1的key
                .equalTo(value -> value.word)
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                .apply(new CoGroupFunction<WordWithCount, WordWithCount, String>() {
                    @Override
                    public void coGroup(Iterable<WordWithCount> first, Iterable<WordWithCount> second, Collector<String> out) throws Exception {
                        Iterator<WordWithCount> iterator = first.iterator();
                        Iterator<WordWithCount> iterator1 = second.iterator();

                        while (iterator.hasNext()) {
                            while (iterator1.hasNext()) {
                                out.collect(iterator.next().word + "=>" + iterator1.next().word);
                            }
                        }
                    }
                });
        env.execute("co group test");
    }
}
