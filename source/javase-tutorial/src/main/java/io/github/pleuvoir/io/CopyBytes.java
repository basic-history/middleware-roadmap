package io.github.pleuvoir.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节流的使用，平时开发使用应避免使用这种低级的IO 每次只会读取一个字节 <br>
 * 
 * CopyBytes大部分时间都花在一个简单的循环中，该循环读取输入流并一次写入一个字节的输出流
 * 
 *
 */
public class CopyBytes {

	// 使用绝对路径
	public static String filepath = "D:\\03 space\\01 git\\middleware-roadmap\\source\\javase-tutorial\\src\\main\\resources\\";
	
	public static void main(String[] args) throws IOException {
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(filepath + "xanadu.txt");		
			fos = new FileOutputStream(filepath + "tcopyed-xanadu.txt");
			int c;
			
			System.out.println("共" + fis.available() + "字节");  //41
			
			int count = 0;
			while ((c = fis.read()) != -1) { //每次只会读取一个字节
				fos.write(c);
				++count;
			}
			
			System.out.println("循环" + count + "次"); //41
		} finally {
			if (null != fis) {
				fis.close();
			}
			if (null != fos) {
				fos.close();
			}
		}
	}

}
