package io.github.pleuvoir.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 面向行的字符流，一次读一行 ，对字符流增强
 *
 * <p>
 *  该 CopyLines示例调用BufferedReader.readLine并PrintWriter.println一次输入和输出一行。
 *  调用readLine返回带有该行的文本行。CopyLines使用输出每一行println，它附加当前操作系统的行终止符。这可能与输入文件中使用的行终止符不同。
 *  <p>
 */
public class CopyLines {

	public static void main(String[] args) throws IOException {

		BufferedReader br = null;
		PrintWriter pw = null;

		try {
			br = new BufferedReader(new FileReader(CopyBytes.filepath + "xanadu.txt"));
			pw = new PrintWriter(new FileWriter(CopyBytes.filepath + "line-xanadu.txt"));

			String c;

			while ((c = br.readLine()) != null) {
				pw.println(c);
			}

		} finally {
			if (br != null) {
				br.close();
			}
			if (pw != null) {
				pw.close();
			}
		}

	}
}
