package io.github.pleuvoir.io.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class EchoClient {
	
	static CyclicBarrier CYCLICBARRIER = new CyclicBarrier(2);
	
	static int CONNECT_COUNT = 6;

	static ExecutorService tp = Executors.newFixedThreadPool(CONNECT_COUNT, new ThreadFactory() {
		AtomicLong count = new AtomicLong();
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setName("client-" + count.getAndIncrement());
			return t;
		}
	});

	public static void main(String[] args) {
		for (int i = 0; i < CONNECT_COUNT; i++) {
			tp.execute(new ConnectTask());
		}
		tp.shutdown();
	}

	public static class ConnectTask implements Runnable {
		@Override
		public void run() {
			try (Socket client = new Socket();) {
				CYCLICBARRIER.await();
				client.connect(new InetSocketAddress("127.0.0.1", 8000));
				System.out.println("echoClient connect success.");
				PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
				for (int i = 0; i < 1; i++) {
					// 打印对象的格式化输出到文本输出流
					pw.println("我是来自" + Thread.currentThread().getName() + "的消息.");
					pw.flush();
					// 从一个字符输入流中读取文本
					BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
					System.out.println(br.readLine());
					System.out.println(br.readLine());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
