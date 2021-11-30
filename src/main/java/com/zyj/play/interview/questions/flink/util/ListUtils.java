package com.zyj.play.interview.questions.flink.util;


import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author yingjiezhang
 */
public class ListUtils {

    //1、交集
    public static void main(String[] args) {
        String[] arrayA = new String[] { "1", "2", "3", "4"};
        String[] arrayB = new String[] { "3", "4", "5", "6" };
        List<String> listA = Arrays.asList(arrayA);
        List<String> listB = Arrays.asList(arrayB);

        Collection<String> intersection = CollectionUtils.intersection(listA, listB);
        System.out.println(intersection);
    }
}
