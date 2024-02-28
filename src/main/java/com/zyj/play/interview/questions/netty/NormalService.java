package com.zyj.play.interview.questions.netty;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NormalService {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.setReuseAddress(false);
        serverSocket.bind(new InetSocketAddress(8000));
        System.out.println("reuse address： " + serverSocket.getReuseAddress());
        while (true){
            Socket socket = serverSocket.accept();
            System.out.println("incoming socket.....");
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("hello world".getBytes());
            outputStream.close();
        }
    }
}
