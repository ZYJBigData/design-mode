package com.zyj.play.interview.questions.flink.join;

import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssignerSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.time.Duration;


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
 * <p>
 * coGroup和Join之间的区别就是 join只能输出两个流都存在的元素，但是coGroup是可以进行选择自己的实现的，
 * 可以选择将两个流都有的元素输出，也可以选择将全部的元素输出。
 * 测试两条流分别两个用两个水位线和两条流用一条流水线的区别。
 */
public class CoGroupDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //注册水位线策略，完整的代码
        WatermarkStrategy<String> watermarkStrategy = WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(10))
                .withTimestampAssigner(new TimestampAssignerSupplier<String>() {
                    @Override
                    public TimestampAssigner<String> createTimestampAssigner(Context context) {
                        return new TimestampAssigner<String>() {
                            @Override
                            public long extractTimestamp(String s, long l) {
                                return Long.parseLong(s.split(",")[0]);
                            }
                        };
                    }
                });

        //注册水位线策略，lambda表达式代码
//        WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(10))
//                .withTimestampAssigner((s, l) -> Long.parseLong(s.split(",")[0]));
        //注册
//        CoGroupedStreams<WordWithCount, WordWithCount> wordWithCountWordWithCountCoGroupedStreams =
//                CommonEnvironment.getSource(env, 9000).assignTimestampsAndWatermarks(watermarkStrategy)
//                .coGroup(CommonEnvironment.getSource(env, 9001).assignTimestampsAndWatermarks(watermarkStrategy));
        
        //source的key
//        wordWithCountWordWithCountCoGroupedStreams
//                .where(value -> value.word)
//                //source1的key
//                .equalTo(value -> value.word)
//                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
//                .apply(new CoGroupFunction<WordWithCount, WordWithCount, String>() {
//                    @Override
//                    public void coGroup(Iterable<WordWithCount> first, Iterable<WordWithCount> second, Collector<String> out) throws Exception {
//                        Iterator<WordWithCount> iterator = first.iterator();
//                        Iterator<WordWithCount> iterator1 = second.iterator();
//                        while (iterator.hasNext()) {
//                            out.collect(iterator.next().word);
//                        }
//                        while (iterator1.hasNext()) {
//                            out.collect(iterator1.next().word);
//                        }
//                    }
//                }).print();
//        env.execute("co group test");
    }
}
