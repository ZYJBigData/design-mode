package com.zyj.play.interview.questions.flink;

//import org.apache.flink.api.common.RuntimeExecutionMode;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author zhangyingjie
 * 窗口中进行统计  流任务读取文件
 * 流批一体
 * env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC); 这里是最重要的一个部分
 */
public class WordCountDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        DataStreamSource<String> dataStreamSource = env.readTextFile("/Users/yingjiezhang/test.txt");
        
        dataStreamSource.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
                String[] splits = value.split("\\s");
                for (String split : splits) {
                    out.collect(new WordWithCount(split, 1L));
                }
            }
            //将相同的key值 分配到不同的子任务
        }).keyBy(new KeySelector<WordWithCount, String>() {
            @Override
            public String getKey(WordWithCount value) throws Exception {
                return value.getWord();
            }
        });
//        .reduce(new ReduceFunction<WordWithCount>() {
//            @Override
//            public WordWithCount reduce(WordWithCount value1, WordWithCount value2) throws Exception {
//                return new WordWithCount(value1.getWord(), value1.count + value2.count);
//            }
//        }).print();
        env.execute("stream read file");
    }
}
