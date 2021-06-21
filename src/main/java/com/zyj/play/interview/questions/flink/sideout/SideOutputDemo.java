package com.zyj.play.interview.questions.flink.sideout;

import com.zyj.play.interview.questions.flink.datasource.OrderLog;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import static com.zyj.play.interview.questions.flink.util.JsonUtil.getObjectMapper;


/**
 * @author zhangyingjie
 * <p>
 * 分隔过滤。充当filter算子功能，将源中的不同类型的数据做分隔处理。因为使用filter算子对数据源进行筛选分隔的话，会造成数据流的多次复制，导致不必要的性能浪费
 * 延迟数据处理。在做对延时迟窗口计算时，对延迟数据进行处理
 */
public class SideOutputDemo {
    public static final OutputTag<OrderLog> NEW_PRICE =
            new OutputTag<>("NEW_PRICE", TypeInformation.of(OrderLog.class));
    public static final OutputTag<OrderLog> BACK_FLOW_PRICE =
            new OutputTag<>("BACK_FLOW_PRICE", TypeInformation.of(OrderLog.class));

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> textDataSteam = env.readTextFile("/Users/zhangyingjie/orderLog.txt");
        SingleOutputStreamOperator<OrderLog> process = textDataSteam.process(new ProcessFunction<String, OrderLog>() {
            @Override
            public void processElement(String value, Context ctx, Collector<OrderLog> out) throws Exception {
                OrderLog orderLog = getObjectMapper().readValue(value, OrderLog.class);
                if ("normal".equalsIgnoreCase(orderLog.getPriceType())) {
                    out.collect(orderLog);
                } else if ("new".equalsIgnoreCase(orderLog.getPriceType())) {
                    ctx.output(SideOutputDemo.NEW_PRICE, orderLog);
                } else if ("back".equalsIgnoreCase(orderLog.getPriceType())) {
                    ctx.output(SideOutputDemo.BACK_FLOW_PRICE, orderLog);
                }
            }
        });
        process.print("主流-正常价");
        process.getSideOutput(NEW_PRICE).print("测流-新人价");
        process.getSideOutput(BACK_FLOW_PRICE).print("测流-回流价");
        env.execute();
    }
}
