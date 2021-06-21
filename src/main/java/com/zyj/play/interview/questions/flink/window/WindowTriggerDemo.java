package com.zyj.play.interview.questions.flink.window;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.CountEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @author zhangyingjie
 * <p>
 * A global windows assigner assigns all elements with the same key to the same single global window.
 * This windowing scheme is only useful if you also specify a custom trigger.
 * Otherwise, no computation will be performed, as the global window does not have
 * a natural end at which we could process the aggregated elements.
 * <p>
 * <p>
 * trigger 窗口触发器自定义实现，此窗口满足两个条件中的任何一个都能触发窗口，一个是元素的调数，一个元素的时间间隔
 */
public class WindowTriggerDemo {
    private static volatile boolean running = true;

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> source = env.addSource(new SourceFunction<String>() {
            @Override
            public void run(SourceContext<String> ctx) throws Exception {
                int count = 0;
                while (true) {
                    count++;
                    Thread.sleep(1000);
                    long timeMillis = System.currentTimeMillis();
                    ctx.collect("数据写入:" + timeMillis + "_________count=" + count);
                }
            }

            @Override
            public void cancel() {

            }
        });

        SingleOutputStreamOperator<String> process = source.keyBy((KeySelector<String, String>) s -> "bbb")
                .window(TumblingProcessingTimeWindows.of(Time.minutes(5)))
                //给的数字的意思是保留几个数在窗口中
                .evictor(CountEvictor.of(0))
                .trigger(new CustomTrigger(5, 20 * 1000L))
                .process(new ProcessWindowFunction<String, String, String, TimeWindow>() {
                    @Override
                    public void process(String input, Context context, Iterable<String> iterable, Collector<String> collector) throws Exception {
                        Iterator<String> iterator = iterable.iterator();
                        while (iterator.hasNext()) {
                            String next = iterator.next();
                            collector.collect(next);
                        }

                    }
                });
        process.print("===>");
        try {
            env.execute("开始任务.....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class CustomTrigger extends Trigger<String, TimeWindow> {
        private int triggerCount = 10;
        private long triggerTime = 5 * 1000L;


        private final ReducingStateDescriptor<Long> stateDesc =
                new ReducingStateDescriptor<>("count", new Sum(), LongSerializer.INSTANCE);

        private final ReducingStateDescriptor<Long> timeStateDesc =
                new ReducingStateDescriptor<>("fire-time", new Min(), LongSerializer.INSTANCE);

        public CustomTrigger(int count, long time) {
            this.triggerCount = count;
            this.triggerTime = time;
        }

        @Override
        public TriggerResult onElement(String element, long timestamp, TimeWindow window, TriggerContext ctx) throws Exception {
            ReducingState<Long> fireTimestamp = ctx.getPartitionedState(timeStateDesc);
            ReducingState<Long> count = ctx.getPartitionedState(stateDesc);
            count.add(1L);
            if (count.get() >= triggerCount) {
                System.out.println("====数据触发........");
                // 满足条数时也需要清除时间的触发器，如果不是创建结束的触发器
                if (fireTimestamp.get() != window.maxTimestamp()) {
//                logger.info("delete trigger : {}, {}", sdf.format(fireTimestamp.get()), fireTimestamp.get());
                    ctx.deleteProcessingTimeTimer(fireTimestamp.get());
                }
                count.clear();
                return TriggerResult.FIRE;
            }
            //todo  触发之后，下一条数据进来才设置时间计数器注册下一次触发的时间
            timestamp = ctx.getCurrentProcessingTime();
            if (fireTimestamp.get() == null) {
                long nextFireTimestamp = timestamp + triggerTime;
                ctx.registerProcessingTimeTimer(nextFireTimestamp);
                fireTimestamp.add(nextFireTimestamp);
            }

            return TriggerResult.CONTINUE;
        }

        @Override
        public TriggerResult onProcessingTime(long time, TimeWindow timeWindow, TriggerContext ctx) throws Exception {
            // count
            ReducingState<Long> count = ctx.getPartitionedState(stateDesc);
            //interval
            ReducingState<Long> fireTimestamp = ctx.getPartitionedState(timeStateDesc);

            // time trigger and window end
            if (time == timeWindow.maxTimestamp()) {
                // 窗口结束，清0条数和时间的计数器
                count.clear();
                ctx.deleteProcessingTimeTimer(fireTimestamp.get());
                fireTimestamp.clear();
                return TriggerResult.FIRE_AND_PURGE;
            } else if (fireTimestamp.get() != null && fireTimestamp.get().equals(time)) {
                System.out.println("============  时间间隔触发 ==============");
                // 时间计数器触发，清0条数和时间计数器
                count.clear();
                fireTimestamp.clear();
                return TriggerResult.FIRE;
            }
            return TriggerResult.CONTINUE;
        }

        @Override
        public TriggerResult onEventTime(long l, TimeWindow timeWindow, TriggerContext ctx) throws Exception {
            return TriggerResult.CONTINUE;
        }

        @Override
        public void clear(TimeWindow timeWindow, TriggerContext ctx) throws Exception {
            System.out.println("clear ....");
            ctx.getPartitionedState(stateDesc).clear();
            ReducingState<Long> fireTimestamp = ctx.getPartitionedState(timeStateDesc);
            long timestamp = fireTimestamp.get();
            ctx.deleteProcessingTimeTimer(timestamp);
            fireTimestamp.clear();
        }


        private static class Sum implements ReduceFunction<Long> {
            private static final long serialVersionUID = 1L;

            @Override
            public Long reduce(Long value1, Long value2) throws Exception {
                return value1 + value2;
            }

        }

        private static class Min implements ReduceFunction<Long> {
            private static final long serialVersionUID = 1L;

            @Override
            public Long reduce(Long value1, Long value2) throws Exception {
                return Math.min(value1, value2);
            }
        }

    }
}
