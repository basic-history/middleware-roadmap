package io.github.pleuvoir.juc.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 强悍的LockSupport
 * 
 * <p>
 * 1.即使unpark方法在park方法之前执行也能保证正常结束，不会导致线程永久挂起 
 * 2.可以响应中断，通过中断标志位来判断
 * </p>
 *
 */
public class LockSupportTest {

	public static void main(String[] args) throws InterruptedException {

		ChangeObjectThread t1 = new ChangeObjectThread("t1");
		ChangeObjectThread t2 = new ChangeObjectThread("t2");

		t1.start();
		TimeUnit.SECONDS.sleep(1); // 保证t1.park在t1.unpark前执行
		t2.start();

		if (ThreadLocalRandom.current().nextBoolean()) {
			t1.interrupt();
		} else {
			LockSupport.unpark(t1);
			System.out.println("t1" + "|" + System.currentTimeMillis() + " unpark");

		}

		LockSupport.unpark(t2);
		System.out.println("t2" + "|" + System.currentTimeMillis() + " unpark");

		t1.join();
		t2.join();
	}

	public static class ChangeObjectThread extends Thread {

		public ChangeObjectThread(String name) {
			super(name);
		}

		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(150));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(getName() + "|" + System.currentTimeMillis() + " park");
			// LockSupport.park();// 阻塞 如果被中断，不会抛出异常而是修改中断标志位
			LockSupport.park(this);

			System.out.println(isInterrupted());
			if (Thread.interrupted()) {
				System.out.println("被中断了");
				System.out.println(isInterrupted());
			}
		}
	}
}
