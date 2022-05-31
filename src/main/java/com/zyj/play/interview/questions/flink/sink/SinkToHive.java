package com.zyj.play.interview.questions.flink.sink;

import com.zyj.play.interview.questions.flink.sink.utils.TableSql;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.hive.HiveCatalog;
import org.apache.flink.table.functions.ScalarFunction;

import java.util.Map;

public class SinkToHive {
    private StreamTableEnvironment streamTableEnvironment;
    private String createKafkaTableSql;

    public SinkToHive(StreamTableEnvironment streamTableEnvironment, String createKafkaTableSql) {
        this.streamTableEnvironment = streamTableEnvironment;
        this.createKafkaTableSql = createKafkaTableSql;
    }
    
    public void run(){
        streamTableEnvironment.createTemporarySystemFunction("MAP_TO_KV_STRING", MapToKvString.class);

        String catalogName = "myhive";
        String hiveDatabase = "haha";
        String hiveConfDir = "/Users/yingjiezhang/usr/local/apache-hive-2.3.9-bin/conf";
        String version = "2.1.1";

        HiveCatalog hiveCatalog = new HiveCatalog(catalogName, "default", hiveConfDir, version);
        hiveCatalog.getHiveConf().set("","");
        streamTableEnvironment.registerCatalog(catalogName, hiveCatalog);
        streamTableEnvironment.useCatalog(catalogName);

        String hiveCreateDatabaseSql = String.format(TableSql.CREATE_HIVE_DATABASE_SQL, hiveDatabase);
        streamTableEnvironment.executeSql(hiveCreateDatabaseSql);
        streamTableEnvironment.useDatabase(hiveDatabase);

        streamTableEnvironment.executeSql(TableSql.DROP_KAFKA_TABLE_SQL);
        streamTableEnvironment.executeSql(createKafkaTableSql);

        streamTableEnvironment.getConfig().setSqlDialect(SqlDialect.HIVE);


        String hiveCreateTableSql = TableSql.CREATE_HIVE_TABLE_SQL;
        streamTableEnvironment.executeSql(hiveCreateTableSql);

        streamTableEnvironment.getConfig().setSqlDialect(SqlDialect.DEFAULT);
        streamTableEnvironment.executeSql(TableSql.INSERT_HIVE_SQL);

    }

    public static class MapToKvString extends ScalarFunction {

        public String eval(Map<String, String> map) {
            try {
                StringBuilder sb = new StringBuilder();
                for (String key : map.keySet()) {
                    sb.append(key);
                    sb.append("=");
                    sb.append(map.get(key));
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                return sb.toString();
            } catch (Exception e) {
                return null;
            }
        }
    }
}
