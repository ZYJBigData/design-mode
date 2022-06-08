package com.zyj.play.interview.questions.flink.source;

import com.zyj.play.interview.questions.flink.source.infra.LearnPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.IteratorUtils;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichCoGroupFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.List;

/**
 * join
 *
 * @author lyy
 */
@Slf4j
public class LearnDemo_Join {

    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();
        configuration.setString(RestOptions.BIND_PORT, "8081-8089");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(configuration);
        env.setParallelism(1);
//        //checkpoint 且是精准一次的

//        env.enableCheckpointing(30000L);
//        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
//        //超时时间
//        env.getCheckpointConfig().setCheckpointTimeout(60000);

        DataStreamSource<LearnPojo> source_1 = env.addSource(new MySource2_udf(false));
        DataStreamSource<LearnPojo> source_2 = env.addSource(new MySource2(false));

        //事件时间 和 水印 策略
        WatermarkStrategy<LearnPojo> watermarkStrategy = WatermarkStrategy
                .<LearnPojo>forBoundedOutOfOrderness(Duration.ofSeconds(0))
                .withTimestampAssigner((SerializableTimestampAssigner<LearnPojo>) (element, recordTimestamp) -> element.getTime())
                .withIdleness(Duration.ofSeconds(120));

        SingleOutputStreamOperator<LearnPojo> sourceStream_1 = source_1.assignTimestampsAndWatermarks(watermarkStrategy);
        SingleOutputStreamOperator<LearnPojo> sourceStream_2 = source_2.assignTimestampsAndWatermarks(watermarkStrategy);

//        SingleOutputStreamOperator<LearnPojo> sourceProcess_1 = sourceStream_1.process(new ProcessFunction<LearnPojo, LearnPojo>() {
//            @Override
//            public void processElement(LearnPojo value, Context ctx, Collector<LearnPojo> out) throws Exception {
//                value.setWaterMark(ctx.timerService().currentWatermark() + 1L);
//                System.out.println("1 ---" + value);
//                out.collect(value);
//            }
//        }).name("source1");
//
//        SingleOutputStreamOperator<LearnPojo> sourceProcess_2 = sourceStream_2.process(new ProcessFunction<LearnPojo, LearnPojo>() {
//            @Override
//            public void processElement(LearnPojo value, Context ctx, Collector<LearnPojo> out) throws Exception {
//                value.setWaterMark(ctx.timerService().currentWatermark() + 1L);
//                System.out.println("2 ---" + value);
//                out.collect(value);
//            }
//        }).name("source2");
//        DataStream<String> coGroup = sourceProcess_1.coGroup(sourceProcess_2)

        DataStream<String> coGroup = sourceStream_1.coGroup(sourceStream_2)
                .where((KeySelector<LearnPojo, String>) LearnPojo::getName)
                .equalTo((KeySelector<LearnPojo, String>) LearnPojo::getName)
                .window(EventTimeSessionWindows.withGap(Time.seconds(5)))
//                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .apply(new RichCoGroupFunction<LearnPojo, LearnPojo, String>() {
                    @Override
                    public void coGroup(Iterable<LearnPojo> first, Iterable<LearnPojo> second, Collector<String> out) throws Exception {
                        List<LearnPojo> list1 = IteratorUtils.toList(first.iterator());
                        List<LearnPojo> list2 = IteratorUtils.toList(second.iterator());
                        System.out.println("list1 ---  " + list1.size());
                        list1.forEach(System.out::println);
                        System.out.println("list2 ---  " + list2.size());
                        list2.forEach(System.out::println);
                    }
                });

//        sourceStream_1.join(sourceStream_2)
//                .where((KeySelector<LearnPojo, String>) LearnPojo::getName)
//                .equalTo((KeySelector<LearnPojo, String>) LearnPojo::getName)
//                .window(EventTimeSessionWindows.withGap(Time.seconds(5)))
////                .window(TumblingEventTimeWindows.of(Time.seconds(10)))
//                .apply(new FlatJoinFunction<LearnPojo, LearnPojo, Object>() {
//                    @Override
//                    public void join(LearnPojo first, LearnPojo second, Collector<Object> out) throws Exception {
//                        System.out.println("---------------------------------------------");
//                        System.out.println(first);
//                        System.out.println(second);
//                    }
//                }).print();

//        coGroup.process(new ProcessFunction<String, String >() {
//            @Override
//            public void processElement(String value, Context ctx, Collector<String> out) throws Exception {
//                out.collect(value);
//            }
//        }).setParallelism(3).print();
//
        env.execute("LearnDemo08");
    }


}
