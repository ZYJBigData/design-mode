package com.zyj.play.interview.questions.flink.datasource.avro;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author zhangyingjie
 * flink 类自己带的avro序列化（发现用java的并不行）
 */
public class SimpleAvroSchemaFlink implements DeserializationSchema<UserBehavior>, SerializationSchema<UserBehavior> {

    @Override
    public UserBehavior deserialize(byte[] message) throws IOException {
        //用来存储结果
        UserBehavior userBehavior = new UserBehavior();
        //创建一个反序列化器
        SpecificDatumReader<UserBehavior> reader = new SpecificDatumReader<>(userBehavior.getSchema());
        //数据流 ，供数据存储
        ByteArrayInputStream in = new ByteArrayInputStream(message);
        //创建二进制解码器
        BinaryDecoder binaryDecoder = DecoderFactory.get().binaryDecoder(in, null);
        userBehavior = reader.read(null, binaryDecoder);
        return userBehavior;
    }

    @Override
    public byte[] serialize(UserBehavior userBehavior) {
        //创建序列化器
        SpecificDatumWriter<UserBehavior> writer = new SpecificDatumWriter<>(userBehavior.getSchema());
        //创建二进制流,用于存储
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建二进制编码器,为null工厂返回一个新的实例（二进制编码器），如果不为null,则重用一个编码器
        BinaryEncoder encoder = EncoderFactory.get().directBinaryEncoder(out, null);
        try {
            writer.write(userBehavior, encoder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    @Override
    public boolean isEndOfStream(UserBehavior nextElement) {
        return false;
    }

    @Override
    public TypeInformation<UserBehavior> getProducedType() {
        return TypeInformation.of(UserBehavior.class);
    }
}
