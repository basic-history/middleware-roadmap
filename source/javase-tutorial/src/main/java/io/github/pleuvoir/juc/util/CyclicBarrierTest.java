package io.github.pleuvoir.juc.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 让一组线程到达某个屏障，然后一起开放，可以用来测试并行
 * 
 * <pre>为什么叫循环屏障？因为在一个线程中可循环使用，注意：不要在多个线程中传递使用，那是无效的。</pre>
 *
 */
public class CyclicBarrierTest {

	static final CyclicBarrier BARRIER = new CyclicBarrier(3);

	static final CyclicBarrier BARRIER_WITH_TASK = new CyclicBarrier(2, new Runnable() {
		@Override
		public void run() {
			System.out.println("barrier_with_task GO..");
		}
	});

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

		// 普通用法可循环使用，注意不要再不同线程中用
		for (int i = 0; i < 3; i++) {
			new ExcuteThread("normal-" + i).start();
		}

		// 到达屏障前会执行BARRIER_WITH_TASK定义的内容
		for (int i = 0; i < 2; i++) {
			new ExcuteThread_2().start();
		}
		
	}

	static class ExcuteThread extends Thread {
		
		public ExcuteThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				if (new Random().nextBoolean()) {
					TimeUnit.SECONDS.sleep(2);
				}
				System.out.println(getName() + " 到达屏障前");
				BARRIER.await();
				TimeUnit.SECONDS.sleep(3);
				System.out.println(getName() + " over");
				
				BARRIER.await();
				TimeUnit.SECONDS.sleep(3);
				System.out.println(getName() + " over again");
				
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class ExcuteThread_2 extends Thread {
		@Override
		public void run() {
			try {
				if (new Random().nextBoolean()) {
					TimeUnit.SECONDS.sleep(2);
				}
				System.out.println(getName() + " 到达屏障前");
				BARRIER_WITH_TASK.await();
				TimeUnit.SECONDS.sleep(3);
				System.out.println(getName() + " 到达位置");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

}