package com.zyj.play.interview.questions.spark;

import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Function1;
import scala.Tuple2;
import scala.math.Ordering;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyingjie
 */
public class WordCount {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("javawordcount").setMaster("local[4]");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        JavaRDD<String> rdd1 = jsc.textFile("/Users/zhangyingjie/word.txt");
        JavaRDD<String> rdd2 = rdd1.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(" ")).iterator());
        JavaPairRDD<String, Integer> rdd3 = rdd2.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));
        JavaPairRDD<String, Integer> rdd4 = rdd3.reduceByKey((Function2<Integer, Integer, Integer>) Integer::sum);
//        List<Tuple2<String, Integer>> collect = rdd4.collect();
//        for (Tuple2<String, Integer> c : collect) {
//            System.out.println("collect:" + c._1 + ":" + c._2);
//        }
//        collect.forEach((c) -> System.out.println("foreach: " + c._1 + ":" + c._2));
//        rdd4.saveAsTextFile("/Users/zhangyingjie/wordcount.txt");
//        rdd4.saveAsHadoopFile("hdfs://192.168.10.221:8020/wordcount/output/wordcount.txt",String.class,Integer.class,
//                TextOutputFormat.class);
//        rdd4.saveAsObjectFile("/Users/zhangyingjie/wordcountobject.txt");
//        Map<String, Integer> map = rdd4.collectAsMap();
//        for (Map.Entry<String,Integer> entry:map.entrySet()){
//            System.out.println("collect map: "+" key:"+ entry.getKey() +" value:"+entry.getValue());
//        }
//        Map<String, Integer> map = rdd4.reduceByKeyLocally((Function2<Integer, Integer, Integer>) Integer::sum);
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            System.out.println("collect map: " + " key:" + entry.getKey() + " value:" + entry.getValue());
//        }
//        List<Integer> value = rdd4.lookup("spark");
//        System.out.println("value: "+value);
//
//        long count = rdd4.count();
//        System.out.println("count: "+count);

//        List<Tuple2<String, Integer>> take = rdd4.take(3);
//        for (Tuple2<String, Integer> t : take) {
//            System.out.println("take:" + t._1 + ":" + t._2);
//        }

//        Tuple2<String, Integer> first = rdd4.first();
//        System.out.println("first: "+first._1 +":"+first._2);

//        List<Tuple2<String, Integer>> top = rdd4.top(2);
//        System.out.println(top);
//
//        List<Tuple2<String, Integer>> list = rdd4.takeOrdered(3);
//        System.out.println("list===" + list);
//        Tuple2<String, Integer> reduce = rdd4.reduce((Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>) (v1, v2) -> {
//            int v3 = v1._2 + v2._2;
//            return new Tuple2<>(v1._1, v3);
//        });
//        System.out.println("reduce: " + reduce._2);

        Tuple2<String, Integer> heha = rdd4.fold(new Tuple2<>("heha", 14), (Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>) (v1, v2) -> {
            int v3 = v1._2 + v2._2;
            return new Tuple2<>(v1._1, v3);
        });
        System.out.println("fold function: " + heha._1 + ":" + heha._2);
    }
}
