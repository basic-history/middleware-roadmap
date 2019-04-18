package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.pleuvoir.io.CopyBytes;

/**
 * Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
 * FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
 *
 * 其他方法可参考：http://ifeve.com/file-channel/
 */
@SuppressWarnings("unused")
public class FileChannelTest {
	
	public static void main(String[] args) throws IOException {
		//read();
		write();
	}
	
	private static void write() throws IOException {
		String newData = System.currentTimeMillis() + " data";
		ByteBuffer buf = ByteBuffer.allocate(60);
		buf.put(newData.getBytes());
		buf.flip();

		try (FileChannel fc = FileChannel.open(Paths.get(CopyBytes.filepath + "fromFile.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		) {
			//注意FileChannel.write()是在while循环中调用的。因为无法保证write()方法一次能向FileChannel写入多少字节，因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
			while (buf.hasRemaining()) {
				fc.write(buf);
			}
		}
	}
	
	private static void read() throws IOException {
		// 使用一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例
		try (FileChannel fc = FileChannel.open(Paths.get(CopyBytes.filepath + "fromFile.txt"), StandardOpenOption.READ,
				StandardOpenOption.WRITE);

		) {
			// 大小设置的越小，循环次数越多，建议设置合理的大小 下面代码flip和rewind的顺序可以调换，但建议使用这样的顺序
			ByteBuffer copy = ByteBuffer.allocate(100);
			while (fc.read(copy) > 0) {
				copy.flip();
				CharBuffer decode = StandardCharsets.UTF_8.decode(copy); // 移动pos
				System.out.println("StandardCharsets.UTF_8.decode   " + decode);
				copy.rewind();
			}
		}
	}
}
