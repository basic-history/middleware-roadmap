package io.github.pleuvoir.io.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *	基于OIO的服务端 
 *
 */
public class EchoServerSocket {

	public static void main(String[] args) throws IOException {

		ExecutorService tp = Executors.newCachedThreadPool();
		try (ServerSocket serverSocket = new ServerSocket(8000);) {

			for (;;) {
				Socket socket = serverSocket.accept(); // 阻塞到连接建立
				System.out.println("echoServerSocket receive request at 8000.");
				tp.submit(() -> {
					try {
						BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
						String request = null;
						while ((request = is.readLine()) != null) {
							System.out.println(Thread.currentThread().getName() + " from client:" + request);
							os.println("-REPLY:" + request);
							os.println("-EOF");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		}
	}
}
