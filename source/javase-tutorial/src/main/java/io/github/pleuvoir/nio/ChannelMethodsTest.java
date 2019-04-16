package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 通道IO <br>
 * 当流I / O一次读取一个字符时，通道I / O一次读取一个缓冲区。
 * 
 * @author pleuvoir
 *
 */
public class ChannelMethodsTest {

	public static void main(String[] args) throws IOException {
		read();
		
		transFrom();
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
	
	
	/**
	 * 方法的输入参数position表示从position处开始向目标文件写入数据，count表示最多传输的字节数。如果源通道的剩余空间小于 count 个字节，则所传输的字节数要小于请求的字节数。
	 *	此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
	 */
	private static void transFrom() throws IOException {

		try (FileChannel fc = FileChannel.open(Paths.get(CopyBytes.filepath + "fromFile.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);

				FileChannel to = FileChannel.open(Paths.get(CopyBytes.filepath + "fromFile2.txt"),
						StandardOpenOption.READ, StandardOpenOption.WRITE);) {

			System.out.println(to.size());

			to.transferFrom(fc, 0, fc.size()); // 内容会被完全覆盖，原始内容丢失

			System.out.println(to.size());
		}
	}
}
