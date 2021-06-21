package com.zyj.play.interview.questions.flink.windowfunction;

import com.zyj.play.interview.questions.flink.datasource.DataSource;
import org.apache.flink.api.common.accumulators.AverageAccumulator;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.time.Duration;

/**
 * @author zhangyingjie
 * Aggregate算子：提供基于事件窗口进行增量计算的函数。（对输入窗口每个数据流元素递增聚合计算，并将窗口状态与窗口内元素保持在累加器中）
 */
public class AggregateFunctionDemo {
    /**
     * 遍历集合，分别打印不同性别的总人数与平均值
     */
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //Tuple3<姓名，性别（man男，girl女），年龄>
        SingleOutputStreamOperator<MyAverageAccumulator> aggregate = env.fromCollection(DataSource.getTuple2List_test())
                .assignTimestampsAndWatermarks(WatermarkStrategy.
                        <Tuple3<Long, String, Integer>>forBoundedOutOfOrderness(Duration.ofSeconds(0))
                        .withTimestampAssigner((t, l) -> t.f0))
                //按照性别进行分区
                .keyBy(k -> k.f1)
                //按数量窗口滚动，每3个输入窗口数据流，计算一次
//                .countWindow(3)
                //注意测试只有session window 窗口才会调用merge方法
                //由于实时计算具有out of order的特性，后输入的数据有可能位于2个原本分开的session中间，
                // 这样就把2个session合为1个session。此时，需要使用merge方法把多个accumulator合为1个accumulator。
                // 整个集合一下给出 和 一条条发送数据结果不同 ，还有就是这个数据延迟时间的设置 数据水位线
                .window(EventTimeSessionWindows.withGap(Time.seconds(6)))
//                .window(TumblingEventTimeWindows.of(Time.seconds(6)))
                //基于Windowed窗口Stream进行调用
                .aggregate(new AggregateFunction<Tuple3<Long, String, Integer>, MyAverageAccumulator, MyAverageAccumulator>() {
                    /**
                     * 创建累加器，开始聚合计算
                     * @return 累加器
                     */
                    @Override
                    public MyAverageAccumulator createAccumulator() {
                        return new MyAverageAccumulator();
                    }

                    /**
                     * 将窗口输入的数据流值添加到窗口累加器，并返回新的累加器值
                     * @param value
                     * @param accumulator
                     * @return
                     */
                    @Override
                    public MyAverageAccumulator add(Tuple3<Long, String, Integer> value, MyAverageAccumulator accumulator) {
                        accumulator.setGender(value.f1);
                        accumulator.add(value.f2);
                        return accumulator;
                    }

                    /**
                     * 获取累加器聚合结果
                     * @param accumulator
                     * @return
                     */
                    @Override
                    public MyAverageAccumulator getResult(MyAverageAccumulator accumulator) {
                        return accumulator;
                    }

                    /**
                     * 将两个累加器merge  merge 函数的触发条件
                     * @param a cd
                     * @param b cd
                     * @return s
                     */
                    @Override
                    public MyAverageAccumulator merge(MyAverageAccumulator a, MyAverageAccumulator b) {
                        System.out.println("merge ...............");
                        a.merge(b);
                        return a;
                    }
                });
        aggregate.print();
        env.execute("value run faster ");
    }

    public static class MyAverageAccumulator extends AverageAccumulator {
        private String gender;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            //继承父类的this.getLocalValue()方法用于计算并返回平均值
            return super.toString() + ", gender to " + gender;
        }
    }
}
