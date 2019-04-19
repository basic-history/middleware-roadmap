package io.github.pleuvoir.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

// 指明我这个 handler 可以在多个 channel 之间共享，意味这个实现必须是线程安全的。
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	// 对于每个传入的消息都要调用
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf in = (ByteBuf) msg;
		System.out.println("Server Accept:" + in.toString(CharsetUtil.UTF_8));
		ctx.write(in); // 将接受到的消息写给发送者，而不冲刷出站消息
	}

	// 将未决消息冲刷到远程节点，并且关闭该Channel（未决消息指的是暂存于ChannelOutBoundBuffer中的消息，在下一次调用flush或者writeAndFlush时将会尝试写到套接字）
	// 通知ChannelInboundHandler最后一次对channelRead的调用是当前批量读取的最后一条消息
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	//在读取操作期间有异常抛出会调用
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
