package io.github.pleuvoir.juc.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量， 通常是限制多少数量的线程访问某一资源。其实信号量就是一种限制策略，在 web 服务器中，信号量就是一种限流策略，限制多少线程执行，和这个模式差不多。
 * <br>
 * 示例即为每5个线程一组同时访问，一共3次
 */
public class SemaphoreTest implements Runnable {

	final Semaphore SA = new Semaphore(5);

	public static void main(String[] args) {

		SemaphoreTest semaphoreTest = new SemaphoreTest();
		for (int i = 0; i < 15; i++) {
			new Thread(semaphoreTest).start();
		}
	}

	@Override
	public void run() {
		try {
			SA.acquire(); // 获取1个许可，如果不能获取到许可则会一直阻塞
			TimeUnit.SECONDS.sleep(3);
			System.out.println(Thread.currentThread().getName() + " go.");
			SA.release(); // 归还1个许可
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
