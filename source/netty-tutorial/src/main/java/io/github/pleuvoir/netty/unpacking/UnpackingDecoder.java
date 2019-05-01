package io.github.pleuvoir.netty.unpacking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 
 * 基于固定域长度的拆包器
 *
 */
public class UnpackingDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * @param maxFrameLength	最大长度，超出这个长度会丢弃或者其他
	 * @param lengthFieldOffset		域的偏移量
	 * @param lengthFieldLength		域长度
	 */
	public UnpackingDecoder() {
		super(Integer.MAX_VALUE, 6, 4);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		return super.decode(ctx, in);
	}

}
