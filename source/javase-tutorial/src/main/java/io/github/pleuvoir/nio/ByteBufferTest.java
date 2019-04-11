package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * http://ifeve.com/buffers/ ByteBuffer使用
 *
 *<p>
 *
 *  capacity
	作为一个内存块，Buffer有一个固定的大小值，也叫“capacity”.你只能往里写capacity个byte、long，char等类型。一旦Buffer满了，需要将其清空（通过读数据或者清除数据）才能继续写数据往里写数据。
	
	position
	当你写数据到Buffer中时，position表示当前的位置。初始的position值为0.当一个byte、long等数据写到Buffer后， position会向前移动到下一个可插入数据的Buffer单元。position最大可为capacity – 1.
	当读取数据时，也是从某个特定位置读。当将Buffer从写模式切换到读模式，position会被重置为0. 当从Buffer的position处读取数据时，position向前移动到下一个可读的位置。
	
	limit
	在写模式下，Buffer的limit表示你最多能往Buffer里写多少数据。 写模式下，limit等于Buffer的capacity。
	
	当切换Buffer到读模式时， limit表示你最多能读到多少数据。因此，当切换Buffer到读模式时，limit会被设置成写模式下的position值。
	换句话说，你能读到之前写入的所有数据（limit被设置成已写数据的数量，这个值在写模式下就是position）
 *
 *<p>
 *
 *
 * 总结： ByteBuffer.flip() 一般和FileChannel.write（）同时出现
 */
@SuppressWarnings("unused")
public class ByteBufferTest {

	
	// inChannel.read(buf)  从管道向buf写数据（向buf写），buf应当在写模式
	// inChannel.write(buf) 向管道写数据，相对buf来说就是来读它的数据（从buf读），需要将buf切换为读模式（flip） 
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
		
		
		// 包装 字节数组为ByteBuffer 
		ByteBuffer wrap2 = ByteBuffer.wrap(b, 5, 12);
		System.out.println("包装 字节数组为ByteBuffer  " + wrap2); //java.nio.HeapByteBuffer[pos=5 lim=17 cap=32]
		
		// 写数据 pos+1
		wrap2.put(new byte[1]);
		System.out.println(wrap2); // java.nio.HeapByteBuffer[pos=6 lim=17 cap=32]
		
		//flip方法将Buffer从写模式切换到读模式
		wrap2.flip();
		System.out.println("flip方法将Buffer从写模式切换到读模式  " + wrap2); //java.nio.HeapByteBuffer[pos=0 lim=6 cap=32]
		
		//读取一位会发现pos+1
		wrap2.get();
		System.out.println("wrap2.get()读取一位会发现pos+1  " + wrap2);
		
		//可以从头开始读取
		wrap2.rewind();
		System.out.println("wrap2.rewind()可以从头开始读取 " + wrap2);
		
		wrap2.clear();
		System.out.println("wrap2.clear()被清空了" + wrap2);
		//System.in.read();
	}
}
