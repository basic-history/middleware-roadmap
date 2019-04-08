package io.github.pleuvoir.juc.atomic;

import java.util.concurrent.CountDownLatch;

public class SimulatedCASTest {

	static int threadCount = 20;
	static SimulatedAtomicInteger count = new SimulatedAtomicInteger(0);
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
