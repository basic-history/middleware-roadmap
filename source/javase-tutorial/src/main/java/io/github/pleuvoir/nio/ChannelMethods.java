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
			ByteBuffer buf = ByteBuffer.allocate(1024);  //分配大小 b，如果过小则读取的内容会有问题
			while (sbc.read(buf) > 0) {
				buf.rewind();
				CharBuffer decode = StandardCharsets.UTF_8.decode(buf);
				System.out.println(decode);
				buf.flip();
			}
		}
	}
}
