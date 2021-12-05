package com.zyj.play.interview.questions.flink.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author zhangyingjie
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    private final static String PARAM = "params";

    public static void main(String[] args) throws IOException {
        String jsonTest ="{\n" +
                "\t\"tasks\": [{\n" +
                "\t\t\"conditionsTask\": false,\n" +
                "\t\t\"desc\": \"告警事件管理\",\n" +
                "\t\t\"forbidden\": false,\n" +
                "\t\t\"maxRetryTimes\": 10,\n" +
                "\t\t\"name\": \"告警事件管理\",\n" +
                "\t\t\"params\": \"{\\\"mainArgs\\\":\\\"--bootstrap.servers 10.0.90.74:9000,10.0.90.75:9000,10.0.90.76:9000 --group.id ts --alert.event.source bizseer-alert --alert.event.sink bizseer-anomaly-forest-event --redis.hosts 10.0.90.75 --redis.port 6379 --redis.password Bizseer@2020 --redis.master mymaster --nacos.discovery.service 10.0.90.74:8848 --nacos.config.service 10.0.90.74:8848 --unknown.metadata.sink bizseer-metadata-unknown-metadata --es.security true --es.user elastic --es.password Bizseer@2020 --es.ssl true --es.ssl.type pkcs12 --es.ssl.password Bizseer@2020 --es.ssl.file es-http.p12 --es.address 10.0.90.74:9200,10.0.90.75:9200,10.0.90.76:9200,10.0.90.84:9200 --es.numMaxActions 1000 --es.intervalMillis 10000 --es.index alert_task_event_tag --redis.expire 1800 --xts.address ts --redis.max.total 100 --redis.max.idle 100 --redis.min.idle 20\\\",\\\"programType\\\":\\\"JAVA\\\",\\\"mainClass\\\":\\\"com.bizseer.xts.job.AlertEventJob\\\",\\\"deployMode\\\":\\\"cluster\\\",\\\"parallelism\\\":\\\"2\\\",\\\"taskManagerMemory\\\":\\\"3048\\\",\\\"mainJar\\\":{\\\"id\\\":231},\\\"slot\\\":\\\"4\\\",\\\"jobManagerMemory\\\":\\\"1024\\\",\\\"resourceList\\\":[{\\\"id\\\":40}]}\",\n" +
                "\t\t\"retryInterval\": 1,\n" +
                "\t\t\"taskTimeoutParameter\": {\n" +
                "\t\t\t\"enable\": false,\n" +
                "\t\t\t\"interval\": 0\n" +
                "\t\t},\n" +
                "\t\t\"type\": \"FLINK\",\n" +
                "\t\t\"workerGroup\": \"flink\"\n" +
                "\t}],\n" +
                "\t\"tenantId\": 0,\n" +
                "\t\"timeout\": 0\n" +
                "}";
//        System.out.println("source==={}"+jsonTest);
        String update = updateJson(jsonTest, "deployMode", "yarn");
//        System.out.println("updsate==={}"+update);
        System.out.println(updateJson(update, "params", null));
        List<String> result = new ArrayList<>();
        System.out.println(getDeployMode(jsonTest, "type", result));
//        result.clear();
//        System.out.println(getDeployMode(jsonTest,"deployMode",result));
    }

    public static String getDeployMode(String processDefinitionJson, String keySpecial, List<String> list) {
        JSONObject jsonObject = JSONObject.parseObject(processDefinitionJson);
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            if (key.equalsIgnoreCase(PARAM)) {
                String params = jsonObject.get(PARAM).toString().replace("\\\\", "");
                jsonObject.put(key, JSONObject.parse(params));
            }
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) obj;
                for (int i = 0; i < jsonArray.size(); i++) {
                    getDeployMode(jsonArray.get(i).toString(), keySpecial, list);
                }
            } else if (obj instanceof JSONObject) {
                getDeployMode(((JSONObject) obj).toJSONString(), keySpecial, list);
            } else {
                if (key.equalsIgnoreCase(keySpecial)) {
                    list.add(jsonObject.get(keySpecial).toString());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public static String updateJson(String str, String keyModify, String value) {
        JSONObject json = JSONObject.parseObject(str);
        Set<String> keySet = json.keySet();
        for (String key : keySet) {
            if (PARAM.equalsIgnoreCase(key) && !PARAM.equalsIgnoreCase(keyModify)) {
                String params = json.get(PARAM).toString().replace("\\\\", "");
                json.put(key, JSONObject.parse(params));
            }
            Object obj = json.get(key);
            if (obj instanceof JSONArray) {
                JSONArray arr = (JSONArray) obj;
                for (int i = 0; i < arr.size(); i++) {
                    String child = updateJson(arr.get(i).toString(), keyModify, value);
                    arr.set(i, JSONObject.parse(child));
                }
                json.put(key, arr);
            } else if (obj instanceof JSONObject) {
                if (PARAM.equalsIgnoreCase(keyModify)) {
                    String paramsStr = obj.toString().replaceAll("\"", "\\\"");
                    if (key.equals(keyModify)) {
                        json.put(key, paramsStr);
                    }
                } else {
                    JSONObject sub = (JSONObject) obj;
                    String substr = updateJson(sub.toJSONString(), keyModify, value);
                    json.put(key, JSONObject.parse(substr));
                }
            } else {
                if (key.equals(keyModify)) {
                    json.put(key, value);
                }
            }
        }
        return json.toJSONString();
    }
}