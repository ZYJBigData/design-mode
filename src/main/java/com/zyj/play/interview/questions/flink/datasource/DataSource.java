package com.zyj.play.interview.questions.flink.datasource;

import org.apache.flink.api.java.tuple.Tuple3;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangyingjie
 */
public class DataSource {
    public static List<Tuple3<String, String, Integer>> getTuple3ToList() {
        return Arrays.asList(new Tuple3<>("张三", "man", 20),
                new Tuple3<>("张三", "man", 20),
                new Tuple3<>("李四", "girl", 24),
                new Tuple3<>("王五", "man", 29),
                new Tuple3<>("刘六", "girl", 32),
                new Tuple3<>("伍七", "girl", 18),
                new Tuple3<>("吴八", "man", 30),
                new Tuple3<>("牛九", "middle", 45)
        );
    }

//    public static List<Tuple3<String, Long,Integer>> getTuple2List() {
//        return Arrays.asList(new Tuple3<>("key", 1623808800000L,1),
//                new Tuple3<>("key1", 1623808803000L,2),
//                new Tuple3<>("key1", 1623808805000L,3),
//                new Tuple3<>("key1", 1623808812000L,4),
//                new Tuple3<>("key", 1623808815000L,5444),
//                new Tuple3<>("key", 1623808824000L,6),
//                new Tuple3<>("key", 1623808830000L,7),
//                new Tuple3<>("key", 1623808842000L,8)
//        );
//    }


    public static List<Tuple3<String, Long, Integer>> getTuple2List() {
        return Arrays.asList(new Tuple3<>("key", 1623808800000L, 1),
                new Tuple3<>("key", 1623808803000L, 2),
                new Tuple3<>("key", 1623808805000L, 3),
                new Tuple3<>("key", 1623808812000L, 4),
                new Tuple3<>("key", 1623808815000L, 5444),
                new Tuple3<>("key", 1623808824000L, 6),
                new Tuple3<>("key", 1623808830000L, 6),
                new Tuple3<>("key", 1623808844000L, 6),
                new Tuple3<>("key", 1623808850000L, 7),
                new Tuple3<>("key", 1623808842000L, 8),
                new Tuple3<>("key", 1623808841000L, 8),
                new Tuple3<>("key", 1623808835000L, 8)
        );
    }

    public static List<Tuple3<Long, String, Integer>> getTuple2List_test() {
        return Arrays.asList(
                new Tuple3<>(1623808803000L, "man", 20),
                new Tuple3<>(1623808804000L, "man", 10),

                new Tuple3<>(1623808812000L, "man", 20),
                new Tuple3<>(1623808815000L, "man", 30),

                new Tuple3<>(1623808824000L, "man", 40),
                new Tuple3<>(1623808830000L, "man", 60),
                new Tuple3<>(1623808844000L, "man", 50),
                new Tuple3<>(1623808850000L, "man", 60),
                new Tuple3<>(1623808842000L, "man", 70),
                new Tuple3<>(1623808841000L, "man", 70),
                new Tuple3<>(1623808835000L, "man", 70),
                new Tuple3<>(1623808824000L, "man", 40),
                new Tuple3<>(1623808830000L, "man", 60),
                new Tuple3<>(1623808844000L, "man", 50),
                new Tuple3<>(1623808850000L, "man", 60),
                new Tuple3<>(1623808842000L, "man", 70),
                new Tuple3<>(1623808841000L, "man", 70),
                new Tuple3<>(1623808835000L, "man", 70),

                new Tuple3<>(1623808811000L, "man", 70),
                new Tuple3<>(1623808811100L, "man", 70),
                new Tuple3<>(1623808811200L, "man", 70)
        );
    }
}
