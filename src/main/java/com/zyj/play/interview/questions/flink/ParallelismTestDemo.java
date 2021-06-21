package com.zyj.play.interview.questions.flink;

import com.zyj.play.interview.questions.flink.datasource.ParallelSourceFunctionDemo;
import com.zyj.play.interview.questions.flink.datasource.Word;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * @author zhangyingjie
 * flink中任务，子任务，是怎么划分的，划分之后是如何分配的
 */
public class ParallelismTestDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataStreamSink<String> print = env.addSource(new ParallelSourceFunctionDemo())
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Word>forBoundedOutOfOrderness
                        (Duration.ofSeconds(2)).withTimestampAssigner((element, recordTimestamp) -> element.getTimestamp())).keyBy(Word::getWord)
                .window(TumblingEventTimeWindows.of(Time.seconds(2)))
                .apply(new RichWindowFunction<Word, String, String, TimeWindow>() {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    @Override
                    public void apply(String s, TimeWindow window, Iterable<Word> input, Collector<String> out) throws Exception {
                        String windowStart = format.format(new Date(window.getStart()));
                        String windowEnd = format.format(new Date(window.getEnd()));
                        ArrayList<String> list = new ArrayList<>();
                        Iterator<Word> iterator = input.iterator();
                        while (iterator.hasNext()) {
                            list.add(iterator.next().getWord());
                        }
                        Word word = new Word(s, list.size());
                        out.collect("(" + windowStart + "," + windowEnd + ")" + word);
                    }
                }).print();
        env.execute();
    }

}
