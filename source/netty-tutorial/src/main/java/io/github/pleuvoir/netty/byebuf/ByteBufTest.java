package io.github.pleuvoir.netty.byebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

@SuppressWarnings("unused")
public class ByteBufTest {

	public static void main(String[] args) {
		
		// 可以这样获取
		ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
		
		// 课可以 这是一个堆内存中的
		ByteBuf buffer2 = Unpooled.buffer();
	}
}
