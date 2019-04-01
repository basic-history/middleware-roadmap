package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public class ByteBufferTest {

	public static void main(String[] args) throws IOException {
		
		System.out.println("before allocate: 		" + Runtime.getRuntime().freeMemory());
		ByteBuffer bb = ByteBuffer.allocate(1024 * 1024);
		System.out.println("after allocate:  		" + Runtime.getRuntime().freeMemory());
		
		// 分配1g直接内存，非JVM内存，可以发现机器内存飙升
		ByteBuffer.allocateDirect(1024 * 1024 * 1024);
		System.out.println("after allocateDirect:   	" + Runtime.getRuntime().freeMemory());
		
		byte[] b = new byte[32];
		ByteBuffer wrap = ByteBuffer.wrap(b);
		System.out.println(wrap); // java.nio.HeapByteBuffer[pos=0 lim=32 cap=32]
		
		ByteBuffer wrap2 = ByteBuffer.wrap(b, 5, 12);
		System.out.println(wrap2); //java.nio.HeapByteBuffer[pos=5 lim=17 cap=32]
		
		//翻转
		wrap2.flip();
		System.out.println(wrap2); //java.nio.HeapByteBuffer[pos=0 lim=5 cap=32]
		//System.in.read();
	}
}
