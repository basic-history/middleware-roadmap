package io.github.pleuvoir.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest4_Condition {

	static ReentrantLock lock = new ReentrantLock();
	static Condition condition = lock.newCondition();

	public static void main(String[] args) throws InterruptedException {

		Task task1 = new Task();
		Task task2 = new Task();

		task2.start();
		task1.start();

		TimeUnit.SECONDS.sleep(3);
		try {
			lock.lock();
		//	condition.signal();
			condition.signalAll();
			System.out.println("已通知");
		} finally {
			TimeUnit.SECONDS.sleep(2);
			lock.unlock();
		}
	}

	public static class Task extends Thread {
		@Override
		public void run() {
			try {
				lock.lock();
				condition.await();
				System.out.println(getName() + " over..");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
