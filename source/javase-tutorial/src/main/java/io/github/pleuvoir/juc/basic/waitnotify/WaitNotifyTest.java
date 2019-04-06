package io.github.pleuvoir.juc.basic.waitnotify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 演示等待通知机制
 * 
 * <p>
 * 控制线程顺序，使T1先于T2执行；
 * 当栅栏开放，T1线程休眠2秒，此时T2线程无法获得对象锁，直到2秒过后T1线程执行wait   -> （wait会释放锁）
 * 当T2获得锁执行notify，T1并没有第一时间返回（指的是wait等待结束），而是在休眠2秒后返回  -> （notify不会释放锁，只有在临界区结束后会释放）
 * </p>
 *
 */
public class WaitNotifyTest {

	static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws InterruptedException {
		T1 t1 = new T1();
		T2 t2 = new T2();
		t1.start();
		t2.start();
	}

	final static Object object = new Object();

	public static class T1 extends Thread {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":T1 start!");
			synchronized (object) {
				try {
					System.out.println(System.currentTimeMillis() + ":T1 wait for notify!");
					latch.countDown(); 
					sleep2();  
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(System.currentTimeMillis() + ":T1 end!");
		}
	}

	public static class T2 extends Thread {
		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println(System.currentTimeMillis() + ":T2 栅栏开放，尝试获取对象锁!");
			synchronized (object) {
				System.out.println(System.currentTimeMillis() + ":T2 栅栏开放，获取对象锁成功!");
				System.out.println(System.currentTimeMillis() + ":T2 start!");
				object.notify();
				System.out.println(System.currentTimeMillis() + ":T2 end! notify t1 Thread");
				sleep2();
			}
		}
	}

	private static void sleep2() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
