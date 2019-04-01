package io.github.pleuvoir.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * 安全的停止线程测试
 *
 */
public class TestClient {

	public static void main(String[] args) throws InterruptedException {

		ThreadTask threadTask = new ThreadTask();
		threadTask.start();

		Thread thread = new Thread(new RunnableTask());
		thread.start();
		
		// 尝试5秒后停止任务
		TimeUnit.SECONDS.sleep(5);
		threadTask.interrupt();
		thread.interrupt();
		
	}
}
