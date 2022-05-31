package com.zyj.play.interview.questions.flink.sink;


import com.zyj.play.interview.questions.flink.sink.utils.TableSql;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.ExecutionCheckpointingOptions;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

import java.time.Duration;

/**
 * kafka 中的数据样例
 * {"pre-agg":false,"time":1650008639335,"metrics":{"app.succ_total":5.684311234022034},"db":"bizseer_metric","tags":{"model":"app_zlh_prop","instance":"zlh2"}}
 */
public class HiveSink {
    public static int checkInterval = 20;

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置失败策略
        env.setRestartStrategy(RestartStrategies.failureRateRestart(3, Time.minutes(5),Time.seconds(5)));
        //使用Blink Planner
        EnvironmentSettings settings = EnvironmentSettings.newInstance().useBlinkPlanner().build();
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env, settings);
        //设置checkpoint 机制 ，如果不开启会怎么样呢？
        tEnv.getConfig().getConfiguration().set(ExecutionCheckpointingOptions.CHECKPOINTING_MODE, CheckpointingMode.EXACTLY_ONCE);
        tEnv.getConfig().getConfiguration().set(ExecutionCheckpointingOptions.CHECKPOINTING_INTERVAL, Duration.ofSeconds(checkInterval));
        //开始调用写入到hdfs和hive中
        String createKafkaTableSql = String.format(TableSql.CREATE_KAFKA_TABLE_SQL, "metrics-persistent", "x-data-collector", "10.0.90.74:9000,10.0.90.75:9000,10.0.90.76:9000");
        SinkToHdfs sinkToHdfs = new SinkToHdfs(tEnv,createKafkaTableSql);
        sinkToHdfs.run();

//        SinkToHive sinkToHive = new SinkToHive(tEnv,createKafkaTableSql);
//        sinkToHive.run();
        
        
    }
}
