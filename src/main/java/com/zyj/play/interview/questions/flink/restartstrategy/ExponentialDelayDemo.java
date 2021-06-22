package com.zyj.play.interview.questions.flink.restartstrategy;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @author zhangyingjie
 * 指数延迟（参数是很难理解的）：
 *  1.initialBackoff 重新启动之间的起始持续时间.
 *  2.maxBackoff 重新启动之间可能的最长持续时间.
 *  3.backoffMultiplier 回退值在每次失败后乘以此值,直到达到最大回退值
 *  4.resetBackoffThreshold 回退值重置为初始值时的阈值
 *  5.jitterFactor 向回退值添加随机值,避免同一时间重启多个Job.
 *
 *  指数增长
 */
public class ExponentialDelayDemo {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.exponentialDelayRestart(
                Time.milliseconds(1),
                Time.milliseconds(1000),
                // exponential multiplier
                1.1,
                // threshold duration to reset delay to its initial value
                Time.milliseconds(2000),
                // jitter
                0.1
        ));
    }
}
