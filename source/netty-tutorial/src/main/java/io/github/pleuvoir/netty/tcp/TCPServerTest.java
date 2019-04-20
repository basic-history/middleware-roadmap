package io.github.pleuvoir.netty.tcp;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

public class TCPServerTest {

	public static void main(String[] args) throws InterruptedException {
		
		NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap();
			
			serverBootstrap.group(nioEventLoopGroup).channel(NioServerSocketChannel.class).childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

				@Override
				protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
							System.out.println("接收到数据：" + msg.toString());
							
//							byte[] dst = new byte[msg.readableBytes()];
//							
//							msg.getBytes(msg.readerIndex(), dst);
//							
//							System.out.println("接收到数据：" + new String(dst));
							
							System.out.println(msg.toString(CharsetUtil.UTF_8));
				}
			});
			
			ChannelFuture f = serverBootstrap.bind(new InetSocketAddress("127.0.0.1", 4633)).addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()){
						System.out.println("bind 成功");
					}else{
						System.err.println("bind 失败");
					}
				}
			}).sync();
			
			f.channel().closeFuture().sync();	// 阻塞，直到 channel 关闭
			 System.out.println(TCPServerTest.class.getName() +
		                " started and listening for connections on " + f.channel().localAddress());
		} finally {
			nioEventLoopGroup.shutdownGracefully().sync(); // 优雅停机
		}
		
	}
}
