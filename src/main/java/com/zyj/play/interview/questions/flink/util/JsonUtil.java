package com.zyj.play.interview.questions.flink.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.apache.logging.log4j.util.Strings;

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

    private final static org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper FLIN_OBJECT_MAPPER = new org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper()
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private final static org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper YAML_MAPPER = new YAMLMapper()
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper getFlinkObjectMapper() {
        return FLIN_OBJECT_MAPPER;
    }

    public static org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper getYamlMapper() {
        return YAML_MAPPER;
    }

    private final static String PARAM = "params";

    public static String getDeployMode(String processDefinitionJson, String keySpecial, List<String> list) {
        if (Strings.isNotBlank(processDefinitionJson)) {
            System.out.println("processDefinitionJson=={}" + processDefinitionJson);
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