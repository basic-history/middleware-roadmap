package io.github.pleuvoir.nio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.pleuvoir.io.CopyBytes;

/**
 * 文本文件的缓冲I / O方法
 *
 */
public class BufferedTextMethod {

	public static void main(String[] args) throws IOException {
		read();
		read2();
		write();
		write2();
	}
	// StandardCharsets.UTF_8.name()

	// 带缓冲的读（优先使用）
	private static void read() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "small-file");
		try (BufferedReader reader = Files.newBufferedReader(path);) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
	
	
	// 带缓冲的写（优先使用）
	private static void write() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "bufferedtextmethod-file");
		try (BufferedWriter writer = Files.newBufferedWriter(path);) {
			writer.write("你好");
		}
	}
	
	
	// 无缓冲流的方法和java.ioAPI的互操作性
	private static void read2() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "small-file");
		try (InputStream in = Files.newInputStream(path);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		}
	}
	
	// 无缓冲流的方法和java.ioAPI的互操作性
	private static void write2() throws IOException {
		Path path = Paths.get(CopyBytes.filepath + "bufferedtextmethod-file");
		try (OutputStream os = Files.newOutputStream(path);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
			) {
			writer.write("你好啊");
		}
	}

}
