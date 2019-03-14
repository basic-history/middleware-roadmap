package io.github.pleuvoir.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 字符流
 *
 *<p>
 *	所有字符流类都来自 Reader和 Writer。与字节流一样，有一些专门用于文件I / O的字符流类： FileReader和 FileWriter。
 *
 *</p>
 */
public class CopyCharacters {

	public static void main(String[] args) throws IOException {

		FileReader fr = null;
		FileWriter fw = null;

		try {
			fr = new FileReader(CopyBytes.filepath + "xanadu.txt");
			fw = new FileWriter(CopyBytes.filepath + "char-xanadu.txt");
			int c;
			while ((c = fr.read()) != -1) {
				fw.write(c);
			}
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (fw != null) {
				fw.close();
			}
		}

	}
}
