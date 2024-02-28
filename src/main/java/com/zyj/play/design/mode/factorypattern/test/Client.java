package com.zyj.play.design.mode.factorypattern.test;

public class Client {
    public static void main(String[] args) {
        //调用特别指定两个函数的值
        String s = GetValue.outputToChrome(new RollupQueryFunction(), new RollupProcessFunction(),new RollupProcessFunction.rollUpQueryParm());
        Integer integer = GetValue.outputToKafka(new RollupQueryFunction(), new RollupProcessFunction(),new RollupProcessFunction.rollUpQueryParm());
    
        //计算某个函数值
        String query = new RollupJobQueryFunction().query();
        String s1 = new RollupProcessFunction().processData(query,new RollupProcessFunction.rollUpQueryParm());
    }
}

