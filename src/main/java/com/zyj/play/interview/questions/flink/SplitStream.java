package com.zyj.play.interview.questions.flink;

import akka.japi.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

/**
 * @author yingjiezhang
 */
public class SplitStream {
    /**
     * flink进行分流
     * @param args
     */
    public static void main(String[] args) throws Exception {
        //运行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //输入数据源
        DataStreamSource<Tuple3<Integer, String, String>> source = env.fromElements(
                new Tuple3<>(1, "1", "AAA"),
                new Tuple3<>(2, "2", "AAA"),
                new Tuple3<>(3, "3", "AAA"),
                new Tuple3<>(1, "1", "BBB"),
                new Tuple3<>(2, "2", "BBB"),
                new Tuple3<>(3, "3", "BBB")
        );
        //1、定义OutputTag
        OutputTag<Tuple3<Integer, String, String>> ATag = new OutputTag<Tuple3<Integer, String, String>>("A-tag") {};
        OutputTag<Tuple3<Integer, String, String>> BTag = new OutputTag<Tuple3<Integer, String, String>>("B-tag") {};

//        OutputTag<String> A_TAG = new OutputTag<String>("A", TypeInformation.of(String.class));
//        OutputTag<String> B_TAG = new OutputTag<String>("B", TypeInformation.of(String.class));

        SingleOutputStreamOperator<Tuple3<Integer, String, String>> processedStream =
                source.process(new ProcessFunction<Tuple3<Integer, String, String>, Tuple3<Integer, String, String>>() {
                    @Override
                    public void processElement(Tuple3<Integer, String, String> value, Context ctx, Collector<Tuple3<Integer, String, String>> out) throws Exception {
                        //侧流-只输出特定数据
                        if (value.t3().equals("AAA")) {
                            ctx.output(ATag, value);
                            //为BBB的则分到这个留中分
                        } else {
                            ctx.output(BTag,value);
                        }

                    }
                });

        //获取主流
        processedStream.print("主流输出B：");
        //获取侧流
        processedStream.getSideOutput(ATag).print("分流输出A：");
        Thread.sleep(2000);
        //获取测流B
        processedStream.getSideOutput(BTag).print("分流输出B：");
        env.execute();
    }
}
