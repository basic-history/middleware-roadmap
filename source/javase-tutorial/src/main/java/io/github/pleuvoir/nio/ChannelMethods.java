package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 通道IO <br>
 * 当流I / O一次读取一个字符时，通道I / O一次读取一个缓冲区。
 * 
 * @author pleuvoir
 *
 */
public class ChannelMethods {

	public static void main(String[] args) throws IOException {
		read();
	}

	private static void read() throws IOException {
		try (SeekableByteChannel sbc = Files.newByteChannel(Paths.get(CopyBytes.filepath + "line-xanadu.txt"));) {
			System.out.println("文件大小：" + sbc.size());
			ByteBuffer buf = ByteBuffer.allocate(100);  //分配大小 ，如果过大会浪费内存
			while (sbc.read(buf) > 0) { //将通道数据写入缓冲区，如果缓冲区过小则会循环多次，如果大小足够则一次
				System.out.println("rewind before=" + buf); //[pos=43 lim=100 cap=100]
				buf.rewind(); //需要重置pos，因为如果有第二次循环,limit已经满了 （这一步如果分配的内存不足则是必须的）
				System.out.println("rewind after =" + buf);  //[pos=0 lim=100 cap=100]
				CharBuffer decode = StandardCharsets.UTF_8.decode(buf); //这一步操作会移动pos
				System.out.println("decode after =" + buf); //[pos=100 lim=100 cap=100]
				System.out.println(decode);
				buf.flip();   //因为上面的decode改变了pos，所以需要重置为0，否则无法写入
				System.out.println("flip after =" + buf); //[pos=0 lim=100 cap=100]
			}
		}
	}
}
