package com.zyj.play.interview.questions.flink.sideout;

import com.zyj.play.interview.questions.flink.datasource.OrderLog;
import com.zyj.play.interview.questions.flink.datasource.RichParallelSourceFunctionDemo;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.time.Duration;
import java.util.Iterator;


/**
 * @author zhangyingjie
 */
public class SideOutputLateDataDemo {
    public static final OutputTag<OrderLog> NEW_PRICE = new OutputTag<>("NEW_PRICE", TypeInformation.of(OrderLog.class));

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        SingleOutputStreamOperator<OrderLog> dayPvDataStream = env.addSource(new RichParallelSourceFunctionDemo())
                //注册水位线策略：允许乱序的时间为3秒
                .assignTimestampsAndWatermarks(
                        WatermarkStrategy.<OrderLog>forBoundedOutOfOrderness(Duration.ofSeconds(4))
                                //对于传入的数据，获取响应的时间值
                                .withTimestampAssigner((orderLog, l) -> orderLog.getRequestTime()))
                //指定的key进行分到不同的子任务中
                .keyBy(OrderLog::getOrderId)
                //按照事件时间，2秒钟一个窗口，窗口间隔是2秒
                .window(TumblingEventTimeWindows.of(Time.seconds(2), Time.seconds(0)))
                //不允许迟到，过了水位线的数据都会被删除，默认值就是0
//                .allowedLateness(Time.seconds(1))
                //迟到的数据从另外一个流中输出
                .sideOutputLateData(SideOutputLateDataDemo.NEW_PRICE)
                .process(new SideOutPutWindowProcessFunction());
        //所有的数据都打印出来
//        dayPvDataStream.print("未迟到的数据");
        //得到迟到数据处理并且打印。
        dayPvDataStream.getSideOutput(SideOutputLateDataDemo.NEW_PRICE).print("迟到的数据");
        env.execute();
    }

    /**
     * 窗口函数
     */
    static class SideOutPutWindowProcessFunction extends ProcessWindowFunction<OrderLog, OrderLog, String, TimeWindow> {
        @Override
        public void process(String s, ProcessWindowFunction<OrderLog, OrderLog, String, TimeWindow>.Context ctx,
                            Iterable<OrderLog> it, Collector<OrderLog> collect)
                throws Exception {
            Iterator<OrderLog> iterator = it.iterator();
            while (iterator.hasNext()) {
                OrderLog orderLog = iterator.next();
                collect.collect(orderLog);
            }
        }
    }
}
