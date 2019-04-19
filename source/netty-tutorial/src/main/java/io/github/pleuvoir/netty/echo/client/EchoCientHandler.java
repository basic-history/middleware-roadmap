package io.github.pleuvoir.netty.echo.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable
public class EchoCientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	// 和服务器建立连接后调用
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 往服务器写数据
		System.out.println("EchoCientHandler channelActive ..");
		ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
	}

	/// 当收到消息时调用
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("EchoCientHandler channelRead0 ..");
		System.out.println("Client accetp:" + msg.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
