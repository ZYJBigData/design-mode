package com.zyj.play.interview.questions.netty.nettyClient;

import com.zyj.play.interview.questions.netty.encoder.MessageDecoder;
import com.zyj.play.interview.questions.netty.encoder.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;

/**
 * @author yingjiezhang
 */
public class EchoClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws SSLException, InterruptedException {
        System.out.println("EchoClient.main");
        // Configure SSL.git
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        try {
            b.group(group)
                    .channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    if (sslCtx != null) {
                        p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                    }
                    //p.addLast(new LoggingHandler(LogLevel.INFO));
                    p.addLast(new MessageDecoder());
                    p.addLast(new MessageEncoder());
                    //p.addLast("encoder", new MessageEncoder());
                    //p.addLast("decoder", new MessageDecoder());
                    //p.addFirst(new LineBasedFrameDecoder(65535));
                    p.addLast(new EchoClientHandler());
                }
            });
            ChannelFuture f = b.connect(HOST, PORT).sync();
            System.out.println("EchoClient.main ServerBootstrap配置启动完成");

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            System.out.println("EchoClient.end");
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
