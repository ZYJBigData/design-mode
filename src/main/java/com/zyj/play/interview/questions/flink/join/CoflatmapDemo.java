package com.zyj.play.interview.questions.flink.join;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author zhangyingjie
 * coflatMap 在flink中并不存在
 */
public class CoflatmapDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        CommonEnvironment.getSource(env,9000).coflatmap
    }
}
