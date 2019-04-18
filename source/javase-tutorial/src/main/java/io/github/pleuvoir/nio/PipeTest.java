package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;
import java.nio.charset.StandardCharsets;

/**
 * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
 *
 */
public class PipeTest {

	public static void main(String[] args) throws IOException {
		Pipe pipe = Pipe.open();

		// 用来写入
		SinkChannel sink = pipe.sink();
		ByteBuffer src = ByteBuffer.allocate(1000);
		src.put((System.currentTimeMillis() + "data").getBytes());

		src.flip();
		while (src.hasRemaining()) {
			sink.write(src);
		}

		// 用来读取
		SourceChannel source = pipe.source();
		source.configureBlocking(false); // 否则程序无法退出
		ByteBuffer buf = ByteBuffer.allocate(1);

		while (source.read(buf) > 0) {
			System.out.println(buf);
			buf.flip();
			System.out.println(buf);
			System.out.println("读取到数据：" + StandardCharsets.UTF_8.decode(buf));
			buf.rewind();
		}

	}
}
