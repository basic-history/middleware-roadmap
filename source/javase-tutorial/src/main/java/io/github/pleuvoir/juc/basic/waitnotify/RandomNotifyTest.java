package io.github.pleuvoir.juc.basic.waitnotify;

import java.util.concurrent.TimeUnit;

/**
 * 演示线程的通知是从对象的等待队列随机选择
 * <br>
 * 有很多个线程在这个对象上等待，会随机通知某个准备获取synchronized锁的线程
 */
public class RandomNotifyTest {

	static Monitor monitor = new Monitor();

	public static void main(String[] args) {

		Thread waitGetNameThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					monitor.waitGetName();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread waitGetTimestampThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					monitor.waitGetTimestamp();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		

		waitGetTimestampThread.setName("waitGetTimestampThread");
		waitGetTimestampThread.start();
		
		waitGetNameThread.setName("waitGetNameThread");
		waitGetNameThread.start();

		//改变名字并通知，会发现它是随机通知 正在等待获取对象锁的线程
		new Thread(() -> {monitor.changeName("pleuvoir");}).start();
	}

	private static class Monitor {
		private String name = "default";
		private long timestamp = -1L;

		public synchronized String waitGetName() throws InterruptedException {
			System.out.println(Thread.currentThread().getName() + "获取到对象锁" + System.currentTimeMillis());
			wait();
			System.out.println(Thread.currentThread().getName() + " wait返回了：waitGetName()=" + name);
			return name;
		}

		public synchronized long waitGetTimestamp() throws InterruptedException {
			System.out.println(Thread.currentThread().getName() + "获取到对象锁" + System.currentTimeMillis());
			wait();
			System.out.println(Thread.currentThread().getName() + " wait返回了：waitGetTimestamp()=" + timestamp);
			return timestamp;
		}

		public synchronized void changeName(String name) {
			this.name = name;
			notify();
			//notifyAll();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		@SuppressWarnings("unused")
		public void changeTimestamp(long timestamp) {
			this.timestamp = timestamp;
			notify();
		}

	}
}
