package io.github.pleuvoir.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest1_Lock {

	static ReentrantLock lock = new ReentrantLock();

	static int j = 0;

	public static void main(String[] args) throws InterruptedException {
		Task first = new Task();
		Task second = new Task();
		first.start();
		second.start();
		first.join();
		second.join();
		System.out.println(j);
	}

	public static class Task extends Thread {
		@Override
		public void run() {
			try {
				lock.lock();
				lock.lock();
				for (int i = 0; i < 1000; i++) {
					j++;
				}
			} finally {
				lock.unlock();
				lock.unlock();
			}
		}
	}

}
