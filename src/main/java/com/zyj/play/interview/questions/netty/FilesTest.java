package com.zyj.play.interview.questions.netty;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * File 工具省略写 递归遍历工具
 */
@Slf4j
public class FilesTest {
    public static void main(String[] args) throws IOException, InterruptedException {
//        ThreadTest();
        nettyTest();
    }

    public static void listFile() throws IOException {
        Files.walkFileTree(Paths.get("/Library/Java/JavaVirtualMachines/jdk1.8.0_301.jdk/Contents/Home"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("dir=={}" + dir.getFileName());
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("file=={}" + file.getFileName());
                return super.visitFile(file, attrs);
            }
        });
    }

    public static void channel() throws IOException {
        FileChannel channel = new RandomAccessFile("", "").getChannel();
        ServerSocketChannel open = ServerSocketChannel.open();
        open.configureBlocking(false);
    }

    public static void ThreadTest() throws IOException, InterruptedException {
        BlackTest blackTest = new BlackTest();
        blackTest.register();
        ServerSocketChannel open = ServerSocketChannel.open();
        open.register(blackTest.selector, SelectionKey.OP_READ, null);
        System.out.println("selector =={}" + blackTest.selector);
        System.out.println("ahahahahahahahahahahahahahaahahahah");
        System.out.println("打印出结果值=={}" + blackTest.a);
    }

    static class BlackTest implements Runnable {
        private int a;
        private int b;
        private Thread thread;
        private Selector selector;


        private void register() throws IOException {
            this.thread = new Thread(this);
            this.a = 10;
            this.selector = Selector.open();
            thread.start();

        }

        @SneakyThrows
        @Override
        public void run() {
            selector.select();
            System.out.println("睡一段时间。。");
            Thread.sleep(120000000);
            System.out.println("睡好了");
        }
    }

    public static void nettyTest() {
        EventLoopGroup eventExecutors = new DefaultEventLoopGroup(2);
        eventExecutors.next().execute(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.error(Thread.currentThread().getName());
        });

        log.error("debug=={}", Thread.currentThread().getName());
    }
}
