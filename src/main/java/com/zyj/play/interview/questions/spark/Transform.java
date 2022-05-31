package com.zyj.play.interview.questions.spark;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangyingjie
 * 比较常用的spark transformation
 * map filter flatmap groupbykey reducebykey sortbykey join cogroup combineByKey aggregateByKey makerdd和parallelize之间的区别
 */
@Slf4j
public class Transform {
    //创建SparkConf 和 SparkContext 对象。
    public static JavaSparkContext getContext() {
        SparkConf conf = new SparkConf()
                .setAppName("Transform")
                .setMaster("local");
        conf.set("spark.driver.bindAddress", "127.0.0.1");
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }

    //关闭 SparkContext 对象。
    public static void closeContext(JavaSparkContext sc) {
        if (sc != null) {
            sc.close();
        }
    }

    public static void main(String[] args) {

//准备测试数据
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<String> text = Arrays.asList("cat,dog,rabbit", "apple,pear,peach", "eyes,nose,mouth");
        List<Tuple2<String, Integer>> scores = Arrays.asList(
                new Tuple2<String, Integer>("class1", 1),
                new Tuple2<String, Integer>("class1", 2),
                new Tuple2<String, Integer>("class1", 4),
                new Tuple2<String, Integer>("class2", 3),
                new Tuple2<String, Integer>("class2", 1),
                new Tuple2<String, Integer>("class2", 5)

        );
        List<Tuple2<Integer, String>> students = Arrays.asList(
                new Tuple2<Integer, String>(1, "s1"),
                new Tuple2<Integer, String>(2, "s2"),
                new Tuple2<Integer, String>(3, "s3"),
                new Tuple2<Integer, String>(4, "s4")
        );
        List<Tuple2<Integer, Integer>> stuScores = Arrays.asList(
                new Tuple2<Integer, Integer>(1, 100),
                new Tuple2<Integer, Integer>(2, 98),
                new Tuple2<Integer, Integer>(3, 98),
                new Tuple2<Integer, Integer>(3, 99),
                new Tuple2<Integer, Integer>(2, 99)
        );

        ArrayList<ScoreDetail> scoreDetails = new ArrayList<>();
        scoreDetails.add(new ScoreDetail("xiaoming", "math", 90f));
        scoreDetails.add(new ScoreDetail("xiaoming", "English", 80f));
        scoreDetails.add(new ScoreDetail("xiaohong", "math", 70f));
        scoreDetails.add(new ScoreDetail("xiaohong", "English", 60f));
        scoreDetails.add(new ScoreDetail("xiaozhang", "math", 50f));
        scoreDetails.add(new ScoreDetail("xiaozhang", "English", 40f));

//拿到 SparkContext 对象
        JavaSparkContext sc = getContext();
//测试 Transformation 方法：
//        mapDemo(sc, numbers);
//        filterDemo(sc, numbers);
//        flatMapDemo(sc, text);
//        groupByKeyDemo(sc,scores);
//        reduceByKeyDemo(sc,scores);
//        sortByKeyDemo(sc,scores);
//        joinDemo(sc, students, stuScores);
//        cogroupDemo(sc, students, stuScores);
//        aggregateByKeyDemo(sc, scores);
//        combineByKeyDemo(sc, scoreDetails);
//        combineByKeyWc(sc);
//        closeContext(sc);
    }

    //调用 map 算子实现功能：将集合中的每个元素乘以 2 .
    public static void mapDemo(JavaSparkContext sc, List<Integer> numbers) {
        JavaRDD<Integer> rdd = sc.parallelize(numbers, 1);
        JavaRDD<Integer> doubledNumbers = rdd.map((Function<Integer, Integer>) v1 -> v1 * 2);

        doubledNumbers.foreach((VoidFunction<Integer>) integer -> System.out.println("map::: " + integer));
    }

    public static void filterDemo(JavaSparkContext sc, List<Integer> numbers) {
        JavaRDD<Integer> rdd = sc.parallelize(numbers, 1);
        JavaRDD<Integer> filter = rdd.filter((Function<Integer, Boolean>) v1 -> v1 % 2 == 0);
        filter.foreach((VoidFunction<Integer>) integer -> System.out.println("filter: " + integer));
    }


    //调用 flatMap 算子实现功能：将每个字符串拆分成单个的单词。
    public static void flatMapDemo(JavaSparkContext sc, List<String> text) {
        JavaRDD<String> rdd = sc.parallelize(text);
        JavaRDD<String> words = rdd.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(",")).iterator());

        words.foreach((VoidFunction<String>) word -> System.out.println("flatMap::" + word));
    }

    //调用 groupByKey 算子实现功能：根据班级分组，将同一个班级的分数归为一组。
    public static void groupByKeyDemo(JavaSparkContext sc, List<Tuple2<String, Integer>> scores) {
        JavaPairRDD<String, Integer> lists = sc.parallelizePairs(scores);
        JavaPairRDD<String, Iterable<Integer>> groupedScores = lists.groupByKey();
        groupedScores.foreach((VoidFunction<Tuple2<String, Iterable<Integer>>>) scores1 -> {
            System.out.println(scores1._1);
            Iterator<Integer> iterator = scores1._2.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
            System.out.println("========================================");
        });
    }

    //调用 reduceByKey 算子实现功能：计算每个班级分数总和。
    public static void reduceByKeyDemo(JavaSparkContext sc, List<Tuple2<String, Integer>> scores) {
        JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(scores);
        JavaPairRDD<String, Integer> reducedScores = rdd.reduceByKey((Function2<Integer, Integer, Integer>) Integer::sum);

        reducedScores.foreach((VoidFunction<Tuple2<String, Integer>>) scores1 -> System.out.println(scores1._1 + " : " + scores1._2));
    }

    //调用 sortedByKey 算子实现功能：按照分数做升序排序。
    public static void sortByKeyDemo(JavaSparkContext sc, List<Tuple2<String, Integer>> scores) {
        JavaPairRDD<String, Integer> rdd = sc.parallelizePairs(scores);
        //因为是要根据分数排序，而原始数据的key是class，所以将key和value临时调换一下。
        JavaPairRDD<Integer, String> swapedRdd = rdd.mapToPair((PairFunction<Tuple2<String, Integer>, Integer, String>) pair -> new Tuple2<Integer, String>(pair._2, pair._1));
        //根据现在的key（分数）升序排序。
        JavaPairRDD<Integer, String> sortedRdd = swapedRdd.sortByKey();
        //排序完成后，还是要按照原始数据的key和value来保存，所以再把key和value调换回来。
        JavaPairRDD<String, Integer> result = sortedRdd.mapToPair((PairFunction<Tuple2<Integer, String>, String, Integer>) pair -> new Tuple2<String, Integer>(pair._2, pair._1));

        result.foreach((VoidFunction<Tuple2<String, Integer>>) pairs -> System.out.println(pairs._1 + " : " + pairs._2));
    }

    //调用 join 算子实现功能：将两个RDD的元素按照key做连接。
    public static void joinDemo(JavaSparkContext sc, List<Tuple2<Integer, String>> students, List<Tuple2<Integer, Integer>> stuScores) {
        JavaPairRDD<Integer, String> stuRdd = sc.parallelizePairs(students);
        JavaPairRDD<Integer, Integer> scoreRdd = sc.parallelizePairs(stuScores);

        JavaPairRDD<Integer, Tuple2<String, Integer>> lists = stuRdd.join(scoreRdd);

        lists.foreach((VoidFunction<Tuple2<Integer, Tuple2<String, Integer>>>) pairs -> System.out.println(pairs._1 + " : " + pairs._2._1 + " : " + pairs._2._2));
    }

    //调用 cogroup 算子实现功能：将两个RDD的元素按照key做连接。 它跟join实现的功能是一样的，但是它们的返回值不同。
    public static void cogroupDemo(JavaSparkContext sc, List<Tuple2<Integer, String>> students, List<Tuple2<Integer, Integer>> stuScores) {
        JavaPairRDD<Integer, String> stuRdd = sc.parallelizePairs(students);
        JavaPairRDD<Integer, Integer> scoreRdd = sc.parallelizePairs(stuScores);
        JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> cogroupedRdd = stuRdd.cogroup(scoreRdd);
        cogroupedRdd.foreach(new VoidFunction<Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>>() {
            @Override
            public void call(Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>> pairs) throws Exception {
                log.error(pairs._1 + " : (" + pairs._2._1 + " : " + pairs._2._2 + ")");
            }
        });
//        cogroupedRdd.foreach((VoidFunction<Tuple2<Integer, Tuple2<Iterable<String>, Iterable<Integer>>>>) pairs ->
//                System.out.println(pairs._1 + " : " + pairs._2._1 + " : " + pairs._2._2));
    }

    /**
     * 第一个参数是：每个key的初始值
     * 第二个参数是： Seq Function， 经测试这个函数就是用来先对每个分区内的数据按照key分别进行函数定义的操作
     * 第三个是个函数， Combiner Function， 对经过 Seq Function 处理过的数据按照key分别进行函数定义的操作
     * 还是和运用的结果看不懂的话可以看这篇博客
     * https://blog.csdn.net/qq_35440040/article/details/82691794
     *
     * @param sc
     * @param student
     */
    public static void aggregateByKeyDemo(JavaSparkContext sc, List<Tuple2<String, Integer>> student) {
        JavaPairRDD<String, Integer> rdd1 = sc.parallelizePairs(student, 3);
        rdd1.mapPartitionsWithIndex(new Function2<Integer, Iterator<Tuple2<String, Integer>>, Iterator<String>>() {
            @Override
            public Iterator<String> call(Integer v1, Iterator<Tuple2<String, Integer>> v2) throws Exception {
                ArrayList<String> list = new ArrayList<>();
                while (v2.hasNext()) {
                    list.add(v2.next() + " in " + (v1 + 1) + " partition");
                }
                return list.iterator();
            }
        }, true).foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                log.error("s=={}", s);
            }
        });
//                .foreach(System.out::println);

        JavaPairRDD<String, Integer> abkrdd2 = rdd1.aggregateByKey(0,
                new Function2<Integer, Integer, Integer>() {
                    //在通一个分区中将分区中相同的key的value都和初始值相比较，注意必须是相同的key值，将最大的值保留。然后将每个分区中保留的的最大值，传入给下个函数进行操作。
                    @Override
                    public Integer call(Integer s, Integer v) throws Exception {
                        System.out.println("seq: " + s + "," + v);
//                        return s + v;
                        return Math.max(s, v);
                    }
                    //将每个分区内key相同的value相加，返回结果。
                }, new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer s, Integer v) throws Exception {
                        System.out.println("com:" + s + "," + v);
                        return s + v;
                    }
                });

        abkrdd2.foreach(new VoidFunction<Tuple2<String, Integer>>() {
            @Override
            public void call(Tuple2<String, Integer> s) throws Exception {
                log.error("c:" + s._1 + ",v:" + s._2);
//                System.out.println("c:" + s._1 + ",v:" + s._2);
            }
        });
    }

    /**
     * combineByKey() 会遍历分区中的所有元素，因此每个元素的键要么还没有遇到过，要么就和之前的某个元素的键相同。如果这是一个新的元素，
     * combineByKey() 第一个参数：会使用一个叫作 createCombiner() 的函数来创建 ,那个键对应的累加器的初始值
     * mergeValue: 第二个参数： 如果这是一个在处理当前分区之前已经遇到的键， 它会使用 mergeValue() 方法将该键的累加器对应的当前值与这个新的值进行合并。
     * mergeCombiners:第三个参数：由于每个分区都是独立处理的， 因此对于同一个键可以有多个累加器。如果有两个或者更多的分区都有对应同一个键的累加器。
     * 如果不是很懂的话，可以看一下这篇博客
     *
     * @param sc
     * @param scoreDetails
     */
    public static void combineByKeyDemo(JavaSparkContext sc, List<ScoreDetail> scoreDetails) {
        JavaRDD<ScoreDetail> rdd1 = sc.parallelize(scoreDetails, 3);
        JavaRDD<String> stringJavaRDD = rdd1.mapPartitionsWithIndex(new Function2<Integer, Iterator<ScoreDetail>, Iterator<String>>() {
            @Override
            public Iterator<String> call(Integer v1, Iterator<ScoreDetail> v2) throws Exception {
                ArrayList<String> list = new ArrayList<>();
                while (v2.hasNext()) {
                    list.add(v2.next() + " in " + (v1 + 1) + " partition");
                }
                return list.iterator();
            }
        }, true);
        stringJavaRDD.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                log.error("s==={}", s);
            }
        });

        JavaPairRDD<String, ScoreDetail> rdd2 = rdd1.mapToPair((PairFunction<ScoreDetail, String, ScoreDetail>) scoreDetail -> new Tuple2<>(scoreDetail.name, scoreDetail));
        JavaPairRDD<String, Tuple2<Float, Integer>> rdd3 = rdd2.combineByKey(new Function<ScoreDetail, Tuple2<Float, Integer>>() {
            @Override
            public Tuple2<Float, Integer> call(ScoreDetail v1) throws Exception {
                return new Tuple2<>(v1.score, 1);
            }
        }, new Function2<Tuple2<Float, Integer>, ScoreDetail, Tuple2<Float, Integer>>() {
            @Override
            public Tuple2<Float, Integer> call(Tuple2<Float, Integer> v1, ScoreDetail v2) throws Exception {
                return new Tuple2<>(v1._1 + v2.score, v1._2 + 1);
            }
        }, new Function2<Tuple2<Float, Integer>, Tuple2<Float, Integer>, Tuple2<Float, Integer>>() {
            @Override
            public Tuple2<Float, Integer> call(Tuple2<Float, Integer> v1, Tuple2<Float, Integer> v2) throws Exception {
                return new Tuple2<>(v1._1 + v2._1, v1._2 + v2._2);
            }
        });
//        JavaPairRDD<String, Tuple2<Float, Integer>> rdd3 = rdd2.combineByKey(
//                (Function<ScoreDetail, Tuple2<Float, Integer>>) v1 -> new Tuple2<>(v1.score, 1),
//                (Function2<Tuple2<Float, Integer>, ScoreDetail, Tuple2<Float, Integer>>) (v1, v2) -> new Tuple2<>(v1._1 + v2.score, v1._2 + 1),
//                (Function2<Tuple2<Float, Integer>, Tuple2<Float, Integer>, Tuple2<Float, Integer>>) (v1, v2) -> new Tuple2<>(v1._1 + v2._1, v1._2 + v2._2));
        System.out.println(rdd3.collect());
    }

    public static void parallelizeAndMarkrdd(JavaSparkContext sc, List<Tuple2<String, Integer>> student) {
        //mark rdd 只有在jsc(JavaSparkContext)是不具备这个方法的  它只在sc(SparkContext)中有这个方法
        JavaRDD<Tuple2<String, Integer>> rdd = sc.parallelize(student);
        JavaPairRDD<String, Integer> stringIntegerJavaPairRDD = sc.parallelizePairs(student);
    }

    //通过combineByKey实现wordCount程序
    public static void combineByKeyWc(JavaSparkContext sc) {
        List<String> list = Arrays.asList("hello spark", "hello java", "hello flink", "hello flink", "hello java", "hello spark");
        JavaRDD<String> parallelize = sc.parallelize(list);
        JavaRDD<String> rdd1 = parallelize.flatMap(line -> Arrays.stream(line.split(",")).iterator());
        JavaPairRDD<String, Integer> rdd2 = rdd1.mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1));
        JavaPairRDD<String, Integer> rdd3 = rdd2.combineByKey((Function<Integer, Integer>) v1 -> 1, (Function2<Integer, Integer, Integer>) Integer::sum, (Function2<Integer, Integer, Integer>) Integer::sum);
        rdd3.foreach((VoidFunction<Tuple2<String, Integer>>) v1 -> System.out.println("word: " + v1._1 + " count: " + v1._2));
    }
}
