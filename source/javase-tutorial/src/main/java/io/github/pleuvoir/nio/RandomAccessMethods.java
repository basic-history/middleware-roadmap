package io.github.pleuvoir.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 随机访问文件
 * <p>
 * 随机访问文件允许对文件内容进行非顺序或随机访问。要随机访问文件，请打开文件，查找特定位置，以及读取或写入该文件。
 * 会在所在位置覆盖原始数据
 * 
 * https://docs.oracle.com/javase/tutorial/essential/io/rafs.html
 * <p>
 *
 */
public class RandomAccessMethods {

	public static void main(String[] args) throws IOException {
		readAndWrite();
	}


  /**
   * hello world\n
   * 原始内容的几个字节，取决于allocate的大小  注：测试时原始文件请设置为123
   * hello world\n
   */
	private static void readAndWrite() throws IOException {

		ByteBuffer data = ByteBuffer.wrap("hello world\n".getBytes()); // 包装为bb
		ByteBuffer copy = ByteBuffer.allocate(300); 

		
		try(
				FileChannel fc = FileChannel.open(Paths.get(CopyBytes.filepath + "random-file.txt"), StandardOpenOption.READ,
						StandardOpenOption.WRITE);){
			int nread;
			do {
				nread = fc.read(copy);  //从通道读取字节到缓冲区，读取完毕通道的portion也会随之移动
			} while (nread != -1 && copy.hasRemaining());
			
			// 以上先读取最多n个字节到copy中，接下来我们移动指针到文件开头，并加上 hello world
			
			fc.position(0);  //移动到开头为了写数据
			while (data.hasRemaining()) {
				fc.write(data);
			}
			data.rewind();  //倒回，重置 pos=0，方便再次写入

			// 移动指针到文件末尾
			long length = fc.size();
			fc.position(length - 1);	
			copy.flip();	//翻转，即如果之前设置的limit=12,postion=5 翻转后postion=0,limit=5

			while (copy.hasRemaining()) {
				fc.write(copy);
			}
			
			while (data.hasRemaining()) {
				fc.write(data);
			}
		}
		
	}
	
	
	
	
}
