package com.zyj.play.interview.questions.spark;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Serializable;
import scala.Tuple2;

/**
 * @author zhangyingjie
 */
@Slf4j
public class WordCount implements Serializable {
    private static final long serialVersionUID = 123L;

    public static void main(String[] args) throws InterruptedException {
        SparkConf sparkConf = new SparkConf().setAppName("JavaWordCount");
        JavaStreamingContext jsc = new JavaStreamingContext(sparkConf, Duration.apply(5000));
        JavaPairDStream<String, Integer> javaPairDStream = jsc.socketTextStream(args[0], Integer.parseInt(args[1]))
                .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1))
                .reduceByKey((Function2<Integer, Integer, Integer>) Integer::sum);
        javaPairDStream.print();
        jsc.start();
        jsc.awaitTermination();
        jsc.stop();
    }
}
