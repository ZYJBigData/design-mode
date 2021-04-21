package com.zyj.play.interview.questions.jvm.oom;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * @author zhangyingjie
 * 这种错误的出现主要是基于Netty的出现 因为其底层的实现是NIO
 */
public class GcBufferMemory {
    public static void main(String[] args) throws IOException {
        System.out.println("配置的maxDirectMemory:" + sun.misc.VM.maxDirectMemory());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {//Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}

