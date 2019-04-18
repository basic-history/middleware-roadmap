package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 异步读取和写入文件
 * 使用AsynchronousFileChannel可以实现异步地读取和写入文件数据。
 */
public class AsynchronousFileChannelTest {


	public static void main(String[] args) throws IOException {
	//	read();
		
		write();
	}

	private static void write() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "\\test-write.txt");
		if (!Files.exists(path)) {
			try {
				Files.createFile(path);
			} catch (Exception e) {
				System.out.println("文件创建失败");
				e.printStackTrace();
			}
		}

		try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);) {

			ByteBuffer buffer = ByteBuffer.allocate(1024);
			long position = 0;

			buffer.put("test data".getBytes());
			buffer.flip();

			fileChannel.write(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					System.out.println("bytes written: " + result);
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					System.out.println("Write failed");
					exc.printStackTrace();
				}
			});
		}
	}


	private static void read() throws IOException {

		try (AsynchronousFileChannel fc = AsynchronousFileChannel
				.open(Paths.get(CopyBytes.filepath + "random-file.txt"), StandardOpenOption.READ);) {

			System.out.println("fc.size()=" + fc.size());

			ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
			long position = 0;

			fc.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
				@Override
				public void completed(Integer result, ByteBuffer attachment) {
					System.out.println("result = " + result);
					attachment.flip();
					byte[] data = new byte[attachment.limit()];
					attachment.get(data);
					System.out.println(new String(data));
					attachment.clear();
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment) {
					
				}
			});
		}
	}

}
