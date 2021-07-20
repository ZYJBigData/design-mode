package com.zyj.play.interview.questions.flink.datasource.avro;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhangyingjie
 * java自定义序列化和反序列化,此类只是用来kafka自己玩的 未涉及到flink的东西
 */
public class SimpleAvroSchemaJava implements Serializer<UserBehavior>, Deserializer<UserBehavior> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    /**
     * 反序列化
     *
     * @param topic
     * @param data  要序列化二进制数据
     * @return 反序列化的对象
     */
    @Override
    public UserBehavior deserialize(String topic, byte[] data) {
        UserBehavior userBehavior = new UserBehavior();
        // 创建输入流用来读取二进制文件
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
        // 创建输入序列化执行器
        SpecificDatumReader<UserBehavior> stockSpecificDatumReader = new SpecificDatumReader<>(userBehavior.getSchema());
        //创建二进制解码器
        BinaryDecoder binaryDecoder = DecoderFactory.get().directBinaryDecoder(arrayInputStream, null);
        try {
            // 数据读取
            userBehavior = stockSpecificDatumReader.read(null, binaryDecoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 结果返回
        return userBehavior;
    }

    /**
     * 序列化
     *
     * @param topic
     * @param data  要序列化的对象
     * @return 序列化的结果数据
     */
    @Override
    public byte[] serialize(String topic, UserBehavior data) {
        //创建序列化执行器
        SpecificDatumWriter<UserBehavior> writer =
                new SpecificDatumWriter<>(data.getSchema());
        //创建一个流，用于存储序列化的二进制文件
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建一个二进制的编码器
        BinaryEncoder encoder = EncoderFactory.get().directBinaryEncoder(out, null);
        try {
            //数据都入流中
            writer.write(data, encoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    @Override
    public void close() {

    }
}
