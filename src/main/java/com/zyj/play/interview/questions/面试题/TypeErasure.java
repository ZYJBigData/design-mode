package com.zyj.play.interview.questions.面试题;

import com.zyj.play.interview.questions.flink.datasource.WordWithCount;
import com.zyj.play.interview.questions.flink.join.CommonEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyingjie
 * 类型摩擦 java本身和flink
 * 如果返回的是具体的
 *  .returns(TypeInformation.of(WordWithCount.class))
 * 如果返回值是泛型的
 * .returns(TypeInformation.of(new TypeHint<Tuple2<String, String>>() { }))
 * <p>
 * foo(List<Integer>)' clashes with 'foo(List<String>)'; both methods have same erasure
 */
@Slf4j
public class TypeErasure {
    public static void main(String[] args) throws Exception {
        List<String> list = new ArrayList<>();
        TypeErasure typeErasure = new TypeErasure();
        typeErasure.flinkTest();
//        traverse(list);
    }

    public void flinkTest() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<WordWithCount> source = CommonEnvironment.getSource(env, 9000);
        DataStream<WordWithCount> windowCounts = source
                .flatMap((WordWithCount value, Collector<WordWithCount> out) -> out.collect(value))
                .returns(TypeInformation.of(WordWithCount.class))
                .keyBy("word")
                .timeWindow(Time.seconds(5))
                .reduce((a, b) -> new WordWithCount(a.word, a.count + b.count));
        windowCounts.print();
        env.execute();
    }

//    static void traverse(List<Object> list) {
//        log.info("some string");
//        for (Object obj : list) {
//            System.out.println("ojb");
//        }
//    }

//    public void foo(List<Integer> list){
//
//    }
//
//    public void foo(List<String> list){
//
//    }
}
