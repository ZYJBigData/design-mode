package com.zyj.play.interview.questions.flink.restartstrategy;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * @author zhangyingjie
 * If checkpointing is not enabled, the “no restart” strategy is used.
 * If checkpointing is activated and the restart strategy has not been configured, the fixed-delay strategy is used with Integer.MAX_VALUE restart attempts.
 * 1.如果没有开启checkpoint 默认是没有重启策略的
 * 2.如果开启了checkpoint 默认的重启测试是  fixed-delay strategy
 * FixedDelay 有两个参数，两个参数的含义是：最大尝试次数是3次，每次尝试的时间间隔是10秒
 */
public class FixedDelayDemo {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(
                // 尝试重启的次数，restartAttempts
                3,
                // 两次尝试之间间隔的时间，size
                Time.of(10, TimeUnit.SECONDS)
        ));
    }
}
