package com.zyj.play.interview.questions.flink.restartstrategy;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @author zhangyingjie
 * 没有重启策略，任务失败就是直接失败
 */
public class NOdemo {
    public static void main(String[] args) {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setRestartStrategy(RestartStrategies.noRestart());
    }
}
