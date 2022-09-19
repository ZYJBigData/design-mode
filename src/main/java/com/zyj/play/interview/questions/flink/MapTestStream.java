package com.zyj.play.interview.questions.flink;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.TypeSerializer;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.connectors.kafka.KafkaDeserializationSchema;
import org.apache.flink.streaming.connectors.kafka.internals.AbstractFetcher;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaConsumerThread;
import org.apache.flink.streaming.connectors.kafka.internals.KafkaFetcher;
import org.apache.flink.streaming.runtime.operators.CheckpointCommitter;
import org.apache.flink.streaming.runtime.operators.GenericWriteAheadSink;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.internals.SubscriptionState;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.IsolationLevel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class MapTestStream {
    public static String groupId = "zyj-in";
    public static String topicNameOut = "zyj-out";

    public static void main(String[] args) throws Exception {
        Properties prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.90.74:9000,10.0.90.74:9000,10.0.90.74:9000");
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        ParameterTool parameters = ParameterTool.fromArgs(args);
        String topic = parameters.get("topic", "zyj-in");
        String checkpoint = parameters.get("checkpoint", "true");
        String name = parameters.get("name", "Lag_Test");
        FlinkKafkaConsumer<String> kafkaConsumer =
                new FlinkKafkaConsumer<>(topic, new SimpleStringSchema(), prop);
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = env.addSource(kafkaConsumer);
        SingleOutputStreamOperator<String> outputStreamOperator = dataStreamSource.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                out.collect(value);
                log.error("value=={}" + value);
                Thread.sleep(30000);
            }
        });
        FlinkKafkaProducer<String> sink = new FlinkKafkaProducer<>(topicNameOut, new SimpleStringSchema(), prop);
        outputStreamOperator.addSink(sink);
        env.execute(name);
    }

    public static class CustomerJsonDeserialization implements KafkaDeserializationSchema<ObjectNode> {
        private String DT_TOPIC_GROUP = "topic";
        private String DT_PARTITION_GROUP = "partition";
        private String DT_TOPIC_PARTITION_LAG_GAUGE = "topic_partition_lag";
        private Counter inCounter;
        private Counter outCounter;
        boolean firstMsg = true;

        private AbstractFetcher<Row, ?> fetcher;
        private ObjectMapper mapper;
        private final boolean includeMetadata;
        private RuntimeContext runtimeContext;
        private String confName;

        public CustomerJsonDeserialization(boolean includeMetadata, String confName) {
            this.includeMetadata = includeMetadata;
            this.confName = confName;
        }

        public void initMetric() {
            this.inCounter =
                    runtimeContext.getMetricGroup()
                            .addGroup("web-streaming")
                            .counter(this.confName + "-" + "in-count");

            this.outCounter =
                    runtimeContext.getMetricGroup().addGroup("web-streaming").counter(this.confName + "-" + "out-count");

        }

        @Override
        public ObjectNode deserialize(ConsumerRecord<byte[], byte[]> record) throws Exception {
            inCounter.inc();
            if (firstMsg) {
                // 只有在第一条数据到来的时候，才会调用该方法
                registerPtMetric(fetcher);

                firstMsg = false;
            }
            if (mapper == null) {
                mapper = new ObjectMapper();
            }
            ObjectNode node = mapper.createObjectNode();
            if (record.key() != null) {
                node.set("key", mapper.readValue(record.key(), JsonNode.class));
            }
            if (record.value() != null) {
                node.set("value", mapper.readValue(record.value(), JsonNode.class));
            }
            if (includeMetadata) {
                node.putObject("metadata")
                        .put("offset", record.offset())
                        .put("topic", record.topic())
                        .put("partition", record.partition());
            }
            outCounter.inc();
            return node;
        }

        @Override
        public boolean isEndOfStream(ObjectNode jsonNodes) {
            return false;
        }

        @Override
        public TypeInformation<ObjectNode> getProducedType() {
            return null;
        }


        public void setFetcher(AbstractFetcher<Row, ?> fetcher) {
            this.fetcher = fetcher;
        }

        public void setRuntimeContext(RuntimeContext runtimeContext) {
            this.runtimeContext = runtimeContext;
        }

        protected void registerPtMetric(AbstractFetcher<Row, ?> fetcher) throws Exception {
            // 通过反射获取fetcher中的kafka消费者等信息, 反射获取属性路径如下：
            // Flink: Fetcher -> KafkaConsumerThread -> KafkaConsumer ->
            // Kafka Consumer: KafkaConsumer -> SubscriptionState -> partitionLag()
            Field consumerThreadField = ((KafkaFetcher) fetcher).getClass().getDeclaredField("consumerThread");

            consumerThreadField.setAccessible(true);
            KafkaConsumerThread consumerThread = (KafkaConsumerThread) consumerThreadField.get(fetcher);

            Field hasAssignedPartitionsField = consumerThread.getClass().getDeclaredField("hasAssignedPartitions");
            hasAssignedPartitionsField.setAccessible(true);

            boolean hasAssignedPartitions = (boolean) hasAssignedPartitionsField.get(consumerThread);
            if (!hasAssignedPartitions) {
                throw new RuntimeException("wait 50 secs, but not assignedPartitions");
            }
            Field consumerField = consumerThread.getClass().getDeclaredField("consumer");
            consumerField.setAccessible(true);
            KafkaConsumer kafkaConsumer = (KafkaConsumer) consumerField.get(consumerThread);
            Field subscriptionStateField = kafkaConsumer.getClass().getDeclaredField("subscriptions");
            subscriptionStateField.setAccessible(true);
            SubscriptionState subscriptionState = (SubscriptionState) subscriptionStateField.get(kafkaConsumer);
            Set<TopicPartition> assignedPartitions = subscriptionState.assignedPartitions();
            for (TopicPartition topicPartition : assignedPartitions) {
                System.out.println(DT_TOPIC_GROUP + " = " + topicPartition.topic());
                System.out.println(DT_PARTITION_GROUP + " = " + topicPartition.partition());
//                System.out.println(DT_TOPIC_PARTITION_LAG_GAUGE+"="+(Gauge<Long>) () ->
//                                subscriptionState.partitionLag(topicPartition, IsolationLevel.READ_UNCOMMITTED));
//                runtimeContext.getMetricGroup().addGroup(DT_TOPIC_GROUP, topicPartition.topic())
//                        .addGroup(DT_PARTITION_GROUP, topicPartition.partition() + "")
//                        .gauge(DT_TOPIC_PARTITION_LAG_GAUGE, (Gauge<Long>) () ->
//                                subscriptionState.partitionLag(topicPartition, IsolationLevel.READ_UNCOMMITTED));

            }
        }
    }

    public class KafkaTopicPartitionLagMetric implements Gauge<Long> {

        private SubscriptionState subscriptionState;

        private TopicPartition tp;

        public KafkaTopicPartitionLagMetric(SubscriptionState subscriptionState, TopicPartition tp) {
            this.subscriptionState = subscriptionState;
            this.tp = tp;
        }

        @Override
        public Long getValue() {
            // 计算消费延迟
            Long result = null;
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                Class<?> clazz = classLoader.loadClass("org.apache.kafka.clients.consumer.internals.SubscriptionState");
                SubscriptionState subscriptionState = (SubscriptionState) clazz.newInstance();
                Method method = clazz.getDeclaredMethod("partitionLag", TopicPartition.class, IsolationLevel.class);
                method.setAccessible(true);
                result = (Long) method.invoke(subscriptionState, tp, IsolationLevel.READ_UNCOMMITTED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }
    

    public static class test extends GenericWriteAheadSink {
        public test(CheckpointCommitter committer, TypeSerializer serializer, String jobID) throws Exception {
            super(committer, serializer, jobID);
        }

        @Override
        protected boolean sendValues(Iterable values, long checkpointId, long timestamp) throws Exception {
            return false;
        }
    }

    public static class test1 extends TwoPhaseCommitSinkFunction {

        /**
         * Use default {@link ListStateDescriptor} for internal state serialization. Helpful utilities
         * for using this constructor are {@link TypeInformation#of(Class)}, {@link
         * TypeHint} and {@link TypeInformation#of(TypeHint)}.
         * Example:
         *
         * <pre>{@code
         * TwoPhaseCommitSinkFunction(TypeInformation.of(new TypeHint<State<TXN, CONTEXT>>() {}));
         * }</pre>
         *
         * @param transactionSerializer {@link TypeSerializer} for the transaction type of this sink
         * @param contextSerializer     {@link TypeSerializer} for the context type of this sink
         */
        public test1(TypeSerializer transactionSerializer, TypeSerializer contextSerializer) {
            super(transactionSerializer, contextSerializer);
        }

        @Override
        protected void invoke(Object transaction, Object value, Context context) throws Exception {
            
        }

        @Override
        protected Object beginTransaction() throws Exception {
            return null;
        }

        @Override
        protected void preCommit(Object transaction) throws Exception {

        }

        @Override
        protected void commit(Object transaction) {

        }

        @Override
        protected void abort(Object transaction) {

        }
    }
//    mapWithState( fd
                                      
                                      
}
