package io.github.pleuvoir.juc.datasourcepool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 测试连接池实现
 *
 */
public class TestClient {

	public static void main(String[] args) throws InterruptedException {

		DatasourcePool pool = new DatasourcePool(5);
		AtomicLong success = new AtomicLong(0);
		AtomicLong fail = new AtomicLong(0);

		// 50个线程每个线程拿20次连接，总共拿1000次连接
		int threadCount = 50;
		int connectionCount = 20;
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			new Thread(new Worker(pool, success, fail, connectionCount, latch), "Worker-" + i).start();
		}
		latch.await();

		System.out.println("共获取连接" + threadCount * connectionCount + "次");
		System.out.println("成功获取连接" + success.get() + "次");
		System.out.println("失败获取连接" + fail.get() + "次");

	}
}
