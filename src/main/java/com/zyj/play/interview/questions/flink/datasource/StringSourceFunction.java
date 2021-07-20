package com.zyj.play.interview.questions.flink.datasource;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @author zhangyingjie
 */
public class StringSourceFunction implements SourceFunction<String> {
    private volatile boolean running = true;

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (running) {
            String wordRan = RandomStringUtils.random(2, new char[]{'a', 'b'});
            ctx.collect(wordRan);
            Thread.sleep(2000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.addSource(new StringSourceFunction()).print();
        env.execute();
    }
}
