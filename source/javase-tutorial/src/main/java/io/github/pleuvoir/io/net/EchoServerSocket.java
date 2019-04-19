package io.github.pleuvoir.io.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerSocket {

	public static void main(String[] args) throws IOException {

		try (
				ServerSocket serverSocket = new ServerSocket(8000); 
				Socket socket = serverSocket.accept(); //阻塞到连接建立
				
				) {
			
			System.out.println("echoServerSocket receive request at 8000.");

				BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter os = new PrintWriter(socket.getOutputStream(), true);
				String request = null;
				while ((request = is.readLine()) != null) {
					System.out.println("from client:" + request);
					os.println("-REPLY:" + request);
					os.println("-EOF");
			}
		}
	}
}
