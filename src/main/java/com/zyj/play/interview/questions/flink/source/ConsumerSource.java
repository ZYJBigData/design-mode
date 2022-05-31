package com.zyj.play.interview.questions.flink.source;

import org.apache.commons.lang.StringUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.Encoder;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.core.fs.Path;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.util.Collector;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ConsumerSource extends RichSourceFunction<String> {
    //public class ConsumerSource extends RichParallelSourceFunction<String> {
    private static final byte[] LINE_BREAK = "\n".getBytes();

    /**
     * 按照年月日分桶
     */
    private final static String DEFAULT_FORMAT_BUCKET = "yyyy-MM-dd";
    private final static Long MAX_PART_SIZE = 1L;
    /**
     * 文件滚动策略
     * 滚动时间间隔
     * 单位：分钟
     */
    private final static int ROLLOVER_INTERVAL = 1;
    /**
     * 文件滚动策略
     * 不活动时间间隔
     * 单位：分钟
     */
    private final static int INACTIVITY_INTERVAL = 1;

    public static class WorldCount implements Serializable {
        private String name;
        private Integer value;

        public WorldCount(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "WorldCount{" +
                    "name='" + name + '\'' +
                    ", value=" + value +
                    '}';
        }
    }

    @Override
    public void run(SourceContext ctx) throws Exception {
        while (true) {
            Thread.sleep(10000);
            ctx.collect("张英杰");
//            ctx.collect("{\"db\":\"metric\",\"time\":1648727880000,\"metrics\":{\"cpu_126\":45.50778},\"tags\":{\"app\":\"app_17\",\"instance\":\"instance_8\",\"class\":\"class_2\",\"service\":\"service_2\"},\"pre-agg\":true}");
        }
    }

    @Override
    public void cancel() {
        System.out.println("我被调用了，，，，，");
    }

    static class JuggleSerializationEncoder implements Encoder<byte[]> {

        @Override
        public void encode(byte[] element, OutputStream stream) throws IOException {
            stream.write(element);
            stream.write(LINE_BREAK);
        }
    }

    public static void main(String[] args) throws Exception {
        String partFileSuffix = ".txt";
        String partFilePrefix = "wanwan";
        OutputFileConfig.OutputFileConfigBuilder outputFileConfigBuilder = OutputFileConfig.builder();
        if (StringUtils.isNotEmpty(partFilePrefix)) {
            outputFileConfigBuilder.withPartPrefix(partFilePrefix);
        }
        if (StringUtils.isNotEmpty(partFileSuffix)) {
            outputFileConfigBuilder.withPartSuffix(partFileSuffix);
        }
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        env.enableCheckpointing(TimeUnit.SECONDS.toMillis(10));
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.setStateBackend(new FsStateBackend("hdfs://yingjiedeMacBook-Prolocal.local:8280/flink/checkpoints"));
        DataStreamSource<String> dataStreamSource = env.addSource(new ConsumerSource());
        dataStreamSource.flatMap(new FlatMapFunction<String, WorldCount>() {
            @Override
            public void flatMap(String value, Collector<WorldCount> out) throws Exception {
                out.collect(new WorldCount(value, 1));
            }
        })
//        worldCountSingleOutputStreamOperator
//                .windowAll(TumblingEventTimeWindows.of(Time.seconds(5)));
                .keyBy(new KeySelector<WorldCount, String>() {
                    @Override
                    public String getKey(WorldCount value) throws Exception {
                        return value.name;
                    }
                })
//        worldCountStringKeyedStream.window(TumblingEventTimeWindows.of(Time.seconds(5)));

                .reduce(new ReduceFunction<WorldCount>() {
                    @Override
                    public WorldCount reduce(WorldCount value1, WorldCount value2) throws Exception {
                        return new WorldCount(value1.name, value1.value + value1.value);
                    }
                })

                .map(new MapFunction<WorldCount, byte[]>() {

                    @Override
                    public byte[] map(WorldCount value) throws Exception {
                        ByteArrayOutputStream obj = new ByteArrayOutputStream();
                        ObjectOutputStream out = new ObjectOutputStream(obj);
                        out.writeObject(value);
                        return obj.toByteArray();
                    }
                })
//                .map(new MapFunction<byte[], WorldCount>() {
//            @Override
//            public WorldCount map(byte[] value) throws Exception {
//                ByteArrayInputStream bin = new ByteArrayInputStream((byte[]) value);
//                ObjectInputStream obin = new ObjectInputStream(bin);
//                return (WorldCount) obin.readObject();
//            }
//        })
                .addSink(StreamingFileSink.forRowFormat(new Path("/Users/yingjiezhang/FileSink"), new JuggleSerializationEncoder())
                        .withBucketAssigner(new DateTimeBucketAssigner<>(DEFAULT_FORMAT_BUCKET))
                        .withRollingPolicy(
                                DefaultRollingPolicy.builder()
                                        .withRolloverInterval(TimeUnit.MINUTES.toMillis(ROLLOVER_INTERVAL))
                                        .withInactivityInterval(TimeUnit.MINUTES.toMillis(INACTIVITY_INTERVAL))
                                        .withMaxPartSize(MAX_PART_SIZE)
                                        .build())
                        .withOutputFileConfig(outputFileConfigBuilder.build())
                        .build());
        env.execute();
    }
}
