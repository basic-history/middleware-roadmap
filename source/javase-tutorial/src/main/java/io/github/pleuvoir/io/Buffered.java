package io.github.pleuvoir.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Java平台实现了缓冲的 I / O流。缓冲输入流从称为缓冲区的存储区读取数据; 仅当缓冲区为空时才调用本机输入API。类似地，缓冲输出流将数据写入缓冲区，并且仅在缓冲区已满时才调用本机输出API。
 * 刷新缓冲区而无需等待它填充，请调用其flush方法。该flush方法在任何输出流上都有效，但除非缓冲流，否则无效。
 * 
 * <p>
 * 还有用于包装缓冲流4个缓冲流类： BufferedInputStream与 BufferedOutputStream创建缓冲字节流，而 BufferedReader与 BufferedWriter创建缓冲字符流
 */
public class Buffered {


	public static void main(String[] args) throws IOException {

		bufferedInputStreamAndOutputStream();
		bufferedReaderAndWriter();
	}


	// 缓冲增强CopyBytes字节流
	private static void bufferedInputStreamAndOutputStream() throws IOException {

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try {
			bis = new BufferedInputStream(new FileInputStream(CopyBytes.filepath + "xanadu.txt"));
			bos = new BufferedOutputStream(new FileOutputStream(CopyBytes.filepath + "buffered-io-xanadu.txt"));
			int c;
			while ((c = bis.read()) != -1) {
				bos.write(c);
			}
			bos.flush();
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}

	}


	// 缓冲增强CopyCharacters字符流
	private static void bufferedReaderAndWriter() throws IOException {

		BufferedReader br = null;
		BufferedWriter bw = null;

		try {
			br = new BufferedReader(new FileReader(CopyBytes.filepath + "xanadu.txt"));
			bw = new BufferedWriter(new FileWriter(CopyBytes.filepath + "buffered-xanadu.txt"));
			int c;
			while ((c = br.read()) != -1) {
				bw.write(c);
			}
			bw.flush();
		} finally {
			if (br != null) {
				br.close();
			}
			if (bw != null) {
				bw.close();
			}
		}
	}

}
