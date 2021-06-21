package com.zyj.play.interview.questions.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyingjie
 */
public class MapAndFlatMap {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("map and flatMap of difference");
        JavaSparkContext sc = new JavaSparkContext(conf);
        List<String> list = Arrays.asList("hello java", "hello python", "hello hadoop");
        JavaRDD<String> rdd = sc.parallelize(list);
        JavaRDD<String> rdd1 = rdd.map((Function<String, String>) v1 -> v1);
        rdd1.foreach((VoidFunction<String>) s -> System.out.println("after map is =" + s));
        JavaRDD<String> rdd2 = rdd.flatMap((FlatMapFunction<String, String>) s -> Arrays.stream(s.split(",")).iterator());
        rdd2.foreach((VoidFunction<String>) s -> System.out.println("after flatmap = " + s));
    }
}
