package io.github.pleuvoir.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示死锁
 *
 */
public class ReentrantLockTest2_DeadLock {

	static ReentrantLock lock1 = new ReentrantLock();
	static ReentrantLock lock2 = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {
		Task t1 = new Task(1);
		Task t2 = new Task(2);
		t1.start();
		t2.start();
		t1.join();
	}

	public static class Task extends Thread {
		int lock; // 控制传入的锁

		public Task(int lock) {
			this.lock = lock;
		}

		@Override
		public void run() {

			if (lock == 1) {
				try {
					// 先获取lock1 再获取lock2
					lock1.lock();
					TimeUnit.SECONDS.sleep(1);
					lock2.lock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock1.unlock();
					lock2.unlock();
				}
			} else {
				try {
					// 先获取lock2 再获取lock1
					lock2.lock();
					TimeUnit.SECONDS.sleep(1);
					lock1.lock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock2.unlock();
					lock1.unlock();
				}
			}

		}
	}

}
