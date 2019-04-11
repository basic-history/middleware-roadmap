package test;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.github.pleuvoir.io.CopyBytes;


/**
 * 这种文件的读写是覆盖模式，writer.write("")会全部清除文件内容，和NIO随机读写不一样
 *
 */
public class LicenseTest {

	private static String lineSeperator = System.getProperty("line.separator");

	public static void main(String[] args) throws IOException {
		String license = read2String(CopyBytes.filepath + "Apache-LICENSE");
		String src = read2String(CopyBytes.filepath + "test.txt");

		String concat = license.concat(src);
		System.out.println(concat);

		Path path = Paths.get(CopyBytes.filepath + "test.txt");
		try (BufferedWriter writer = Files.newBufferedWriter(path);) {
			writer.write(concat);
			// 是清空文件内容
			// writer.write("");
		}
	}

	private static String read2String(String s) throws IOException {
		Path path = Paths.get(s);
		StringBuffer sb = new StringBuffer();
		try (BufferedReader reader = Files.newBufferedReader(path);) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append(lineSeperator);
			}
		}
		return sb.toString();
	}
}
