package io.github.pleuvoir.juc.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 让一组线程到达某个屏障，然后一起开放，可以用来测试并行
 * 
 * <pre>为什么叫循环屏障？TOOD
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

		// 普通用法
		for (int i = 0; i < 3; i++) {
			new ExcuteThread().start();
		}

		// 到达屏障前会执行BARRIER_WITH_TASK定义的内容
		for (int i = 0; i < 2; i++) {
			new ExcuteThread_2().start();
		}
		
	}

	static class ExcuteThread extends Thread {
		@Override
		public void run() {
			try {
				if (new Random().nextBoolean()) {
					System.out.println(getName() + " 运气不好，休息 2 秒");
					TimeUnit.SECONDS.sleep(2);
				}
				System.out.println(getName() + " 到达屏障前");
				BARRIER.await();
				System.out.println(getName() + "准备休息 3 秒，然后一起出发");
				TimeUnit.SECONDS.sleep(3);
				System.out.println(getName() + " 到达位置");
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
					System.out.println(getName() + " 运气不好，休息 2 秒");
					TimeUnit.SECONDS.sleep(2);
				}
				System.out.println(getName() + " 到达屏障前");
				BARRIER_WITH_TASK.await();
				System.out.println(getName() + "准备休息 3 秒，然后一起出发");
				TimeUnit.SECONDS.sleep(3);
				System.out.println(getName() + " 到达位置");
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
	}

}