package io.github.pleuvoir.juc.excutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class CachedThreadPoolTest {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
			AtomicLong count = new AtomicLong(1);

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				thread.setName("nameThreadFactory-" + count.getAndIncrement());
				return thread;
			}
		});

		int i = 0;
		for (; i < 10; i++) {
			pool.execute(() -> {
				System.out.println(Thread.currentThread().getName() + " go");
			});
		}
		//必须结束，否则线程无法关闭
		pool.shutdown();
	}
}
