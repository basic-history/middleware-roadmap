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

			PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

			pw.println("hello");
			pw.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

			String response;
			while ((response = br.readLine()) != null) {
				System.out.println(response);
			}
		}

	}
}
