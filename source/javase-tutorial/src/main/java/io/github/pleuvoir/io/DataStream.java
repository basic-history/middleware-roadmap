package io.github.pleuvoir.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 数据流
 * 
 * <p>
 * 数据流支持基本数据类型值的二进制I / O（ ，boolean，char，byte，short，int，long，float和double），以及字符串值。
 * 示例通过写出一组数据记录然后再次读取它们来演示数据流 <br>
 * 
 * 另请注意，每个专门write的DataStreams都与相应的专业匹配read。程序员需要确保输出类型和输入类型以这种方式匹配。也就是读取和写入的格式是对应的
 * <p>
 *
 */
public class DataStream {

	static final String dataFile = CopyBytes.filepath + "invoicedata";


	public static void main(String[] args) throws IOException {
		write();
		read();
	}

	// 生成的文件可能是乱码
	private static void write() throws IOException {
		DataOutputStream dos = null;;
		try {
			dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
			for (int i = 0; i < 5; i++) {
				dos.writeDouble(1.00);
				dos.writeInt(1);
				dos.writeUTF("UTF你好");
				
				//写入下一个要读取的字节长度位
				byte[] bytes = "byte[]你好".getBytes();
				int length = bytes.length;
				dos.writeInt(length);
				dos.write(bytes);
				
				dos.writeUTF("\n");
			} 
		} finally {
			if (dos != null) {
				dos.close();
			}
		}
	}


	private static void read() throws IOException {
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
		try {
			for(;;) {
				System.out.println(dis.readDouble());
				System.out.println(dis.readInt());
				System.out.println(dis.readUTF());
				
				int length = dis.readInt();
				// 读取大小必须一样
				byte[] b = new byte[length];
				dis.readFully(b);  //   = readFully(b, 0, b.length); 
				System.out.println(new String(b, "UTF-8"));
				
				System.out.println(dis.readUTF());
			}
		} catch (EOFException e) {
		//	e.printStackTrace();
			System.out.println("end of file");
			// 通过eof异常判断文件到达结尾，而不是-1
		}finally {
			if(dis!=null) {
				dis.close();
			}
		}
	}

}
