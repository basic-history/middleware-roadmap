package io.github.pleuvoir.netty.echo.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println("启动服务端 ...");
		EchoServer echoServer = new EchoServer(9000);
		echoServer.start();
		System.out.println("服务端关闭 ...");
	}

	private void start() throws InterruptedException {

		// 线程组
		EventLoopGroup group = new NioEventLoopGroup();

		final EchoServerHandler echoServerHandler = new EchoServerHandler();
		
		try {
			// 服务端启动必备
			ServerBootstrap bootstrap = new ServerBootstrap();

			bootstrap.group(group).channel(NioServerSocketChannel.class) 	// 指明使用NIO进行网络通讯
					.localAddress(new InetSocketAddress(port))
					.childHandler(new ChannelInitializer<Channel>() {		// 接收到连接请求，新启一个 socket 通信，也就是channel，这里可以选择new 一个或者 shared
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ch.pipeline().addLast(echoServerHandler);  //@Sharable所以我们总是可以使用相同的实例而不需要new
						//	ch.pipeline().addLast(new EchoServerHandler());
						}
					});
				
			//这里的sync保证channel初始化完成，即不为空，否则下面的f.channel().localAddress()可能会为null
			ChannelFuture f = bootstrap.bind().sync();	// 绑定到端口，阻塞等待直到连接完成，这里 ChannelFuture 就像一个占位
			f.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
				}
			});
			
			f.channel().closeFuture().sync();	// 阻塞，直到 channel 关闭
			 System.out.println(EchoServer.class.getName() +
		                " started and listening for connections on " + f.channel().localAddress());
		} finally {
			group.shutdownGracefully().sync(); // 优雅停机
		}

	}

}
