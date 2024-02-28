package com.zyj.play.design.mode.factorypattern.test;


public class GetValue {
    public static String outputToChrome(QueryFunction queryFunction, ProcessFunction processFunction, Query query) {
        return processFunction.processData(queryFunction.query(), query);
    }

    public static Integer outputToKafka(QueryFunction queryFunction, ProcessFunction processFunction, Query query) {
        return Integer.parseInt(processFunction.processData(queryFunction.query(), query));
    }
}
