package com.zyj.play.interview.questions.flink.restartstrategy;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 * 失败率策略
 * 下面代码的意思是：
 * 在5分钟内最大的重试次数是3次，每次重试的时间间隔是10秒
 */
public class FailureRateDemo {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.failureRateRestart(
                // 一段时间内最大的失败次数
                3,
                //衡量失败次数的时间段
                Time.of(5, TimeUnit.MINUTES),
                // 失败重启之间的时间间隔
                Time.of(10, TimeUnit.SECONDS)
        ));
    }
}
