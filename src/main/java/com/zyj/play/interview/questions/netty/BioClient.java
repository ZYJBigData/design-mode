package com.zyj.play.interview.questions.netty;

import java.io.*;
import java.net.Socket;

/**
 * client 端口
 */
public class BioClient {
    public static void main(String[] args) throws IOException {
        final String host = "127.0.0.1";
        final int port = 6767;
        final String QUIT = "quit";
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer = null;
        try {
            //建立socket连接
            socket = new Socket(host, port);

            //获取输入字符流
            reader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()
            ));

            //获取输出字符流
            writer = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream()
            ));

            //获取输入字符流
            BufferedReader scan = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String msg = scan.readLine();

                //向服务器发送消息
                writer.write(msg + "\n");
                writer.flush();

                //接收消息
                String line = reader.readLine();
                System.out.println("server：" + line);

                if (msg.equals(QUIT)) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(writer != null) {
                try {
                    writer.close();
                    System.out.println("Client close!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
