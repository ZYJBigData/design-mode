package com.zyj.play.interview.questions.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyingjie
 */
public class CollectionPartitions {
    public static void main(String[] args) {
        SparkConf conf= new SparkConf().setAppName("collectionPartitions").setMaster("local[8]");
        JavaSparkContext jsc= new JavaSparkContext(conf);
        List<String> list =new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(String.valueOf(i));
        }
        JavaRDD<String> rdd1 = jsc.parallelize(list);
        //是否要进行shuffle
//        JavaRDD<String> rdd2 = rdd1.repartition(4);
        JavaRDD<String> rdd2 = rdd1.coalesce(4, false);
        for (int i = 0; i < 4; i++) {
            List<String> partitions0 = rdd2.collectPartitions(new int[]{i})[0];
            System.out.println("Partitions: "+i +"  size: "+partitions0.size());
        }
    }
}
