package io.github.pleuvoir.netty.unpacking;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class EchoClient {

	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws InterruptedException {

		// if (args.length != 2) {
		// System.err.println("Usage: " + EchoClient.class.getSimpleName() +
		// " <host> <port>"
		// );
		// return;
		// }
		//
		// final String host = args[0];
		// final int port = Integer.parseInt(args[1]);
		new EchoClient("127.0.0.1", 9000).start();
	}

	
	
	private void start() throws InterruptedException {
		// 线程组
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap(); // 客户端启动必备
			bootstrap.group(group).channel(NioSocketChannel.class) // 指明使用NIO进行网络通讯
					.remoteAddress(new InetSocketAddress(host, port)).handler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel ch) throws Exception {

							// ch.pipeline().addLast(new FixedLengthFrameDecoder(21));

							
							ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 6, 4));
							ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
								@Override
								public void channelActive(ChannelHandlerContext ctx) throws Exception {
									for (int i = 0; i < 100; i++) {
										ByteBuf buffer = ctx.alloc().buffer();
//										ctx.writeAndFlush(buffer.writeBytes("你好啊，渣渣灰".concat(System.getProperty("line.separator")).getBytes())); //21
//										System.out.println("这个就是固定长度拆包器要设置的大小=" + buffer.writerIndex());
										
										byte[] bytes = "你好啊，渣渣灰".getBytes();
										
									      // 魔数
								        buffer.writeInt(9527);
								        //指令
								        buffer.writeByte(1);
								        //序列化算法
								        buffer.writeByte(1);
								        //长度位
								        buffer.writeInt(bytes.length);
								        // 内容
								        buffer.writeBytes(bytes);
								        
										ctx.channel().writeAndFlush(buffer);
									}
								}
							});
						}
					});
			// 配置远程服务器的地址

			ChannelFuture channelFuture = bootstrap.connect().sync(); // 连接到远程节点，阻塞等待直到连接完成

			channelFuture.channel().closeFuture().sync(); // 阻塞，直到 channel 关闭
		} finally {
			group.shutdownGracefully().sync(); // 优雅停机
		}
	}
}
