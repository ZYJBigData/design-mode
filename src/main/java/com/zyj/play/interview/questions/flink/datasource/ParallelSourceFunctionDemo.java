package com.zyj.play.interview.questions.flink.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.ParallelSourceFunction;

/**
 * @author zhangyingjie
 * 保留的疑问：cancel什么时候执行？为什么会执行两次。
 */
@Slf4j
public class ParallelSourceFunctionDemo implements ParallelSourceFunction<Word> {
    private volatile boolean running = true;
    private final Word word = new Word();

    @Override
    public void run(SourceContext<Word> ctx) throws Exception {
        while (running) {
            String wordRan = RandomStringUtils.random(2, new char[]{'a', 'b'});
            long timestamp = System.currentTimeMillis();
            word.setWord(wordRan);
            word.setTimestamp(timestamp);
            ctx.collect(word);
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {
        running = false;
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Word> streamSource = env.addSource(new ParallelSourceFunctionDemo()).setParallelism(2);
        streamSource.print();
        env.execute();
    }
}
