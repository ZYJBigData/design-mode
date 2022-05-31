package com.zyj.play.interview.questions.flink.util;

import org.apache.flink.api.common.eventtime.TimestampAssigner;
import org.apache.flink.api.common.eventtime.TimestampAssignerSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author yingjiezhang
 * 测试的方式为：
 * 1.三个并行度，两个partition
 * 2.二个并行度，两个partition
 */
public class FlinkWithIdleness {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(3);
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "yingjiedeMacBook-Prolocal.local:9092");
        DataStreamSource<String> kafka = env.addSource(new FlinkKafkaConsumer<>("luelue", new SimpleStringSchema(), properties));
        SingleOutputStreamOperator<String> source = kafka
                .assignTimestampsAndWatermarks(WatermarkStrategy.<String>forBoundedOutOfOrderness(Duration.ofSeconds(1))
                        .withTimestampAssigner(new TimestampAssignerSupplier<String>() {
                            @Override
                            public TimestampAssigner<String> createTimestampAssigner(Context context) {
                                return new TimestampAssigner<String>() {
                                    @Override
                                    public long extractTimestamp(String element, long recordTimestamp) {
                                        return Long.parseLong(element.split(",")[0]);
                                    }
                                };
                            }
                        }).withIdleness(Duration.ofSeconds(10)));
        SingleOutputStreamOperator<Tuple2<String, Long>> map = source.map(new MapFunction<String, Tuple2<String, Long>>() {
            @Override
            public Tuple2<String, Long> map(String value) throws Exception {
                return new Tuple2<String, Long>(value.split(",")[1], 1L);
            }
        });
        map.keyBy(x -> x.f0).window(TumblingEventTimeWindows.of(Time.of(2, TimeUnit.SECONDS)))
                .process(new ProcessWindowFunction<Tuple2<String, Long>, String, String, TimeWindow>() {
            @Override
            public void process(String s, Context context, Iterable<Tuple2<String, Long>> iterable, Collector<String> collector) throws Exception {
                long sum = 0L;
                Iterator<Tuple2<String, Long>> iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    Tuple2<String, Long> next = iterator.next();
                    sum += next.f1;
                }
                collector.collect(s + "," + sum);
            }
        }).print();
        env.execute("kafka source test");
    }
}
