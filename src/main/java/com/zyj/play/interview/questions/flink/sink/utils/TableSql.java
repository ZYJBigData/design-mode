package com.zyj.play.interview.questions.flink.sink.utils;

public interface TableSql {
    
    String CREATE_KAFKA_TABLE_SQL = "CREATE TABLE kafka_source (\n" +
            "    `db` STRING,\n" +
            "    `time` BIGINT,\n" +
            "    `metrics` STRING,\n" +
            "    `tags` MAP<STRING, STRING>,\n" +
            "    `value` FLOAT \n" +
            ") WITH (\n" +
            "    'connector' = 'kafka', " +
            "    'topic' = '%s', " +
            "    'properties.group.id' = '%s', " +
            "    'json.fail-on-missing-field' = 'false', " +
            "    'json.ignore-parse-errors' = 'true', " +
            "    'scan.startup.mode' = 'latest-offset',  " +
            "    'properties.bootstrap.servers' = '%s', " +
            "    'format' = 'json' " +
            ")  ";



    String CREATE_FILE_TABLE_SQL = "CREATE TABLE bizseer_metrics (\n" +
            " `db`  STRING,\n" +
            " `time` BIGINT,\n" +
            " `tags` STRING,\n" +
            " `metrics` STRING,\n" +
            " `value` FLOAT, \n" +
            " `dt` STRING,\n" +
            " `h` STRING \n" +
            ") PARTITIONED BY (db, dt, h, metrics) WITH (\n" +
            "  'connector'='filesystem',\n" +
            "  'path'='%s',\n" +
            "  'sink.partition-commit.policy.kind' = 'custom', \n" +
            "  'sink.partition-commit.policy.class' = 'com.zyj.play.interview.questions.flink.sink.utils.ParquetFileMergingCommitPolicy', \n" +
            "  'format'='parquet'\n" +
            ")";

    
    String INSERT_FILE_SQL = "INSERT INTO bizseer_metrics " +
            "SELECT `db`, `time`, MAP_TO_JSON_STRING(tags) as tags, `metrics`, `value`, " +
            "DATE_FORMAT(FROM_UNIXTIME(`time` / 1000), 'yyyy-MM-dd') as dt, " +
            "DATE_FORMAT(FROM_UNIXTIME(`time` / 1000), 'HH') as h " +
            "from kafka_source";


    String CREATE_HIVE_DATABASE_SQL = "CREATE DATABASE IF NOT EXISTS %s";

    String DROP_KAFKA_TABLE_SQL = "DROP TABLE IF EXISTS kafka_source";
    
    String CREATE_HIVE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS bizseer_metrics (\n" +
            "  `time` BIGINT,\n" +
            "  `tags` MAP<STRING, STRING>,\n" +
            "  `value` FLOAT,\n" +
            "  `db` STRING,\n" +
            "   dt STRING,\n" +
            "   h STRING,\n" +
            "  `metrics` STRING\n" +
            ") partitioned by (db string, dt string, h string, metrics string) " +
            "stored as parquet " +
            "TBLPROPERTIES (\n" +
            "  'sink.partition-commit.policy.kind' = 'metastore,custom',\n" +
            "  'sink.partition-commit.policy.class' = 'com.zyj.play.interview.questions.flink.sink.utils.ParquetFileMergingCommitPolicy'\n" +
            ")";

    String INSERT_HIVE_SQL = "INSERT INTO bizseer_metrics " +
            "SELECT `time`, STR_TO_MAP(MAP_TO_KV_STRING(tags)) as tags, `value`, `db`, " +
            "DATE_FORMAT(FROM_UNIXTIME(`time` / 1000), 'yyyy-MM-dd') as dt, " +
            "DATE_FORMAT(FROM_UNIXTIME(`time` / 1000), 'HH') as h, " +
            "metrics " +
            "from kafka_source";
    
    
    
}
