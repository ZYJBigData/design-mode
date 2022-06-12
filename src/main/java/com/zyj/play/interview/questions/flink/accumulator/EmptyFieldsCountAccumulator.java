package com.zyj.play.interview.questions.flink.accumulator;

import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.Accumulator;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmptyFieldsCountAccumulator {
    private static final String EMPTY_FIELD_ACCUMULATOR = "empty-fields";

    public static void main(String[] args) throws Exception {
        ParameterTool params = ParameterTool.fromArgs(args);
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 1. 得到数据集
        final DataSet<StringTriple> file = getDataSet(env, params);
        // 2. 过滤含有空值的行
        final DataSet<StringTriple> filteredLines = file.filter(new EmptyFieldFilter());
        JobExecutionResult result;
        // 3. 执行任务并输出过滤行
        if (params.has("output")) {
            filteredLines.writeAsCsv(params.get("output"));
            // 执行程序
            result = env.execute("Accumulator example");
        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            filteredLines.print();
            result = env.getLastJobExecutionResult();
        }
        // 4. 通过注册时的key值来获得累加器的结果
        final List<Integer> emptyFields = result.getAccumulatorResult(EMPTY_FIELD_ACCUMULATOR);
        System.out.format("Number of detected empty fields per column: %s\n", emptyFields);

    }

    @SuppressWarnings("unchecked")
    private static DataSet<StringTriple> getDataSet(ExecutionEnvironment env, ParameterTool params) {
        // 如果指定了input参数
        if (params.has("input")) {
            return env.readCsvFile(params.get("input"))
                    .fieldDelimiter(";")
                    .pojoType(StringTriple.class);
            // 否则，读取默认的数据集
        } else {
            System.out.println("Executing EmptyFieldsCountAccumulator example with default input data set.");
            System.out.println("Use --input to specify file input.");
            return env.fromCollection(getExampleInputTuples());
        }
    }

    public static class StringTriple extends Tuple3<String, String, String> {
        public StringTriple() {
        }

        public StringTriple(String f0, String f1, String f2) {
            super(f0, f1, f2);
        }
    }

    public static final class EmptyFieldFilter extends RichFilterFunction<StringTriple> {

        // 在每个筛选函数实例中创建新的收集器
        // 以后可以合并累加器
        private final VectorAccumulator emptyFieldCounter = new VectorAccumulator();

        @Override
        public void open(final Configuration parameters) throws Exception {
            super.open(parameters);

            // 注册收集器实例
            getRuntimeContext().addAccumulator(EMPTY_FIELD_ACCUMULATOR,
                    this.emptyFieldCounter);
        }

        @Override
        public boolean filter(final EmptyFieldsCountAccumulator.StringTriple t) {
            boolean containsEmptyFields = false;

            // 遍历tuple的所有字段，寻找有没有空值
            for (int pos = 0; pos < t.getArity(); pos++) {

                final String field = t.getField(pos);
                if (field == null || field.trim().isEmpty()) {
                    containsEmptyFields = true;

                    // 如果遇到空字段，请更新累加器
                    this.emptyFieldCounter.add(pos);
                }
            }

            return !containsEmptyFields;
        }
    }

    public static class VectorAccumulator implements Accumulator<Integer, ArrayList<Integer>> {

        /**
         * 存储累积向量分量.
         */
        private final ArrayList<Integer> resultVector;

        /**
         * 构造函数
         */
        public VectorAccumulator() {
            this(new ArrayList<Integer>());
        }

        public VectorAccumulator(ArrayList<Integer> resultVector) {
            this.resultVector = resultVector;
        }

        /**
         * 将指定位置的结果向量分量增加1.
         */
        @Override
        public void add(Integer position) {
            updateResultVector(position, 1);
        }

        /**
         * 将指定位置的结果向量分量增加指定的增量。
         */
        private void updateResultVector(int position, int delta) {
            // 如果position超出了列的最大索引，那么就再起一列。
            while (this.resultVector.size() <= position) {
                this.resultVector.add(0);
            }

            // 增加该列的值，列索引为position
            final int component = this.resultVector.get(position);
            this.resultVector.set(position, component + delta);
        }

        @Override
        public ArrayList<Integer> getLocalValue() {
            return this.resultVector;
        }

        @Override
        public void resetLocal() {
            // 如果应重用收集器实例，则清除结果向量
            this.resultVector.clear();
        }

        @Override
        public void merge(final Accumulator<Integer, ArrayList<Integer>> other) {
            // 合并两个累加器 
            final List<Integer> otherVector = other.getLocalValue();
            for (int index = 0; index < otherVector.size(); index++) {
                updateResultVector(index, otherVector.get(index));
            }
        }

        @Override
        public Accumulator<Integer, ArrayList<Integer>> clone() {
            return new VectorAccumulator(new ArrayList<Integer>(resultVector));
        }

        @Override
        public String toString() {
            return StringUtils.join(resultVector, ',');
        }
    }

    /**
     *
     * 得到例子输入Tuple
     * @return
     */
    private static Collection<StringTriple> getExampleInputTuples() {
        Collection<StringTriple> inputTuples = new ArrayList<StringTriple>();
        inputTuples.add(new StringTriple("John", "Doe", "Foo Str."));
        inputTuples.add(new StringTriple("Joe", "Johnson", ""));
        inputTuples.add(new StringTriple(null, "Kate Morn", "Bar Blvd."));
        inputTuples.add(new StringTriple("Tim", "Rinny", ""));
        inputTuples.add(new StringTriple("Alicia", "Jackson", "  "));
        return inputTuples;
    }

}
