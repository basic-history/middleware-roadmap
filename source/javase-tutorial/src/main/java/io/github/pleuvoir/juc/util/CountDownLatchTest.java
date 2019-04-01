package io.github.pleuvoir.juc.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * <p> 作用：一个线程等待其他线程完成以后再工作，可以用于控制线程执行顺序
 *
 */
public class CountDownLatchTest {

	static final CountDownLatch LATCH = new CountDownLatch(3);

	public static void main(String[] args) throws InterruptedException {
		
		
		new Worker().start();

		for (int i = 0; i < 3; i++) {
			new Main(i).start();
		}
		
		LATCH.await();  //可以多次使用等待方法，将在扣减完毕后一起释放
		System.out.println("主线程执行了..");
		
	}

	static class Main extends Thread {

		public Main(int i ){
			setName("Main-" + i);
		}
		
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextLong(5));
				System.out.println(getName() + " Main..go");
				LATCH.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static class Worker extends Thread {
		@Override
		public void run() {
			try {
				LATCH.await();
				System.out.println("Worker..go");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
