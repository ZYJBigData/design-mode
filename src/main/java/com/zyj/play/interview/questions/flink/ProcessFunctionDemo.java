package com.zyj.play.interview.questions.flink;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangyingjie
 */
public class ProcessFunctionDemo {
    private static class StreamDataSource extends RichParallelSourceFunction<Tuple3<String, Long, Long>> {
        private volatile boolean running = true;
        private int cancelCount = 0;
        private int closeCount = 0;

        @Override
        public void run(SourceContext<Tuple3<String, Long, Long>> sourceContext) throws Exception {
            System.out.println("run_thread_name::  " + Thread.currentThread().getName() + " isDemo: " + Thread.currentThread().isDaemon());
            Tuple3[] elements = new Tuple3[]{
                    Tuple3.of("a", 1L, 1000000050000L),
                    Tuple3.of("a", 1L, 1000000054000L),
                    Tuple3.of("a", 1L, 1000000079900L),
                    Tuple3.of("a", 1L, 1000000115000L),
                    Tuple3.of("b", 1L, 1000000100000L),
                    Tuple3.of("b", 1L, 1000000108000L)
            };
            int count = 0;
            while (running && count < elements.length) {
                sourceContext.collect(new Tuple3<>((String) elements[count].f0, (Long) elements[count].f1, (Long) elements[count].f2));
                count++;
                System.out.println("run count====" + count);
                Thread.sleep(1000);
            }
        }

        @Override
        public void cancel() {
            System.out.println("cancel== " + Thread.currentThread().getName() + " isDemo: " + Thread.currentThread().isDaemon());
            System.out.println("cancel===" + System.currentTimeMillis());
            cancelCount = cancelCount + 1;
            System.out.println("第cancelCount次取消。" + cancelCount);
            running = false;
        }

        @Override
        public void close() {
            System.out.println("close==" + System.currentTimeMillis());
            closeCount = closeCount + 1;
            System.out.println("第" + closeCount + "次关闭");
        }
    }

    /**
     * 存储在状态中的对象
     */
    public static class CountWithTimestamp {
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //单词
        public String key;
        //单词计数
        public long count;
        //最近更新时间
        public long lastModified;

        @Override
        public String toString() {
            return "CountWithTimestamp{" +
                    "key='" + key + '\'' +
                    ", count=" + count +
                    ", lastModified=" + sdf.format(new Date(lastModified)) +
                    '}';
        }
    }


    /**
     * ProcessFunction有两个泛型类,一个输入一个输出
     */
    public static class CountWithTimeoutFunction extends KeyedProcessFunction<String, Tuple2<String, Long>, Tuple2<String, Long>> {
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        private ValueState<CountWithTimestamp> state;

        @Override
        public void open(Configuration parameters) throws Exception {
            //根据上下文获取状态
            state = getRuntimeContext().getState(new ValueStateDescriptor<CountWithTimestamp>("myState", CountWithTimestamp.class));
        }

        @Override
        public void processElement(Tuple2<String, Long> value, Context ctx, Collector<Tuple2<String, Long>> out) throws Exception {
            CountWithTimestamp current = state.value();
            if (current == null) {
                current = new CountWithTimestamp();
                current.key = value.f0;
            }

            //更新ValueState
            current.count++;
            //这里面的context可以获取时间戳
            //todo 此时这里的时间戳可能为null,如果设置的时间为ProcessingTime
            current.lastModified = ctx.timestamp();
            System.out.println("元素" + value.f0 + " 进入事件时间为：" + sdf.format(new Date(current.lastModified)));
            state.update(current);

            //注册ProcessTimer,更新一次就会有一个ProcessTimer
            ctx.timerService().registerEventTimeTimer(current.lastModified + 9000);
        }

        @Override
        public void onTimer(long timestamp, OnTimerContext ctx, Collector<Tuple2<String, Long>> out) throws IOException {
            //获取上次时间,与参数中的timestamp相比,如果相差等于60s 就会输出
            CountWithTimestamp res = state.value();
            if (timestamp >= res.lastModified + 9000) {
                System.out.println("定时器被触发：" + "当前时间为" + sdf.format(new Date(timestamp)) +
                        " 数据的事件时间为：" + sdf.format(new Date(res.lastModified)));
                out.collect(new Tuple2<String, Long>(res.key, res.count));
            }
        }
    }

    //执行主类
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStream<Tuple2<String, Long>> data =
        DataStreamSource<Tuple3<String, Long, Long>> tuple3DataStreamSource = env.addSource(new StreamDataSource()).setParallelism(1);
//                        .assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, Long, Long>>forBoundedOutOfOrderness(Duration.ofSeconds(4))
//                                //对于传入的数据，获取响应的时间值
//                                .withTimestampAssigner((t, l) -> t.f2)).map(new MapFunction<Tuple3<String, Long, Long>, Tuple2<String, Long>>() {
//                    @Override
//                    public Tuple2<String, Long> map(Tuple3<String, Long, Long> input) throws Exception {
//                        return new Tuple2<>(input.f0, input.f1);
//                    }
//                });
//
//        data.keyBy(f -> f.f0).process(new CountWithTimeoutFunction()).print();
        tuple3DataStreamSource.print();
        env.execute();
    }
}
