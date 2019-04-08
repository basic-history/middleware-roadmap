package io.github.pleuvoir.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

	static int threadCount = 20;
	static AtomicInteger count = new AtomicInteger();
	static CountDownLatch lacth = new CountDownLatch(threadCount);

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < threadCount; i++) {
			new Thread(() -> {
				for (int j = 0; j < 10000; j++) {
					count.incrementAndGet();
				}
				lacth.countDown();
			}).start();
		}

		lacth.await();
		System.out.println(count.get());
	}

}
