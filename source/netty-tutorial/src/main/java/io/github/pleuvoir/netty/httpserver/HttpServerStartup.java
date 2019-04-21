package io.github.pleuvoir.netty.httpserver;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServerStartup {

	public static void main(String[] args) throws InterruptedException {

		ServerBootstrap serverBootstrap = new ServerBootstrap();
		NioEventLoopGroup group = new NioEventLoopGroup();

		try {
			serverBootstrap
					.group(group)
					.channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY, true)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							pipeline.addLast(new HttpRequestDecoder()); //请求解码器
							pipeline.addLast(new HttpResponseEncoder()); //响应加码器
							pipeline.addLast(new HttpObjectAggregator(1024 * 1024 * 10)); //聚合 fullHttpequest 有可能传过来的不是完整的内容了，每次读取只读到一部分，所以要聚合
							pipeline.addLast(new HttpContentCompressor()); //压缩
							pipeline.addLast(new HttpServerCustomHandler()); //自己的处理类
						}
					});
			
			ChannelFuture cf = serverBootstrap.bind(new InetSocketAddress(4399)).sync();
			System.out.println("http server started @port " + 4399);
			cf.channel().closeFuture().sync();
			
		} finally {
			if (group != null) {
				group.shutdownGracefully();
			}
		}
	}
	
}
