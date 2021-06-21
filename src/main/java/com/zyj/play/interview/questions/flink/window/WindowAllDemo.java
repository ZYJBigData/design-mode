package com.zyj.play.interview.questions.flink.window;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.state.ReducingStateDescriptor;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * @author zhangyingjie
 * <p>
 * 对于全局窗口的测试
 */
public class WindowAllDemo {
    public static void main(String[] args) throws Exception {
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
        source.keyBy(t -> "aaa")
                .window(GlobalWindows.create())
                .trigger(new Trigger<String, GlobalWindow>() {
                    private int triggerCount = 10;
                    private long triggerTime = 5 * 1000L;

                    private final ReducingStateDescriptor<Long> stateDesc =
                            new ReducingStateDescriptor<>("count", (ReduceFunction<Long>) Long::sum, LongSerializer.INSTANCE);

                    private final ReducingStateDescriptor<Long> timeStateDesc =
                            new ReducingStateDescriptor<>("fire-time", (ReduceFunction<Long>) Math::max, LongSerializer.INSTANCE);

                    @Override
                    public TriggerResult onElement(String element, long timestamp, GlobalWindow window, TriggerContext ctx) throws Exception {
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
                    public TriggerResult onProcessingTime(long time, GlobalWindow window, TriggerContext ctx) throws Exception {
                        // count
                        ReducingState<Long> count = ctx.getPartitionedState(stateDesc);
                        //interval
                        ReducingState<Long> fireTimestamp = ctx.getPartitionedState(timeStateDesc);

                        // time trigger and window end
                        if (time == window.maxTimestamp()) {
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
                    public TriggerResult onEventTime(long time, GlobalWindow window, TriggerContext ctx) throws Exception {
                        return TriggerResult.CONTINUE;
                    }

                    @Override
                    public void clear(GlobalWindow window, TriggerContext ctx) throws Exception {
                        System.out.println("clear ....");
                        ctx.getPartitionedState(stateDesc).clear();
                        ReducingState<Long> fireTimestamp = ctx.getPartitionedState(timeStateDesc);
                        long timestamp = fireTimestamp.get();
                        ctx.deleteProcessingTimeTimer(timestamp);
                        fireTimestamp.clear();
                    }
                }).process(new ProcessWindowFunction<String, String, String, GlobalWindow>() {
            @Override
            public void process(String input, Context context, Iterable<String> iterable, Collector<String> collector) throws Exception {
                Iterator<String> iterator = iterable.iterator();
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    collector.collect(next);
                }

            }
        }).print();
        env.execute();
    }
}
