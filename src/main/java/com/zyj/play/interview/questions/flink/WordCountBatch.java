package com.zyj.play.interview.questions.flink;

/**
 * @author yingjiezhang
 * 批任务读取文件内容
 */
public class WordCountBatch {
    public static void main(String[] args) throws Exception {
//        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//        env.setParallelism(1);
//        DataSet<String> dataStreamSource = env.readTextFile("/Users/yingjiezhang/test.txt");
//        dataStreamSource.flatMap(new FlatMapFunction<String, WordWithCount>() {
//            @Override
//            public void flatMap(String value, Collector<WordWithCount> out) throws Exception {
//                String[] splits = value.split("\\s");
//                for (String split : splits) {
//                    out.collect(new WordWithCount(split, 1L));
//                }
//            }
//            //将相同的key值 分配到不同的子任务
//        }).reduce(new ReduceFunction<WordWithCount>() {
//            @Override
//            public WordWithCount reduce(WordWithCount value1, WordWithCount value2) throws Exception {
//                return new WordWithCount(value1.getWord(), value1.count + value2.count);
//            }
//        }).print();
        Integer a=0;
        if (a<0){
            System.out.println("*****************");
        }
    }

}
