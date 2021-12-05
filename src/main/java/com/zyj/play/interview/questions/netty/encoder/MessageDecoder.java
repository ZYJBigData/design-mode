package com.zyj.play.interview.questions.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author yingjiezhang
 */
public class MessageDecoder extends ByteToMessageDecoder {

    //将字节转成对象
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {
        if (buf.readableBytes() < Contants.HEAD_LENGHT) {
            throw new CorruptedFrameException("包长度问题");
        }
        byte tag = buf.readByte();
        if (tag != Contants.PACKAGE_TAG) {
            throw new CorruptedFrameException("标志错误");
        }
        byte encode = buf.readByte();
        byte encrypt = buf.readByte();
        byte extend1 = buf.readByte();
        byte extend2 = buf.readByte();
        byte sessionByte[] = new byte[32];
        ByteBuf byteBuf1 = buf.readBytes(sessionByte);
        String sessionid = new String(sessionByte, StandardCharsets.UTF_8);
        int length = buf.readInt();
        int cammand = buf.readInt();
        Header header = new Header(tag, encode, encrypt, extend1, extend2, sessionid, length, cammand);
        byte[] data = new byte[length];
        buf.readBytes(data);
        Message message = new Message(header, new String(data, "UTF-8"));
        out.add(message);
    }
}
