package com.zyj.play.interview.questions.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO 阻塞式的IO模型,当多个客户端进行连接的时候，必须用多个线程
 */
@Slf4j
public class BioServer {
    public static void main(String[] args) {
        final String QUIT = "quit";
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(6767);
            System.out.println("服务器已启动，监听端口：" + 6767);
            while (true) {
                Socket accept = serverSocket.accept();
                System.out.println("客户端" + accept.getPort() + ":" + "已经连接");
                new Thread(
                        () -> {
                            log.info("当前运行的线程是：={}" ,Thread.currentThread().getName());
                            System.out.println("当前运行的线程是：" + Thread.currentThread().getName());
                            try {
                                //获取输入字符流
                                BufferedReader reader = new BufferedReader(new InputStreamReader(
                                        accept.getInputStream()
                                ));
                                //获取输出字符流
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                                        accept.getOutputStream()
                                ));

                                String msg;
                                while ((msg = reader.readLine()) != null) {
                                    //接收消息
                                    System.out.println("client[" + accept.getPort() + "]:" + msg);

                                    //转发到Client,
                                    writer.write("我已经收到：" + msg + "\n");
                                    writer.flush();

                                    if (msg.equals(QUIT)) {
                                        System.out.println("client close!");
                                        break;
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                ).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}