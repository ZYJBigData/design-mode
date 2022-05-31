package com.zyj.play.interview.questions.flink.sink;

import com.zyj.play.interview.questions.flink.sink.utils.TableSql;
import com.zyj.play.interview.questions.flink.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.Map;

@Slf4j
public class SinkToHdfs {
    private static final String FILE_HDFS_PATH = "hdfs://yingjiedeMacBook-Prolocal.local:8280/flinkHdfs";
    private StreamTableEnvironment streamTableEnvironment;
    private String createKafkaTableSql;

    public SinkToHdfs(StreamTableEnvironment streamTableEnvironment, String createKafkaTableSql) {
        this.streamTableEnvironment = streamTableEnvironment;
        this.createKafkaTableSql = createKafkaTableSql;
    }

    public void run() {
        streamTableEnvironment.createTemporarySystemFunction("MAP_TO_JSON_STRING", MapToJsonString.class);
        streamTableEnvironment.executeSql(createKafkaTableSql);

        streamTableEnvironment.executeSql(String.format(TableSql.CREATE_FILE_TABLE_SQL, FILE_HDFS_PATH));

        streamTableEnvironment.executeSql(TableSql.INSERT_FILE_SQL);
    }

    public static class MapToJsonString extends ScalarFunction {

        public String eval(Map<String, String> map) {
            try {
                return JsonUtil.getFlinkObjectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
    }
}
