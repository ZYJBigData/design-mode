package com.zyj.play.interview.questions.flink.accumulator;

import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @Author: aze
 * @Date: 2020-09-16 14:41
 */
public class ReadLineSource  implements SourceFunction<String> {

    private String filePath;
    private boolean canceled = false;

    public ReadLineSource(String filePath){
        this.filePath = filePath;
    }

    @Override
    public void run(SourceContext<String> sourceContext) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (!canceled && reader.ready()){
            String line = reader.readLine();
            sourceContext.collect(line);
            Thread.sleep(10);
        }
    }

    @Override
    public void cancel() {
        canceled = true;
    }
}