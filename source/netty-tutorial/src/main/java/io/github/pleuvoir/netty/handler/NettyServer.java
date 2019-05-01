package io.github.pleuvoir.netty.handler;

import java.util.Date;

import io.github.pleuvoir.netty.handler.in.InBoundHandlerA;
import io.github.pleuvoir.netty.handler.in.InBoundHandlerB;
import io.github.pleuvoir.netty.handler.in.InBoundHandlerC;
import io.github.pleuvoir.netty.handler.out.OutBoundHandlerA;
import io.github.pleuvoir.netty.handler.out.OutBoundHandlerB;
import io.github.pleuvoir.netty.handler.out.OutBoundHandlerC;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {

    private static final int PORT = 9000;

    public static void main(String[] args) {
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) {
                        // inBound，处理读数据的逻辑链
                        ch.pipeline().addLast("in-A",new InBoundHandlerA());
                        ch.pipeline().addLast("in-B",new InBoundHandlerB());
                        ch.pipeline().addLast("out-Te",new OutBoundHandlerB());
                        ch.pipeline().addLast("in-C",new InBoundHandlerC());

                        // outBound，处理写数据的逻辑链
                        ch.pipeline().addLast("out-A",new OutBoundHandlerA());
                        ch.pipeline().addLast("out-B",new OutBoundHandlerB());
                        ch.pipeline().addLast("out-C",new OutBoundHandlerC());
                    }
                });


        bind(serverBootstrap, PORT);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
