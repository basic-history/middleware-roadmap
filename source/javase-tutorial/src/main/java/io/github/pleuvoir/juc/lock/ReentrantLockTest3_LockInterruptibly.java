package io.github.pleuvoir.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest3_LockInterruptibly {

	static ReentrantLock lock1 = new ReentrantLock();
	static ReentrantLock lock2 = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {
		Task t1 = new Task(1);
		Task t2 = new Task(2);
		t1.start();
		t2.start();
		TimeUnit.SECONDS.sleep(2);
		t1.interrupt();
		System.out.println("t1.interrupt()");
	}

	public static class Task extends Thread {
		int lock; // 控制传入的锁

		public Task(int lock) {
			this.lock = lock;
			setName("lock-" + lock);
		}

		@Override
		public void run() {

			try {

				if (lock == 1) {
					// 先获取lock1 再获取lock2
					lock1.lockInterruptibly();
					System.out.println(Thread.currentThread().getName() + "已获得获取lock1");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName() + "尝试获取lock2");
					lock2.lockInterruptibly();
					System.out.println(Thread.currentThread().getName() + "获取lock2成功，执行完毕");
				} else {
					// 先获取lock2 再获取lock1
					lock2.lockInterruptibly();
					System.out.println(Thread.currentThread().getName() + "已获得获取lock2");
					TimeUnit.SECONDS.sleep(1);
					System.out.println(Thread.currentThread().getName() + "尝试获取lock1");
					lock1.lockInterruptibly();
					System.out.println(Thread.currentThread().getName() + "获取lock1成功，执行完毕");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				if (lock1.isHeldByCurrentThread()) {
					lock1.unlock();
				}
				if (lock2.isHeldByCurrentThread()) {
					lock2.unlock();
				}
			}
		}
	}

}
