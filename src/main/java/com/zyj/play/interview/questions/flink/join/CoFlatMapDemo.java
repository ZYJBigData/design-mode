package com.zyj.play.interview.questions.flink.join;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhangyingjie
 * coflatMap 在flink中并不存在 网上和的解释其实是和connect是一样
 */
public class CoFlatMapDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        CommonEnvironment.getSource(env,9000)
        System.out.println("1".equals("1"));
    }
}