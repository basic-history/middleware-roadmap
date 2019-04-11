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
 * 
 *  大家好
	我是渣渣辉
	是兄弟就来砍我
	
	
	你们坏
	我是渣渣辉
	是兄弟就来砍我
	大家好
	我是渣渣辉
	是兄弟就来砍我
	你们坏
 */ 
public class RandomAccessMethods {

	public static void main(String[] args) throws IOException {
		readAndWrite();
	}


	private static void readAndWrite() throws IOException {

		ByteBuffer data = ByteBuffer.wrap("你们坏".getBytes()); // 包装为bb
		ByteBuffer copy = ByteBuffer.allocate(300); 

		
		try(
				FileChannel fc = FileChannel.open(Paths.get(CopyBytes.filepath + "random-file.txt"), StandardOpenOption.READ,
						StandardOpenOption.WRITE);){
			int nread;
			do {
				nread = fc.read(copy);  //从通道写字节到buff，写完后通道的pos也会随之移动
			} while (nread != -1 && copy.hasRemaining());
			
			// copy中现在包含原始文件的所有内容 ，接下来我们移动指针到通道开头，并加上 hello world
			
			fc.position(0);  //移动到开头为了写数据会覆盖开头的内容，除非通道处于APPEND模式 将追加文件到结尾
			while (data.hasRemaining()) {
				fc.write(data);
			}
			
			// 此时如果写的内容过少 则可能不能完全覆盖之前的内容
			data.rewind();  //重置 data pos=0 一般是为了从头开始读取内容

			// 移动指针到通道文件末尾
			long length = fc.size();
			fc.position(length);	
			copy.flip();	//切换为读模式，即如果之前设置的limit=12,postion=5 翻转后postion=0,limit=5

			while (copy.hasRemaining()) {
				fc.write(copy);
			}
			
			while (data.hasRemaining()) {
				fc.write(data);
			}
		}
		
	}
	
	
	
	
}
