package io.github.pleuvoir.io.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try (Socket client = new Socket();) {

			client.connect(new InetSocketAddress("127.0.0.1", 8000));

			System.out.println("echoClient connect success.");

			// 打印对象的格式化输出到文本输出流
			PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

			pw.println("hello");
			pw.flush();

			//从一个字符输入流中读取文本
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String response;
			while ((response = br.readLine()) != null) {
				System.out.println(response);
			}
		}

	}
}
