package com.zyj.play.interview.questions.flink.util;


import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author yingjiezhang
 */
public class HadoopUtils {
    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        Configuration conf=new Configuration();

        conf.set("fs.defaultFS", "hdfs://yingjiedeMacBook-Prolocal.local:8280");

        FileSystem fs=FileSystem.get(conf);

        FSDataInputStream fsDataInputStream=fs.open(new Path("/flink/plugins/README.txt"));

        FileOutputStream fileOutputStream=new FileOutputStream("/Users/yingjiezhang/Downloads/README.txt");

        IOUtils.copy(fsDataInputStream, fileOutputStream);

        fsDataInputStream.close();
        fileOutputStream.close();
    }
}
