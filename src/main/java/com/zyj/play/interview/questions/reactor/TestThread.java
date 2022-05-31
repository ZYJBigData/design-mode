package com.zyj.play.interview.questions.reactor;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.util.Scanner;

/**
 * @author yingjiezhang
 */
public class TestThread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ByteBuffer buf = ByteBuffer.allocate(1024);
                Scanner scanner = new Scanner(System.in);
                System.out.println("从终端中输入的数据是：、、、、");
                while (scanner.hasNext()) {
                    String inputStr = scanner.next();
                    buf.put(inputStr.getBytes());
                    buf.flip();
//                    try {
//                        socketChannel.write(buf);
//                        while (buf.remaining() > 0) {
//                            socketChannel.write(buf);
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    buf.clear();
                }
                scanner.close();
            }
        }).start();
    }
}
