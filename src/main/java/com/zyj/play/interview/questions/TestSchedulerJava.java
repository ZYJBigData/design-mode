package com.zyj.play.interview.questions;

import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.runtime.fs.hdfs.HadoopFileSystem;

import java.io.IOException;
import java.net.URI;
import java.security.KeyStoreException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSchedulerJava {
    public static class Farther {
        int x = 10;
    }


    public static class Son extends Farther {
        int x = 20;
    }


    public static void main(String[] args) throws InterruptedException, IOException, KeyStoreException {
//        System.out.println("hello word");
//        Thread.sleep(10000);
//        ObjectMapper objectMapper = new ObjectMapper()
//                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        String str = "{\"ret_code\":{\"a\":\"aaa.css\",\"b\":\"aaaaa\"}}";
//        JsonNode ret_code = objectMapper.readValue(str, JsonNode.class).get("ret_code");
//        System.out.println(objectMapper.writeValueAsString(ret_code).contains(".css"));
//        String str1 = "{\"ret_code\":{\"zyj\":\"aaaaa\"}}";
//        JsonNode ret_code1 = objectMapper.readValue(str1, JsonNode.class).get("ret_code");
//        System.out.println(objectMapper.writeValueAsString(ret_code1).contains(".css"));
//
//        String str2 = "{\"ret_code\":{\"ddd\":\"aaa.css\"}}";
//        JsonNode ret_code2 = objectMapper.readValue(str2, JsonNode.class).get("ret_code");
//        System.out.println(objectMapper.writeValueAsString(ret_code2).contains(".css"));

        FileSystem fs = FileSystem.getLocalFileSystem();
//        String jksPath = "hdfs://10.0.90.81:8032/test/es-http.p12/";
        String jksPath = "s3://10.0.90.81:19001/test/es-http.p12/";
        Pattern PREFIX = Pattern.compile("^[a-zA-Z0-9]+://[a-zA-Z0-9._\\-]+:[0-9]+/");
        Matcher matcher = PREFIX.matcher(jksPath);
        if (matcher.find()) {
            fs = FileSystem.get(URI.create(matcher.group()));
            jksPath = jksPath.substring(matcher.end() - 1);
            System.out.println("))))))))))))))");
        }
        if (fs instanceof HadoopFileSystem){
            System.out.println("hahahhaha");
        }
        System.out.println("fs=={}"+fs);
//        InputStream in = fs.open(new Path(jksPath));
//        System.out.println("in ==={}" + in);
    }
}
   